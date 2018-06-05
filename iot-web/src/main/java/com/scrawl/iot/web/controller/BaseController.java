package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.ManagerAccount;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.ManagerAccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/4/9.
 */
@Component
public class BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ManagerAccountService managerAccountService;

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

    List<String> getManagerEndUserNames() {
        List<ManagerAccount> managerAccounts = managerAccountService.listByManagerId(getManagerId());
        Set<String> endUserNameSet = new HashSet<>();
        managerAccounts.forEach(managerAccount -> {
            String endUserNameStr = managerAccount.getEndUserName();
            if (StringUtils.isEmpty(endUserNameStr)) {
                return;
            }

            endUserNameSet.addAll(Arrays.asList(endUserNameStr.split(",")));
        });

        return new ArrayList<>(endUserNameSet);
    }
}
