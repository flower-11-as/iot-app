package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Desc:
 * Create by scrawl on 2018/4/21
 */
@Getter
@Setter
public class IotServerResponse extends IotResponse {
//    {"iotserverList":[{"id":"HW-test-iot-117","desc":"华为深圳实验室自助测试平台。"},
// {"id":"ctc-nanjing-iot-137","desc":"中国电信物联网开放平台"},
// {"id":"HW-test-iot-112","desc":"华为深圳实验室测试平台。"}],"optResult":"0"}
    private List<IotServer> iotserverList;

    @Setter
    @Getter
    public class IotServer{
        private String id;
        private String desc;
    }
}
