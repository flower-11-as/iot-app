package com.scrawl.iot.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Created by as on 2018/5/7.
 */
@Getter
@AllArgsConstructor
public enum NoticeRecordStatusEnum {
    // 状态 0、未读 1、已读
    NOT_READ((byte) 0, "未读"),
    READ((byte) 1, "已读");

    private Byte code;
    private String desc;
}
