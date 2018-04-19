package com.scrawl.iot.web;

import com.alibaba.fastjson.JSON;
import com.scrawl.iot.paper.http.request.IotLoginRequest;
import com.scrawl.iot.paper.http.response.IotLoginResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Description:
 * Created by as on 2018/4/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class HttpClientTest {
    @Autowired
    private IotHttpService iotHttpService;

    @Test
    public void loginAuthTest() {
        IotLoginRequest request = new IotLoginRequest();
        request.setServerId("jiayingdev01");
        request.setPassword("jsj2018");
        IotLoginResponse response = iotHttpService.loginAuth(request);
        System.out.println(JSON.toJSONString(response));
    }
}
