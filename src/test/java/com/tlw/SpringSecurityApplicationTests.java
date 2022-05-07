package com.tlw;

import com.tlw.entity.User;
import com.tlw.mapper.MenuMapper;
import com.tlw.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testBcryptEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("2695684aaa"));
        System.out.println(encoder.encode("2695684aaa"));
    }

    @Test
    public void testMenuMapper(){
        List<String> menus = menuMapper.findMenusByUserId(1l);
        System.out.println(menus);
    }

    @Test
    public void testException(){
        AuthenticationException e = new BadCredentialsException("ttt");
        System.out.println(e.getMessage());
        System.out.println(e.getCause());
        System.out.println(e.fillInStackTrace());
        System.out.println(e.getStackTrace());
    }

}
