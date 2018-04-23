package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/4/24.
 */
@Getter
@AllArgsConstructor
public enum  DeviceSyncTypeEnum {
    // 同步类型：1、后台同步 2、job同步 3、iot同步
    MANAGER((byte) 1, "后台同步"),
    JOB((byte) 2, "job同步"),
    IOT((byte) 3, "iot同步");

    private Byte type;
    private String desc;
}
