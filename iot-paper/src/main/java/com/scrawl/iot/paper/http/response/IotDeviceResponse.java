package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Setter
@Getter
public class IotDeviceResponse extends IotResponse {
    private IotDeviceData deviceData;

    private String devSerial;
    private String location;
    private Integer isPublished;
    private String endUserName;
    private String connectPointId;
    private String endUserInfo;
    private String devType;
    private String name;
    private Double longitude;
    private Double latitude;
    private String hasSimCard;
    private Date lastMessageTime;
    private String serviceMode;

    public class IotDeviceData{

    }
}
