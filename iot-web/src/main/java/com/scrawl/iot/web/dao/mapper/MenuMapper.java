package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> listMenuByManagerId(Integer id);

    List<Menu> list();
}