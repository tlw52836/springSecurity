package com.tlw.service.impl;

import com.tlw.mapper.MenuMapper;
import com.tlw.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> findMenusByUserId(Long userid) {
        return menuMapper.findMenusByUserId(userid);
    }
}
