package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/4/24.
 */
@Getter
@AllArgsConstructor
public enum DelFlagEnum {
    NO((byte) 0, "已删除"),
    YES((byte) 1, "未删除");

    private Byte code;
    private String desc;
}
