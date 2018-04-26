package com.scrawl.iot.paper.http.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
@Setter
@Getter
public class IotCommandRequest {
    private String devSerial;
    private String method;
    private Map<String, Object> params;
}
