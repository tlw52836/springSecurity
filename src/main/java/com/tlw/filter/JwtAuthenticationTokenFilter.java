package com.tlw.filter;

import com.alibaba.fastjson.JSON;
import com.tlw.entity.LoginUser;
import com.tlw.utils.JwtUtil;
import com.tlw.vo.Result;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("token非法");
            response.setStatus(403);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSON.toJSONString(Result.fail(403, "token非法")));
            return;
        }

        //从redis中获取用户信息
        String redisKey = "user::" + token;
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        LoginUser loginUser = JSON.parseObject(redisValue, LoginUser.class);


        if(Objects.isNull(loginUser)){
            //throw new RuntimeException("用户未登录");
            response.setStatus(403);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSON.toJSONString(Result.fail(403, "用户未登录")));
            return;
        }

        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request, response);
    }
}
