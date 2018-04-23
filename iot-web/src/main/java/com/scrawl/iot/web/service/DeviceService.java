package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
public interface DeviceService {

    List<Device> list(DeviceListReqVO reqVO);

    int count(DeviceListReqVO reqVO);

    boolean save(Device device);

    boolean update(Device device);

    void syncDevices(List<String> serverIds, Integer managerId);

    boolean syncDevice(Device device);

    boolean remove(Device device);

    boolean sendCommand();
}
