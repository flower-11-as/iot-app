package com.scrawl.iot.web.vo.iot.callback;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
@Setter
@Getter
public class IotDataReportReqVO {
//            'lastMessageTime': '2018-03-06 10:02:02',
//                    'serviceData': [
//    {'serviceId': 'Battery', 'serviceData': {'batteryLevel': 10}}, //电量
//    {'serviceId': 'Meter', 'serviceData': {'signalStrength': -11}}, //信号
//    {
//        'serviceId': 'm1',     //产品定义中消息ID
//            'serviceData': {'param1': 61, 'param2': 51, 'param3': 21}    //传感器参数及值
//    }
//        ],
//                'createTime': '2018-03-01 17:21:47',
//                'devSerial': 'xxxxxxxxxxxxx'
    private Date lastMessageTime;
    private String devSerial;
    private Date createTime;
    private List<IotDeviceData> serviceData;


    @Setter
    @Getter
    public class IotDeviceData{
        private String serviceId;
        private Map<String, Object> serviceData;
    }
}
