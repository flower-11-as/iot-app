package com.scrawl.iot.paper.http.service;

import com.scrawl.iot.paper.http.client.IotHttpClient;
import com.scrawl.iot.paper.http.constans.IotConstant;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.request.IotLoginRequest;
import com.scrawl.iot.paper.http.response.IotLoginResponse;
import com.scrawl.iot.paper.http.response.IotServerResponse;
import com.scrawl.iot.paper.http.response.IotServiceModeResponse;

import java.util.HashMap;
import java.util.Map;

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
    public IotServerResponse getServers(IotHeader header) {
        return iotHttpClient.doGet(IotConstant.SERVER, header, null, IotServerResponse.class);
    }

    public IotServiceModeResponse getServiceModes(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doGet(withUrlParams(IotConstant.SERVICE_MODE, urlParams), header,
                null, IotServiceModeResponse.class);
    }

    private String withUrlParams(String url, Map<String, Object> urlParams) {
        if (null == urlParams || urlParams.size() == 0) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url + "?");
        urlParams.forEach((k, v) -> {
            if (sb.toString().endsWith("?")) {
                sb.append(k).append("=").append(v);
            } else {
                sb.append("&").append(k).append("=").append(v);
            }
        });

        return sb.toString();
    }
}
