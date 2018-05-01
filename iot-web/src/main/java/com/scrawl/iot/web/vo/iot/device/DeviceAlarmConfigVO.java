package com.scrawl.iot.web.vo.iot.device;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Setter
@Getter
public class DeviceAlarmConfigVO {
    private String description;
    private String paramKey;
    private String paramValue;
    private String sysParamValue;
}
