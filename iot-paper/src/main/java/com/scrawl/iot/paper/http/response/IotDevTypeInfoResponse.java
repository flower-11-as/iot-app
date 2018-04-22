package com.scrawl.iot.paper.http.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Getter
@Setter
public class IotDevTypeInfoResponse extends IotResponse {
    private List<IotCommandList> commandList;
    private List<IotMessageList> messageList;

    @Getter
    @Setter
    public class IotCommandList {
        private List<IotParam> responseParamList;
        private List<IotParam> requestParamList;
        private String commandName;
    }

    @Getter
    @Setter
    public class IotMessageList {
        private List<IotParam> paramList;
        private String messageName;
    }

    @Getter
    @Setter
    public static class IotParam {
        private String dataType;
        private String paramName;
        private Integer pos;
    }
}
