package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.DevType;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
public interface DevTypeService {

    List<DevType> list(DevType devType);

    void syncDevTypes(List<String> serverIds, Integer managerId);

    void syncDevType(String d, Account account, Integer managerId);

    void syncDevTypeInfo(DevType devType, Account account, Integer managerId);
}
