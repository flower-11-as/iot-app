package com.scrawl.iot.paper.configuration;

import com.scrawl.iot.paper.http.client.IotHttpClient;
import com.scrawl.iot.paper.http.client.PaperHttpClient;
import com.scrawl.iot.paper.http.properties.IotProperties;
import com.scrawl.iot.paper.http.service.IotHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 * Create by scrawl on 2018/4/19
 */
@Configuration
@EnableConfigurationProperties({IotProperties.class})
public class PaperConfiguration {

    @Autowired
    private IotProperties properties;

    @Bean
    public PaperHttpClient paperHttpClient() {
        return new PaperHttpClient();
    }

    @Bean
    public IotHttpClient iotHttpClient() {
        return new IotHttpClient(properties, paperHttpClient());
    }

    @Bean
    public IotHttpService iotHttpService() {
        return new IotHttpService(iotHttpClient());
    }
}
