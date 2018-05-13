package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.DeviceBasicDetail;
import com.scrawl.iot.web.dao.entity.DeviceMessage;
import com.scrawl.iot.web.dao.entity.DeviceMessageDetail;
import com.scrawl.iot.web.dao.entity.DeviceSync;
import com.scrawl.iot.web.vo.iot.device.DeviceReportRespVO;

import java.util.List;
import java.util.Map;

/**
 * Description: 设备消息接口
 * Created by as on 2018/5/13.
 */
public interface DeviceMessageService {

    /**
     * 获取设备最新同步
     *
     * @param deviceId 设备id
     * @return 设备最新同步
     */
    DeviceSync getLastDeviceSync(Integer deviceId);

    /**
     * 根据设备同步id获取设备消息
     *
     * @param deviceSyncId 设备同步id
     * @return 设备消息
     */
    List<DeviceMessage> getDeviceMessageBySync(Integer deviceSyncId);

    /**
     * 根据设备消息id获取设备消息详情
     *
     * @param deviceMessageId 设备消息id
     * @return 设备消息详情
     */
    List<DeviceMessageDetail> getDeviceMessageDetailsByMsg(Integer deviceMessageId);

    /**
     * 根据设备同步id获取设备消息详情
     *
     * @param deviceSyncId     设备同步id
     * @return 设备消息详情
     */
    Map<String, List<DeviceMessageDetail>> getDeviceMessageDetailsBySync(Integer deviceSyncId);

    /**
     * 根据设备同步id获取设备基础消息详情
     *
     * @param deviceSyncId
     * @return
     */
    List<DeviceBasicDetail> getDeviceBasicDetails(Integer deviceSyncId);

    /**
     * 获取设备报表数据
     * TODO[as]：暂时写死Lamp报表
     *
     * @param deviceId 设备id
     * @return 设备24小时内数据
     */
    DeviceReportRespVO getReportData(Integer deviceId);
}
