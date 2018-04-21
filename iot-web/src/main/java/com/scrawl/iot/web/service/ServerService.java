package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Server;

import java.util.List;

/**
 * Desc:
 * Create by scrawl on 2018/4/21
 */
public interface ServerService {
    List<Server> list();

    void syncServers();
}
