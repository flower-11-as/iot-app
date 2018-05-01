package com.scrawl.iot.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Description:
 * Created by as on 2018/3/11.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.scrawl.iot.web.dao.mapper")
@EnableAspectJAutoProxy(proxyTargetClass=true, exposeProxy=true)
public class IotApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(IotApplication.class);
    }
}
