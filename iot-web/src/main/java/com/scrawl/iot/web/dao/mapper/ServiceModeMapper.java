package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.ServiceMode;

import java.util.List;

public interface ServiceModeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceMode record);

    int insertSelective(ServiceMode record);

    ServiceMode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceMode record);

    int updateByPrimaryKey(ServiceMode record);

    List<ServiceMode> selectList();

    int deleteAll();

    int deleteByServerId(String serverId);

    List<ServiceMode> selectByServerId(String serverId);
}