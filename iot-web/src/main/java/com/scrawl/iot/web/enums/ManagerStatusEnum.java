package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
@Getter
@AllArgsConstructor
public enum ManagerStatusEnum {
    OFF((byte) 0, "禁用"),
    ON((byte) 0, "正常");

    private Byte code;
    private String desc;
}
