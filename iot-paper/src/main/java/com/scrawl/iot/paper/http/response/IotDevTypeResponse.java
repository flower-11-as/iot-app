package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Setter
@Getter
public class IotDevTypeResponse extends IotResponse {
    private List<String> devTypes;
}
