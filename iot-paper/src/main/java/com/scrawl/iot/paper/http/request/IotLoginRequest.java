package com.scrawl.iot.paper.http.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
@Setter
@Getter
public class IotLoginRequest {
    private String serverId;
    private String password;
}
