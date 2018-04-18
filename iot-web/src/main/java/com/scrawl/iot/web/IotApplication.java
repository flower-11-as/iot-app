package com.scrawl.iot.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description:
 * Created by as on 2018/3/11.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.scrawl.iot.web.dao.mapper")
public class IotApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotApplication.class);
    }
}
