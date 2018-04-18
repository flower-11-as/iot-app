package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Manager;
import org.apache.shiro.SecurityUtils;

/**
 * Description:
 * Created by as on 2018/4/9.
 */
public class BaseController {

    Manager getManager() {
        Object object = SecurityUtils.getSubject().getPrincipal();
        return (Manager) object;
    }

    Integer getManagerId() {
        return getManager().getId();
    }

}
