package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.ManagerAccount;
import com.scrawl.iot.web.dao.mapper.ManagerAccountMapper;
import com.scrawl.iot.web.service.ManagerAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Service
@Slf4j
public class ManagerAccountServiceImpl implements ManagerAccountService {

    @Autowired
    private ManagerAccountMapper managerAccountMapper;

    @Override
    public List<ManagerAccount> list() {
        return managerAccountMapper.selectBySelective(new ManagerAccount());
    }

    @Override
    public List<ManagerAccount> managerList(Integer managerId) {
        ManagerAccount managerAccount = new ManagerAccount();
        managerAccount.setManagerId(managerId);
        return managerAccountMapper.selectBySelective(managerAccount);
    }
}
