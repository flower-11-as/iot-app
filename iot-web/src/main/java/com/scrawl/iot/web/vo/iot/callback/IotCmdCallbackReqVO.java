package com.scrawl.iot.web.vo.iot.callback;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
@Setter
@Getter
public class IotCmdCallbackReqVO {
    private String devSerial;
    private String commandId;
    private Map<String, Object> resultParams;
}
