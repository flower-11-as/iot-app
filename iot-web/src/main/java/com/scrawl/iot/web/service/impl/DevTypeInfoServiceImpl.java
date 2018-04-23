package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.DevTypeCommand;
import com.scrawl.iot.web.dao.entity.DevTypeMessage;
import com.scrawl.iot.web.dao.mapper.DevTypeCommandMapper;
import com.scrawl.iot.web.dao.mapper.DevTypeCommandParamMapper;
import com.scrawl.iot.web.dao.mapper.DevTypeMessageMapper;
import com.scrawl.iot.web.dao.mapper.DevTypeMessageParamMapper;
import com.scrawl.iot.web.enums.DevTypeCommandTypeEnum;
import com.scrawl.iot.web.service.DevTypeInfoService;
import com.scrawl.iot.web.vo.iot.devType.DevTypeCommandInfoRespVO;
import com.scrawl.iot.web.vo.iot.devType.DevTypeMessageInfoRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Service
@Slf4j
public class DevTypeInfoServiceImpl implements DevTypeInfoService {
    @Autowired
    private DevTypeMessageMapper devTypeMessageMapper;

    @Autowired
    private DevTypeMessageParamMapper devTypeMessageParamMapper;

    @Autowired
    private DevTypeCommandMapper devTypeCommandMapper;

    @Autowired
    private DevTypeCommandParamMapper devTypeCommandParamMapper;

    @Override
    public List<DevTypeMessageInfoRespVO> getDevTypeMessages(Integer devTypeId) {
        List<DevTypeMessageInfoRespVO> rs = new ArrayList<>();

        List<DevTypeMessage> messages = devTypeMessageMapper.selectByTypeId(devTypeId);
        messages.forEach(message -> {
            DevTypeMessageInfoRespVO respVO = new DevTypeMessageInfoRespVO();
            respVO.setMessage(message);
            respVO.setMessageList(devTypeMessageParamMapper.selectByMessageId(message.getId()));

            rs.add(respVO);
        });

        return rs;
    }

    @Override
    public List<DevTypeCommandInfoRespVO> getDevTypeCommands(Integer devTypeId) {
        List<DevTypeCommandInfoRespVO> rs = new ArrayList<>();

        List<DevTypeCommand> commands = devTypeCommandMapper.selectByTypeId(devTypeId);
        commands.forEach(command -> {
            DevTypeCommandInfoRespVO respVO = new DevTypeCommandInfoRespVO();
            respVO.setCommand(command);
            respVO.setRequestList(devTypeCommandParamMapper.selectByCommandIdAndType(command.getId(),
                    DevTypeCommandTypeEnum.COMMAND_REQUEST.getType()));
            respVO.setResponseList(devTypeCommandParamMapper.selectByCommandIdAndType(command.getId(),
                    DevTypeCommandTypeEnum.COMMAND_RESPONSE.getType()));

            rs.add(respVO);
        });

        return rs;
    }

    @Override
    public boolean updateMessage(DevTypeMessage devTypeMessage) {
        return devTypeMessageMapper.updateByPrimaryKeySelective(devTypeMessage) > 0;
    }

    @Override
    public boolean updateCommand(DevTypeCommand devTypeCommand) {
        return devTypeCommandMapper.updateByPrimaryKeySelective(devTypeCommand) > 0;
    }
}
