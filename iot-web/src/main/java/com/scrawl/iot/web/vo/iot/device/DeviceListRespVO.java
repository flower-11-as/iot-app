package com.scrawl.iot.web.vo.iot.device;

import com.scrawl.iot.web.dao.entity.Device;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Setter
@Getter
public class DeviceListRespVO extends Device {
    private Byte alarmStatus;

    private String alarmDesc;
}
