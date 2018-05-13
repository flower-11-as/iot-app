package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/5/13.
 */
@Getter
@AllArgsConstructor
public enum DeviceAlarmStatusEnum {
    NOT_ALARM((byte) 0, "未预警"),
    ALARM((byte) 1, "预警中");

    private byte status;
    private String desc;
}
