package com.scrawl.iot.paper.http.service;

import com.scrawl.iot.paper.http.constans.IotConstant;
import com.scrawl.iot.paper.http.client.IotHttpClient;
import com.scrawl.iot.paper.http.request.IotLoginRequest;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.response.IotLoginResponse;
import com.scrawl.iot.paper.http.response.IotServerResponse;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
public class IotHttpService {

    private IotHttpClient iotHttpClient;

    public IotHttpService(IotHttpClient iotHttpClient) {
        this.iotHttpClient = iotHttpClient;
    }

    // IoT 登录授权
    public IotLoginResponse loginAuth(IotLoginRequest request) {
        return iotHttpClient.doPost(IotConstant.LOGIN_AUTH, null, request, IotLoginResponse.class);
    }

    // IoT 获取连接平台
    public IotServerResponse getServers(IotHeader header, IotHeader request) {
        return iotHttpClient.doGet(IotConstant.SERVER, header, request, IotServerResponse.class);
    }
}
