package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/21.
 */
@Getter
@Setter
public class IotServiceModeResponse extends IotResponse {
    private List<IotServiceMode> serviceModeList;

    @Setter
    @Getter
    public class IotServiceMode{
        private String serviceMode;
        private String desc;
        private Integer isDefault;
    }
}
