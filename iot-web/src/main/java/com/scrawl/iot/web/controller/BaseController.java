package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/4/9.
 */
@Component
public class BaseController {

    @Autowired
    private AccountService accountService;

    Manager getManager() {
        Object object = SecurityUtils.getSubject().getPrincipal();
        return (Manager) object;
    }

    Integer getManagerId() {
        return getManager().getId();
    }

    List<String> getManagerServerIds() {
        return accountService.getManagerAccountList(getManagerId()).
                stream().map(Account::getServerId).collect(Collectors.toList());
    }
}
