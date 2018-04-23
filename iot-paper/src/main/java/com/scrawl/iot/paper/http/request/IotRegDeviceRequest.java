package com.scrawl.iot.paper.http.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/4/24.
 */
@Setter
@Getter
public class IotRegDeviceRequest {
    private String devSerial;
    private String name;
    private String deviceType;
    private String connectPointId;
    private String serviceMode;
    private String endUserName;
    private String endUserInfo;
    private String location;
    private Double longitude;
    private Double latitude;
    private String extend_type;
}
