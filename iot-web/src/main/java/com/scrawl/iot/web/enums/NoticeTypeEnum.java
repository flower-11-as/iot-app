package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/5/9.
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {
    NOTICE((byte) 1, "通知"),
    ALARM((byte) 2, "预警");

    private byte type;
    private String desc;
}
