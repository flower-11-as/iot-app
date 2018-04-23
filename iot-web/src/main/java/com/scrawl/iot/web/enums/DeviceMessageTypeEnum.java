package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Getter
@AllArgsConstructor
public enum DeviceMessageTypeEnum {
    // 1、设备消息 2、电量 3、信号
    MESSAGE((byte) 1, "设备消息"),
    BATTERY((byte) 2, "电量"),
    METER((byte) 3, "信号");

    private Byte type;
    private String desc;
}
