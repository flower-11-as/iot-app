package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.response.IotDevTypeInfoResponse;
import com.scrawl.iot.paper.http.response.IotDevTypeResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.*;
import com.scrawl.iot.web.dao.mapper.*;
import com.scrawl.iot.web.enums.DevTypeCommandTypeEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.DevTypeService;
import com.scrawl.iot.web.vo.iot.devType.DevTypeListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private DevTypeMessageMapper devTypeMessageMapper;

    @Autowired
    private DevTypeMessageParamMapper devTypeMessageParamMapper;

    @Autowired
    private DevTypeCommandMapper devTypeCommandMapper;

    @Autowired
    private DevTypeCommandParamMapper devTypeCommandParamMapper;

    @Override
    public List<DevType> list(DevTypeListReqVO reqVO) {
        return devTypeMapper.selectPageList(reqVO);
    }

    @Override
    public int count(DevTypeListReqVO reqVO) {
        return devTypeMapper.selectPageCount(reqVO);
    }

    @Override
    public void syncDevTypes(List<String> serverIds) {
        if (null == serverIds) {
            return;
        }

        serverIds.forEach(this::syncDevTypesByServerId);
    }

    // 根据账户同步产品型号
    private void syncDevTypesByServerId(String serverId) {
        /*
         * 1、获取所有产品型号
         * 2、同步该账户下所有产品型号（加、删）
         * 3、同步产品型号下所有产品信息（加）
         */
        Account account = accountService.getAndAuthAccount(serverId);
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        Map<String, Object> params = new HashMap<>();
        params.put("serverID", account.getServerId());
        IotDevTypeResponse response = iotHttpService.getDevTypes(params, header);
        response.getDevTypes().forEach(d -> {
            getSelfProxy().syncDevType(d, account);
        });
    }

    private DevTypeServicesImpl getSelfProxy() {
        return (DevTypeServicesImpl) AopContext.currentProxy();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncDevType(String d, Account account) {
        // 判断是否已经存在
        DevType record = new DevType();
        record.setServerId(account.getServerId());
        record.setDevType(d);

        // 产品型号
        List<DevType> devTypes = devTypeMapper.selectBySelective(record);
        DevType devType;
        if (null != devTypes && devTypes.size() > 0) {
            devType = devTypes.get(0);
            devType.setDelFlag((byte) 0);
            devTypeMapper.updateByPrimaryKey(devType);
        } else {
            devType = new DevType();
            devType.setServerId(account.getServerId());
            devType.setDevType(d);
            devType.setDelFlag((byte) 0);
            devType.setCreateTime(new Date());
            devTypeMapper.insertSelective(devType);
        }

        syncDevTypeInfo(devType, account);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncDevTypeInfo(DevType devType, Account account) {
        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        Map<String, Object> params = new HashMap<>();
        params.put("devType", devType.getDevType());
        IotDevTypeInfoResponse response = iotHttpService.getDevType(params, header);

        // 设备消息
        response.getMessageList().forEach(iotMessageList -> {
            syncDevTypeMessage(devType, iotMessageList.getMessageName(), iotMessageList.getParamList());
        });

        // 设备指令
        response.getCommandList().forEach(iotCommandList -> {
            syncDevTypeCommand(devType, iotCommandList.getCommandName(), DevTypeCommandTypeEnum.COMMAND_REQUEST, iotCommandList.getRequestParamList());
            syncDevTypeCommand(devType, iotCommandList.getCommandName(), DevTypeCommandTypeEnum.COMMAND_RESPONSE, iotCommandList.getResponseParamList());
        });
    }

    private void syncDevTypeMessage(DevType devType, String messageName, List<IotDevTypeInfoResponse.IotParam> paramList) {
        DevTypeMessage devTypeMessage = devTypeMessageMapper.selectByTypeIdAndName(devType.getId(), messageName);

        // 不存在该设备消息，添加
        if (null == devTypeMessage) {
            devTypeMessage = new DevTypeMessage();
            devTypeMessage.setDevTypeId(devType.getId());
            devTypeMessage.setMessageName(messageName);
            devTypeMessage.setCreateTime(new Date());

            devTypeMessageMapper.insertSelective(devTypeMessage);
        }

        Integer messageId = devTypeMessage.getId();

        paramList.forEach(param -> {
            DevTypeMessageParam messageParam = devTypeMessageParamMapper.selectByMessageIdAndName(messageId, param.getParamName());
            if (null == messageParam) {
                messageParam = new DevTypeMessageParam();
                messageParam.setMessageId(messageId);
                messageParam.setParamName(param.getParamName());
                messageParam.setDataType(param.getDataType());
                messageParam.setPos(param.getPos());
                messageParam.setCreateTime(new Date());

                devTypeMessageParamMapper.insertSelective(messageParam);
            }
        });
    }

    private void syncDevTypeCommand(DevType devType, String commandName, DevTypeCommandTypeEnum typeEnum, List<IotDevTypeInfoResponse.IotParam> paramList) {
        DevTypeCommand devTypeCommand = devTypeCommandMapper.selectByTypeIdAndCommandId(devType.getId(), commandName);

        // 不存在该设备消息，添加
        if (null == devTypeCommand) {
            devTypeCommand = new DevTypeCommand();
            devTypeCommand.setDevTypeId(devType.getId());
            devTypeCommand.setCommandName(commandName);
            devTypeCommand.setCreateTime(new Date());

            devTypeCommandMapper.insertSelective(devTypeCommand);
        }

        Integer commandId = devTypeCommand.getId();

        paramList.forEach(param -> {
            DevTypeCommandParam commandParam = devTypeCommandParamMapper.selectByCommandIdAndName(commandId, param.getParamName());
            if (null == commandParam) {
                commandParam = new DevTypeCommandParam();
                commandParam.setCommandId(commandId);
                commandParam.setParamName(param.getParamName());
                commandParam.setParamType(typeEnum.getType());
                commandParam.setDataType(param.getDataType());
                commandParam.setPos(param.getPos());
                commandParam.setCreateTime(new Date());

                devTypeCommandParamMapper.insertSelective(commandParam);
            }
        });
    }

    @Override
    public DevType get(Integer id) {
        return devTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DevType> getByServerIds(List<String> serverIds) {
        List<DevType> devTypeList = new ArrayList<>();
        if (null == serverIds) {
            return devTypeList;
        }

        serverIds.forEach(serverId -> {
            DevType record = new DevType();
            record.setServerId(serverId);
            devTypeList.addAll(devTypeMapper.selectBySelective(record));
        });

        return devTypeList;
    }
}
