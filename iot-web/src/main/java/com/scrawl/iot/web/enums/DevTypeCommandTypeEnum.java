package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Getter
@AllArgsConstructor
public enum DevTypeCommandTypeEnum {
    // 1、指令参数 2、指令响应参数
    COMMAND_REQUEST((byte) 1, "指令参数"),
    COMMAND_RESPONSE((byte) 2, "指令响应参数");

    private Byte type;
    private String desc;
}
