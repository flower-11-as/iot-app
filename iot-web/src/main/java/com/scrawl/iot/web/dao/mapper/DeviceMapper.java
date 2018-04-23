package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;

import java.util.List;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    List<Device> selectPageList(DeviceListReqVO reqVO);

    int selectPageCount(DeviceListReqVO reqVO);

    Device selectByDevSerial(String devSerial);
}