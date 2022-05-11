package com.tlw.service.impl;

import com.alibaba.fastjson.JSON;
import com.tlw.entity.LoginUser;
import com.tlw.entity.User;
import com.tlw.service.LoginServcie;
import com.tlw.utils.JwtUtil;
import com.tlw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



@Service
public class LoginServcieImpl implements LoginServcie {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(User user) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);

        //authenticate存入redis
        String redisKey = "user::" + token;
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(loginUser));

        //把token响应给前端
        Result result = Result.success(token);
        return result;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("user::" + token);
        return Result.success("注销成功");
    }
}
