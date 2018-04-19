package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
@Setter
@Getter
public class IotResponse {
    /**
     * 0为成功，其他为失败
     */
    protected String optResult;
}
