package com.tlw.service;

import com.tlw.entity.User;

public interface UserService {
    User findByUserName(String username);
}
