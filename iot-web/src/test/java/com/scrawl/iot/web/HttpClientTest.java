package com.scrawl.iot.web;

import com.alibaba.fastjson.JSON;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.request.IotLoginRequest;
import com.scrawl.iot.paper.http.request.IotSubscribeRequest;
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
        devTypeService.syncDevTypes(list);
    }

    @Test
    public void getDevices() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("4a4702e7220f5d3c0de819fd51bf7613");

        Map<String, Object> params = new HashMap<>();
        params.put("serverID", "jiayingdev01");
        IotDeviceAllResponse response = iotHttpService.getDevices(params, header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getDevice() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("43a7ba885a626980c51f3d6adb3475f6");

        Map<String, Object> params = new HashMap<>();
        params.put("devSerial", "863703036557655");
        IotDeviceResponse response = iotHttpService.getDevice(params, header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void delDevice() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("43a7ba885a626980c51f3d6adb3475f6");

        Map<String, Object> params = new HashMap<>();
        params.put("devSerial", "test123");
        IotResponse response = iotHttpService.delDevice(params, header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void querySubscribe() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("e5d3cc9b1557bac0cfd95a895e02eb5a");

        Map<String, Object> params = new HashMap<>();
//        params.put("devSerial", "test123");
        IotQuerySubscribeResponse response = iotHttpService.querySubscribe(params, header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void unSubscribe() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("e5d3cc9b1557bac0cfd95a895e02eb5a");

        Map<String, Object> params = new HashMap<>();
//        params.put("devSerial", "test123");
        IotResponse response = iotHttpService.unSubscribe(params, header);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void subscribe() {
        IotHeader header = new IotHeader();
        header.setServerId("jiayingdev01");
        header.setAccessToken("e5d3cc9b1557bac0cfd95a895e02eb5a");

        IotSubscribeRequest request = new IotSubscribeRequest();
        request.setCallbackUrl("http://104.224.159.254/iot/callback");
        IotResponse response = iotHttpService.subscribe(header, request);
        System.out.println(JSON.toJSONString(response));
    }
}
