package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.DevTypeInfo;
import com.scrawl.iot.web.dao.mapper.DevTypeInfoMapper;
import com.scrawl.iot.web.enums.DevTypeInfoTypeEnum;
import com.scrawl.iot.web.service.DevTypeInfoService;
import com.scrawl.iot.web.vo.iot.devType.DevTypeCommandInfoRespVO;
import com.scrawl.iot.web.vo.iot.devType.DevTypeMessageInfoRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Service
@Slf4j
public class DevTypeInfoServiceImpl implements DevTypeInfoService {
    @Autowired
    private DevTypeInfoMapper devTypeInfoMapper;

    @Override
    public List<DevTypeMessageInfoRespVO> getDevTypeMessages(Integer devTypeId) {
        DevTypeInfo record = new DevTypeInfo();
        record.setDevTypeId(devTypeId);
        record.setType(DevTypeInfoTypeEnum.MESSAGE_REQUEST.getType());
        List<DevTypeInfo> devTypeInfos = devTypeInfoMapper.selectBySelective(record);

        Map<String, List<DevTypeInfo>> nameMap = devTypeInfos.stream().collect(Collectors.groupingBy(DevTypeInfo::getName));
        List<DevTypeMessageInfoRespVO> rs = new ArrayList<>();
        nameMap.forEach((name, infos) -> {
            DevTypeMessageInfoRespVO respVO = new DevTypeMessageInfoRespVO();
            respVO.setName(name);
            respVO.setMessageList(infos);

            rs.add(respVO);
        });

        return rs;
    }

    @Override
    public List<DevTypeCommandInfoRespVO> getDevTypeCommands(Integer devTypeId) {
        // 指令参数
        DevTypeInfo record = new DevTypeInfo();
        record.setDevTypeId(devTypeId);
        record.setType(DevTypeInfoTypeEnum.COMMAND_REQUEST.getType());
        List<DevTypeInfo> devTypeInfos = devTypeInfoMapper.selectBySelective(record);

        // 指令响应参数
        record.setType(DevTypeInfoTypeEnum.COMMAND_RESPONSE.getType());
        devTypeInfos.addAll(devTypeInfoMapper.selectBySelective(record));

        Map<String, List<DevTypeInfo>> nameMap = devTypeInfos.stream().collect(Collectors.groupingBy(DevTypeInfo::getName));
        List<DevTypeCommandInfoRespVO> rs = new ArrayList<>();
        nameMap.forEach((name, infos) -> {
            DevTypeCommandInfoRespVO respVO = new DevTypeCommandInfoRespVO();
            respVO.setName(name);

            Map<Byte, List<DevTypeInfo>> typeMap = devTypeInfos.stream().collect(Collectors.groupingBy(DevTypeInfo::getType));

            respVO.setRequestList(typeMap.get(DevTypeInfoTypeEnum.COMMAND_REQUEST.getType()));
            respVO.setResponseList(typeMap.get(DevTypeInfoTypeEnum.COMMAND_RESPONSE.getType()));

            rs.add(respVO);
        });

        return rs;
    }
}
