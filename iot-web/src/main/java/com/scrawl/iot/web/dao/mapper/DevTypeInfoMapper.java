package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevTypeInfo;

public interface DevTypeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevTypeInfo record);

    int insertSelective(DevTypeInfo record);

    DevTypeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevTypeInfo record);

    int updateByPrimaryKey(DevTypeInfo record);
}