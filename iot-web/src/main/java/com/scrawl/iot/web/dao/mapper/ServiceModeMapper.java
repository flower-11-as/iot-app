package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.ServiceMode;

public interface ServiceModeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceMode record);

    int insertSelective(ServiceMode record);

    ServiceMode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceMode record);

    int updateByPrimaryKey(ServiceMode record);
}