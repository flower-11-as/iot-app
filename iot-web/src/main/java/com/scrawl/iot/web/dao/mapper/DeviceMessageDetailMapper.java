package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceMessageDetail;

public interface DeviceMessageDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceMessageDetail record);

    int insertSelective(DeviceMessageDetail record);

    DeviceMessageDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceMessageDetail record);

    int updateByPrimaryKey(DeviceMessageDetail record);
}