package com.scrawl.iot.paper.http.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
@Setter
@Getter
public class IotSubscribeRequest {
    private String callbackUrl;
}
