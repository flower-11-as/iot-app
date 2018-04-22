package com.scrawl.iot.web;

import com.alibaba.fastjson.JSON;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.request.IotLoginRequest;
import com.scrawl.iot.paper.http.response.*;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.service.DevTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Description:
 * Created by as on 2018/4/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class HttpClientTest {
    @Autowired
    private IotHttpService iotHttpService;

    @Autowired
    private DevTypeService devTypeService;

    @Test
    public void loginAuthTest() {
        IotLoginRequest request = new IotLoginRequest();
        request.setServerId("jiayingdev01");
        request.setPassword("jsj2018");
        IotLoginResponse response = iotHttpService.loginAuth(request);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getIotServers() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("10478d612196daf63365956cc643765b");
        IotServerResponse response = iotHttpService.getServers(header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getServiceModes() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("10478d612196daf63365956cc643765b");

        Map<String, Object> params = new HashMap<>();
        params.put("iotserverId", "ctc-nanjing-iot-137");
        IotServiceModeResponse response = iotHttpService.getServiceModes(params, header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getDevTypes() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("10478d612196daf63365956cc643765b");

        Map<String, Object> params = new HashMap<>();
        params.put("serverID", "jiayingdev01");
        IotDevTypeResponse response = iotHttpService.getDevTypes(params, header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getDevType() {
//        IotHeader header = new IotHeader();
//        header.setServerId("jiayingdev01");
//        header.setAccessToken("10478d612196daf63365956cc643765b");
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("devType", "MyLamp");
//        IotDevTypeInfoResponse response = iotHttpService.getDevType(params, header);
//        System.out.println(JSON.toJSONString(response));
        List<String> list = Arrays.asList("jiayingdev01");
        devTypeService.syncDevTypes(list, 1);
    }
}
