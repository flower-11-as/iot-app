package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Getter
@AllArgsConstructor
public enum  DevTypeInfoTypeEnum {
    // 1、设备消息 2、指令参数 3、指令响应参数
    MESSAGE_REQUEST((byte) 1, "设备消息"),
    COMMAND_REQUEST((byte) 2, "指令参数"),
    COMMAND_RESPONSE((byte) 3, "指令响应参数");

    private Byte type;
    private String desc;
}
