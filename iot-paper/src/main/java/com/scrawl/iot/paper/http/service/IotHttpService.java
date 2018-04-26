package com.scrawl.iot.paper.http.service;

import com.scrawl.iot.paper.http.client.IotHttpClient;
import com.scrawl.iot.paper.http.constans.IotConstant;
import com.scrawl.iot.paper.http.request.*;
import com.scrawl.iot.paper.http.response.*;

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

    // IoT登录授权
    public IotLoginResponse loginAuth(IotLoginRequest request) {
        return iotHttpClient.doPost(IotConstant.LOGIN_AUTH, null, request, IotLoginResponse.class);
    }

    // IoT获取连接平台
    public IotServerResponse getServers(IotHeader header) {
        return iotHttpClient.doGet(IotConstant.SERVER, header, null, IotServerResponse.class);
    }

    // IoT获取业务类型
    public IotServiceModeResponse getServiceModes(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doGet(withUrlParams(IotConstant.SERVICE_MODE, urlParams), header,
                null, IotServiceModeResponse.class);
    }

    // IoT获取产品型号
    public IotDevTypeResponse getDevTypes(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doGet(withUrlParams(IotConstant.DEV_TYPES, urlParams), header,
                null, IotDevTypeResponse.class);
    }

    // IoT获取产品型号信息
    public IotDevTypeInfoResponse getDevType(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doGet(withUrlParams(IotConstant.DEV_TYPE, urlParams), header,
                null, IotDevTypeInfoResponse.class);
    }

    // IoT获取设备信息
    public IotDeviceAllResponse getDevices(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doGet(withUrlParams(IotConstant.DEVICE_ALL, urlParams), header,
                null, IotDeviceAllResponse.class);
    }

    // IoT获取单个设备信息
    public IotDeviceResponse getDevice(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doGet(withUrlParams(IotConstant.DEVICE, urlParams), header,
                null, IotDeviceResponse.class);
    }

    // IoT注册设备信息
    public IotResponse regDevice(IotHeader header, IotRegDeviceRequest request) {
        return iotHttpClient.doPost(IotConstant.REG_DEVICE, header,
                request, IotResponse.class);
    }

    // IoT删除设备信息
    public IotResponse delDevice(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doDelete(withUrlParams(IotConstant.DEL_DEVICE, urlParams), header,
                null, IotResponse.class);
    }

    // IoT查询订阅地址
    public IotQuerySubscribeResponse querySubscribe(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doGet(withUrlParams(IotConstant.QUERY_SUBSCRIBE, urlParams), header,
                null, IotQuerySubscribeResponse.class);
    }

    // IoT查询订阅地址
    public IotResponse unSubscribe(Map<String, Object> urlParams, IotHeader header) {
        return iotHttpClient.doDelete(withUrlParams(IotConstant.UN_SUBSCRIBE, urlParams), header,
                null, IotResponse.class);
    }

    // IoT注册订阅地址
    public IotResponse subscribe(IotHeader header, IotSubscribeRequest request) {
        return iotHttpClient.doPost(IotConstant.SUBSCRIBE, header,
                request, IotResponse.class);
    }

    // IoT指令上报
    public IotCommandResponse command(IotHeader header, IotCommandRequest request) {
        return iotHttpClient.doPost(IotConstant.COMMAND, header,
                request, IotCommandResponse.class);
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
