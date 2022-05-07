package com.tlw.service;

import java.util.List;

public interface MenuService {
    List<String> findMenusByUserId(Long userid);
}
