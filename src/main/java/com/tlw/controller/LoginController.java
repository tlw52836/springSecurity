package com.tlw.controller;

import com.tlw.entity.User;
import com.tlw.service.LoginServcie;
import com.tlw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginServcie loginServcie;

    @PostMapping("/user/login")
    public Result login(@RequestBody User user){
        return loginServcie.login(user);
    }


    @PostMapping("/user/logout")
    public Result logout(@RequestHeader String token){
        return loginServcie.logout(token);
    }
}