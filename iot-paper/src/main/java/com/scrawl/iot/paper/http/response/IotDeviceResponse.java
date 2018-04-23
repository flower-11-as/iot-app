package com.scrawl.iot.paper.http.response;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Setter
@Getter
public class IotDeviceResponse extends IotResponse {
    private List<IotDeviceData> deviceData;

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
    private String protocolType;
    private String simNum;

    @Setter
    @Getter
    public class IotDeviceData{
        private String serviceId;
        private Map<String, Object> serviceData;
    }
}
