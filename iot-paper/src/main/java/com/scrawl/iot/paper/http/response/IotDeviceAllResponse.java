package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Getter
@Setter
public class IotDeviceAllResponse extends IotResponse {
    private Integer id;

    private String serverId;

    private String devType;

    private String devSerial;

    private String name;

    private String connectPointId;

    private String serviceMode;

    private Integer isPublished;

    private String location;

    private Double longitude;

    private Double latitude;

    private String endUserInfo;

    private String endUserName;

    private String industryName;

    private String categoryName;

    private Integer displayIconId;

    private String clientId;

    private String protocolType;

    private String hasSimCard;

    private Byte delFlag;

    private Date createTime;
}
