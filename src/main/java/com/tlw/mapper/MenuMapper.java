package com.tlw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.tlw.entity.Menu;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> findMenusByUserId(Long userid);
}