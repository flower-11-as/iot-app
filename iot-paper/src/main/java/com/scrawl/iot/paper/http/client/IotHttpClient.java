package com.scrawl.iot.paper.http.client;

import com.scrawl.iot.paper.http.properties.IotProperties;
import com.scrawl.iot.paper.request.IotRequest;
import com.scrawl.iot.paper.response.IotResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
@Slf4j
public class IotHttpClient {
    private IotProperties iotProperties;
    private PaperHttpClient httpClients;

    public IotHttpClient(IotProperties iotProperties, PaperHttpClient httpClients) {
        this.iotProperties = iotProperties;
        this.httpClients = httpClients;
    }

    public <T extends IotResponse> T doPost(String url, IotRequest request) {
        Map<String, Object> headers = new HashMap<>();
        // request -> params
        Map<String, Object> params = new HashMap<>();
        HttpResponse httpResponse = httpClients.doPost(url, headers, params);
        // httpResponse -> iotResponse

        return null;
    }

    public <T extends IotResponse> T doGet(String url, IotRequest request) {
        Map<String, Object> headers = new HashMap<>();
        // request -> params
        Map<String, Object> params = new HashMap<>();
        HttpResponse httpResponse = httpClients.doGet(url, headers, params);
        // httpResponse -> iotResponse

        return null;
    }
}
