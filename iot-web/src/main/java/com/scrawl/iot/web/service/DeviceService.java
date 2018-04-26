package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.dao.entity.DeviceBasicDetail;
import com.scrawl.iot.web.dao.entity.DeviceMessageDetail;
import com.scrawl.iot.web.vo.iot.callback.IotDataReportReqVO;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
public interface DeviceService {

    List<Device> list(DeviceListReqVO reqVO);

    int count(DeviceListReqVO reqVO);

    void save(Device device);

    boolean update(Device device);

    void syncDevices(List<String> serverIds, Integer managerId);

    void syncDevice(Integer id, Integer managerId);

    void remove(Integer id, Integer managerId);

    boolean sendCommand();

    Device get(Integer id);

    Map<String, DeviceBasicDetail> getBaseSensorInfo(Integer deviceId);

    Map<String, DeviceMessageDetail> getMessageInfo(Integer deviceId);

    void deviceDataReport(IotDataReportReqVO reqVO);
}
