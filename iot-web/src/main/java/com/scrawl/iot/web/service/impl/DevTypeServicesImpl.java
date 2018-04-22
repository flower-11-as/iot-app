package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.response.IotDevTypeInfoResponse;
import com.scrawl.iot.paper.http.response.IotDevTypeResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.DevType;
import com.scrawl.iot.web.dao.entity.DevTypeInfo;
import com.scrawl.iot.web.dao.mapper.DevTypeInfoMapper;
import com.scrawl.iot.web.dao.mapper.DevTypeMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.DevTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Service
@Slf4j
public class DevTypeServicesImpl implements DevTypeService {

    @Autowired
    private DevTypeMapper devTypeMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IotHttpService iotHttpService;

    @Autowired
    private DevTypeInfoMapper insertSelective;

    @Override
    public List<DevType> list(DevType devType) {
        return devTypeMapper.selectBySelective(devType);
    }

    @Override
    public void syncDevTypes(List<String> serverIds, Integer managerId) {
        if (null == serverIds) {
            return;
        }

        serverIds.forEach(serverId -> {
            try {
                syncDevTypesByServerId(serverId, managerId);
            } catch (Exception e) {
                log.error("同步产品类型异常：", e);
                // ignore
            }
        });
    }

    // 根据账户同步产品型号
    private void syncDevTypesByServerId(String serverId, Integer managerId) {
        /*
         * 1、获取所有产品型号
         * 2、同步该账户下所有产品型号（加、删）
         * 3、同步产品型号下所有产品信息（加）
         */
        Account account = accountService.getAndAuthAccount(serverId);
        if (null == account) {
            throw new BizException("SYS80001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        Map<String, Object> params = new HashMap<>();
        params.put("serverID", "account.getServerId()");
        IotDevTypeResponse response = iotHttpService.getDevTypes(params, header);
        response.getDevTypes().forEach(d -> {
            getSelfProxy().syncDevType(d, account, managerId);
        });
    }

    private DevTypeServicesImpl getSelfProxy() {
        return (DevTypeServicesImpl) AopContext.currentProxy();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncDevType(String d, Account account, Integer managerId) {
        // 判断是否已经存在
        DevType record = new DevType();
        record.setServerId(account.getServerId());
        record.setDevType(d);

        // 产品信号
        List<DevType> devTypes = devTypeMapper.selectBySelective(record);
        if (null != devTypes && devTypes.size() > 0) {
            DevType devType = devTypes.get(0);
            devType.setDelFlag((byte) 0);
            devTypeMapper.updateByPrimaryKey(devType);

            // TODO:暂时不考虑这种情况
        } else {
            DevType devType = new DevType();
            devType.setServerId(account.getServerId());
            devType.setDevType(d);
            devType.setDelFlag((byte) 0);
            devType.setCreateTime(new Date());
            devTypeMapper.insertSelective(devType);

            syncDevTypeInfo(devType, account, managerId);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncDevTypeInfo(DevType devType, Account account, Integer managerId) {
        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        Map<String, Object> params = new HashMap<>();
        params.put("devType", devType.getDevType());
        IotDevTypeInfoResponse response = iotHttpService.getDevType(params, header);
        response.getCommandList().forEach(iotCommandList -> {
            iotCommandList.getRequestParamList().forEach(iotParam -> {
                addDevTypeInfo(devType, (byte)2, iotCommandList.getCommandName(), iotParam, managerId);
            });

            iotCommandList.getResponseParamList().forEach(iotParam -> {
                addDevTypeInfo(devType, (byte)3, iotCommandList.getCommandName(), iotParam, managerId);
            });
        });

        response.getMessageList().forEach(iotMessageList -> {
            iotMessageList.getParamList().forEach(iotParam -> {
                addDevTypeInfo(devType, (byte)1, iotMessageList.getMessageName(), iotParam, managerId);
            });
        });
    }

    // 1、设备消息 2、指令参数 3、指令响应参数
    private void addDevTypeInfo(DevType devType, byte type, String name, IotDevTypeInfoResponse.IotParam iotParam, Integer managerId) {
        DevTypeInfo devTypeInfo = new DevTypeInfo();
        devTypeInfo.setDevTypeId(devType.getId());
        devTypeInfo.setName(name);
        devTypeInfo.setType(type);
        devTypeInfo.setParamName(iotParam.getParamName());
        devTypeInfo.setDataType(iotParam.getDataType());
        devTypeInfo.setPos(iotParam.getPos());
        devTypeInfo.setCreateManager(managerId);
        devTypeInfo.setCreateTime(new Date());

        insertSelective.insertSelective(devTypeInfo);
    }
}
