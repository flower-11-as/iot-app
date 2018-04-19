package com.scrawl.iot.paper.http.client;

import com.alibaba.fastjson.JSONObject;
import com.scrawl.iot.paper.exception.IotHttpException;
import com.scrawl.iot.paper.http.Constans.IotConstant;
import com.scrawl.iot.paper.http.properties.IotProperties;
import com.scrawl.iot.paper.http.request.IotRequest;
import com.scrawl.iot.paper.http.response.IotResponse;
import com.scrawl.iot.paper.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
@Slf4j
public class IotHttpClient {
    private PaperHttpClient httpClients;
    private IotProperties iotProperties;

    public IotHttpClient(IotProperties iotProperties, PaperHttpClient httpClients) {
        this.iotProperties = iotProperties;
        this.httpClients = httpClients;
    }

    public <T extends IotResponse> T doPost(String url, IotRequest request, Class<T> t) {
        return doPost(url, ContentType.APPLICATION_JSON, request, t);
    }

    public <T extends IotResponse> T doPost(String url, ContentType contentType, IotRequest request, Class<T> t) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", contentType.getMimeType());

        Map<String, Object> params = BeanUtil.object2Map(request);
        HttpResponse httpResponse = httpClients.doPost(iotProperties.getApiUri() + url, headers, params);

        return convertResponse(httpResponse, t);
    }

    public <T extends IotResponse> T doGet(String url, IotRequest request, Class<T> t) {
        return doGet(url, ContentType.APPLICATION_JSON, request, t);
    }

    public <T extends IotResponse> T doGet(String url, ContentType contentType, IotRequest request, Class<T> t) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", contentType.getMimeType());

        Map<String, Object> params = BeanUtil.object2Map(request);
        HttpResponse httpResponse = httpClients.doGet(iotProperties.getApiUri() + url, headers, params);

        return convertResponse(httpResponse, t);
    }

    private <T extends IotResponse> T convertResponse(HttpResponse httpResponse, Class<T> t) {
        HttpEntity entity = httpResponse.getEntity();
        String entityStr;
        try {
            entityStr = EntityUtils.toString(entity);
        } catch (IOException e) {
            log.error("解析entity错误", e);
            throw new IotHttpException("解析entity错误");
        }

        IotResponse response = JSONObject.parseObject(entityStr, IotResponse.class);
        if (response.getOptResult().equals(IotConstant.SUCCESS_CODE)) {
            return JSONObject.parseObject(entityStr, t);
        }

        throw new IotHttpException(response.getOptResult() +
                IotConstant.resultCodeExplain.getOrDefault(response.getOptResult(), "iot请求异常"));
    }
}
