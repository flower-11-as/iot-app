package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Server;

import java.util.List;

public interface ServerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);

    int deleteAll();

    List<Server> selectList();
}