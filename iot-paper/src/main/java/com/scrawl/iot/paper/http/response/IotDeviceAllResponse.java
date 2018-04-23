package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Getter
@Setter
public class IotDeviceAllResponse extends IotResponse {
    List<IotDevice> devices;

    @Setter
    @Getter
    public class IotDevice {
        private List<DeviceData> devMessage;
        // 信号
        private BigDecimal signalStrength;
        // 电量
        private BigDecimal batteryLevel;

        private Date createTime;
        private String devSerial;
        private String industryName;
        private String location;
        private Integer isPublished;
        private String createBy;// serverId
        private String clientID;
        private Integer displayIconId;
        private String endUserName;
        private String connectPointId;
        private String categoryName;
        private String endUserInfo;
        private String devType;
        private String name;
        private Double longitude;
        private Double latitude;
        private String hasSimCard;
        private String simNum;
    }

    @Setter
    @Getter
    public static class DeviceData {
        private String serviceId;
        private Map<String, Object> serviceData;
    }
}
