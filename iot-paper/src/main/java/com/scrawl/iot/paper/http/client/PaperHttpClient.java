package com.scrawl.iot.paper.http.client;

import com.alibaba.fastjson.JSONObject;
import com.scrawl.iot.paper.exception.PaperHttpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
@Slf4j
public class PaperHttpClient {

    private CloseableHttpClient httpClient;

    public PaperHttpClient() {
        this.httpClient = HttpClients.createDefault();
    }

    public HttpResponse doPost(String url, Map<String, Object> headers, Map<String, Object> params) {
        return doRequest(RequestMethodEnum.POST, url, headers, params);
    }

    public HttpResponse doGet(String url, Map<String, Object> headers, Map<String, Object> params) {
        return doRequest(RequestMethodEnum.GET, url, headers, params);
    }

    public HttpResponse doDelete(String url, Map<String, Object> headers, Map<String, Object> params) {
        return doRequest(RequestMethodEnum.DELETE, url, headers, params);
    }

    public HttpResponse doPut(String url, Map<String, Object> headers, Map<String, Object> params) {
        return doRequest(RequestMethodEnum.PUT, url, headers, params);
    }

    private HttpResponse doRequest(RequestMethodEnum requestMethod, String url, Map<String, Object> headers, Map<String, Object> params) {
        HttpUriRequest httpRequest;

        if (requestMethod == RequestMethodEnum.POST) {
            StringEntity entity = createEntity(params);
            httpRequest = RequestBuilder.create(requestMethod.name()).setUri(url).setEntity(entity).build();
        } else if (requestMethod == RequestMethodEnum.PUT) {
            StringEntity entity = createEntity(params);
            httpRequest = RequestBuilder.create(requestMethod.name()).setUri(url).setEntity(entity).build();
        } else if (requestMethod == RequestMethodEnum.GET) {
            List<BasicNameValuePair> paris = createPairs(params);
            httpRequest = RequestBuilder.create(requestMethod.name()).setUri(url).addParameters(paris.toArray(new BasicNameValuePair[0])).build();
        } else if (requestMethod == RequestMethodEnum.DELETE) {
            List<BasicNameValuePair> paris = createPairs(params);
            httpRequest = RequestBuilder.create(requestMethod.name()).setUri(url).addParameters(paris.toArray(new BasicNameValuePair[0])).build();
        } else {
            throw new PaperHttpException("请求方式不存在");
        }

        addHeaders(httpRequest, headers);
        CloseableHttpResponse resp;
        try {
            resp = httpClient.execute(httpRequest);
        } catch (IOException e) {
            log.error("paper http request error", e);
            throw new PaperHttpException("http请求异常");
        }

        return resp;
    }

    enum RequestMethodEnum {
        POST,
        GET,
        DELETE,
        PUT
    }

    private List<BasicNameValuePair> createPairs(Map<String, Object> params) {
        List<BasicNameValuePair> formPairs = new ArrayList<>();
        params.forEach((k, v) -> {
            if (v == null) {
                return;
            }
            BasicNameValuePair pair = new BasicNameValuePair(k, v.toString());
            formPairs.add(pair);
        });
        return formPairs;
    }

    private StringEntity createEntity(Map<String, Object> params) {
        StringEntity entity = null;
        if (!params.isEmpty()) {
            try {
                entity = new StringEntity(JSONObject.toJSONString(params), "utf-8");
            } catch (UnsupportedCharsetException e) {
                log.error("create entity error:{}", e.getMessage());
                throw new PaperHttpException("create entity error", e);
            }
        }
        return entity;
    }

    private void addHeaders(HttpRequest httpRequest, Map<String, Object> headers) {
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String, Object>> entrySet = headers.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                httpRequest.addHeader(key, value);
            }
        }
    }
}
