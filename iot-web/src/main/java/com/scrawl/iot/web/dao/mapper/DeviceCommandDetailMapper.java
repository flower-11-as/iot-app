package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceCommandDetail;

public interface DeviceCommandDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceCommandDetail record);

    int insertSelective(DeviceCommandDetail record);

    DeviceCommandDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceCommandDetail record);

    int updateByPrimaryKey(DeviceCommandDetail record);
}