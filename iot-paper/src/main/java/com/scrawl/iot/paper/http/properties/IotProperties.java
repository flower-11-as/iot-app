package com.scrawl.iot.paper.http.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
@ConfigurationProperties("iot")
@Setter
@Getter
public class IotProperties {
    private String apiUri;
}
