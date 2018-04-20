package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/4/21.
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum {
    FAIL((byte) 0, "失败"),
    SUCCESS((byte) 1, "成功");

    private Byte code;
    private String desc;
}
