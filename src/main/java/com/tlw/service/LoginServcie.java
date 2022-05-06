package com.tlw.service;

import com.tlw.entity.User;
import com.tlw.vo.Result;

public interface LoginServcie {
    Result login(User user);
}
