package com.scrawl.iot.web.congufuration;

import com.scrawl.iot.web.helper.ApplicationContextHelper;
import com.scrawl.iot.web.resolver.IotHandlerExceptionResolver;
import com.scrawl.iot.web.resolver.ReloadableMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * Description:
 * Created by as on 2018/4/3.
 */
@Configuration
public class IotConfiguration {

    @Bean
    public ReloadableMessageSource iotReloadableMessageSource() {
        return new ReloadableMessageSource();
    }

    @Bean
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }

    @Bean
    public DefaultHandlerExceptionResolver defaultHandlerExceptionResolver() {
        return new IotHandlerExceptionResolver(iotReloadableMessageSource());
    }
}
