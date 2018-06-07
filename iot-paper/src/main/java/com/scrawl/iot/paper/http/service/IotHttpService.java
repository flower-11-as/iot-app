package com.scrawl.iot.paper.http.service;

import com.alibaba.fastjson.JSON;
import com.scrawl.iot.paper.http.client.IotHttpClient;
import com.scrawl.iot.paper.http.constans.IotConstant;
import com.scrawl.iot.paper.http.request.*;
import com.scrawl.iot.paper.http.response.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
@Slf4j
public class IotHttpService {

    private IotHttpClient iotHttpClient;

    public IotHttpService(IotHttpClient iotHttpClient) {
        this.iotHttpClient = iotHttpClient;
    }

    // IoT登录授权
    public IotLoginResponse loginAuth(IotLoginRequest request) {
        log.info("IoT登录授权：request[{}]", JSON.toJSONString(request));
        IotLoginResponse response = iotHttpClient.doPost(IotConstant.LOGIN_AUTH, null, request, IotLoginResponse.class);
        log.info("IoT登录授权：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT获取连接平台
    public IotServerResponse getServers(IotHeader header) {
        log.info("IoT获取连接平台：header[{}]", JSON.toJSONString(header));
        IotServerResponse response = iotHttpClient.doGet(IotConstant.SERVER, header, null, IotServerResponse.class);
        log.info("IoT获取连接平台：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT获取业务类型
    public IotServiceModeResponse getServiceModes(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT获取业务类型：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotServiceModeResponse response = iotHttpClient.doGet(withUrlParams(IotConstant.SERVICE_MODE, urlParams), header,
                null, IotServiceModeResponse.class);
        log.info("IoT获取业务类型：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT获取产品型号
    public IotDevTypeResponse getDevTypes(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT获取产品型号：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotDevTypeResponse response = iotHttpClient.doGet(withUrlParams(IotConstant.DEV_TYPES, urlParams), header,
                null, IotDevTypeResponse.class);
        log.info("IoT获取产品型号：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT获取产品型号信息
    public IotDevTypeInfoResponse getDevType(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT获取产品型号信息：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotDevTypeInfoResponse response = iotHttpClient.doGet(withUrlParams(IotConstant.DEV_TYPE, urlParams), header,
                null, IotDevTypeInfoResponse.class);
        log.info("IoT获取产品型号信息：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT获取设备信息
    public IotDeviceAllResponse getDevices(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT获取设备信息：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotDeviceAllResponse response = iotHttpClient.doGet(withUrlParams(IotConstant.DEVICE_ALL, urlParams), header,
                null, IotDeviceAllResponse.class);
        log.info("IoT获取设备信息：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT获取单个设备信息
    public IotDeviceResponse getDevice(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT获取单个设备信息：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotDeviceResponse response = iotHttpClient.doGet(withUrlParams(IotConstant.DEVICE, urlParams), header,
                null, IotDeviceResponse.class);
        log.info("IoT获取单个设备信息：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT注册设备信息
    public IotResponse regDevice(IotHeader header, IotRegDeviceRequest request) {
        log.info("IoT注册设备信息：header[{}],request[{}]", JSON.toJSONString(header), JSON.toJSONString(request));
        IotResponse response = iotHttpClient.doPost(IotConstant.REG_DEVICE, header,
                request, IotResponse.class);
        log.info("IoT注册设备信息：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT编辑设备信息
    public IotResponse updateDevice(IotHeader header, IotUpdateDeviceRequest request) {
        log.info("IoT编辑设备信息：header[{}],request[{}]", JSON.toJSONString(header), JSON.toJSONString(request));
        IotResponse response = iotHttpClient.doPut(IotConstant.UPDATE_DEVICE, header,
                request, IotResponse.class);
        log.info("IoT编辑设备信息：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT删除设备信息
    public IotResponse delDevice(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT删除设备信息：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotResponse response = iotHttpClient.doDelete(withUrlParams(IotConstant.DEL_DEVICE, urlParams), header,
                null, IotResponse.class);
        log.info("IoT删除设备信息：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT查询订阅地址
    public IotQuerySubscribeResponse querySubscribe(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT查询订阅地址：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotQuerySubscribeResponse response = iotHttpClient.doGet(withUrlParams(IotConstant.QUERY_SUBSCRIBE, urlParams), header,
                null, IotQuerySubscribeResponse.class);
        log.info("IoT查询订阅地址：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT取消订阅
    public IotResponse unSubscribe(Map<String, Object> urlParams, IotHeader header) {
        log.info("IoT取消订阅：urlParams[{}],header[{}]", JSON.toJSONString(urlParams), JSON.toJSONString(header));
        IotResponse response = iotHttpClient.doDelete(withUrlParams(IotConstant.UN_SUBSCRIBE, urlParams), header,
                null, IotResponse.class);
        log.info("IoT取消订阅：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT注册订阅地址
    public IotResponse subscribe(IotHeader header, IotSubscribeRequest request) {
        log.info("IoT注册订阅地址：header[{}],request[{}]", JSON.toJSONString(header), JSON.toJSONString(request));
        IotResponse response = iotHttpClient.doPost(IotConstant.SUBSCRIBE, header,
                request, IotResponse.class);
        log.info("IoT注册订阅地址：response[{}]", JSON.toJSONString(response));
        return response;
    }

    // IoT指令上报
    public IotCommandResponse command(IotHeader header, IotCommandRequest request) {
        log.info("IoT指令上报：header[{}],request[{}]", JSON.toJSONString(header), JSON.toJSONString(request));
        IotCommandResponse response = iotHttpClient.doPost(IotConstant.COMMAND, header,
                request, IotCommandResponse.class);
        log.info("IoT指令上报：response[{}]", JSON.toJSONString(response));
        return response;
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
