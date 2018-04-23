package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceBasicDetail;

public interface DeviceBasicDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceBasicDetail record);

    int insertSelective(DeviceBasicDetail record);

    DeviceBasicDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceBasicDetail record);

    int updateByPrimaryKey(DeviceBasicDetail record);
}