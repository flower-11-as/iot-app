package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
@Setter
@Getter
public class IotCommandResponse extends IotResponse {
    private String commandId;
}
