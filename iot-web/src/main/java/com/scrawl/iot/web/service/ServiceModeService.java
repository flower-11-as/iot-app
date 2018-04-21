package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.ServiceMode;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
public interface ServiceModeService {

    List<ServiceMode> list();

    void syncServiceModes();
}
