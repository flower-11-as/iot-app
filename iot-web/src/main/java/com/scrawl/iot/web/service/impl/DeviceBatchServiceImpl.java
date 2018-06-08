package com.scrawl.iot.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.scrawl.iot.paper.utils.ExcelUtil;
import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.dao.entity.DeviceBatch;
import com.scrawl.iot.web.dao.mapper.DeviceBatchMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.DeviceBatchService;
import com.scrawl.iot.web.vo.PageReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Desc:
 * Create by scrawl on 2018/6/8
 */
@Service
@Slf4j
public class DeviceBatchServiceImpl implements DeviceBatchService {

    @Autowired
    private DeviceBatchMapper deviceBatchMapper;

    @Override
    public List<DeviceBatch> list(PageReqVO reqVO) {
        return deviceBatchMapper.selectPageList(reqVO);
    }

    @Override
    public int count(PageReqVO reqVO) {
        return deviceBatchMapper.selectPageCount(reqVO);
    }

    @Override
    public void batchAdd(InputStream is, Integer managerId) {
        // TODO[as]：暂时写死后续修改
        String serverId = "jiayingdev01";
        // 解析文件流 获取data_list
        // 生成xlsx文件
        List<List<Object>> excelDataList;
        try {
            excelDataList = ExcelUtil.readExcel(is);
            // 去掉header
            excelDataList.remove(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("SYS14002");
        }

        if (excelDataList.size() == 0) {
            throw new BizException("SYS14002");
        }


        List<Device> dataList = new ArrayList<>();
        excelDataList.forEach(list -> {
            Device device = new Device();
            device.setServerId(serverId);
            device.setDevSerial(list.get(0).toString());
            device.setName(list.get(1).toString());
            device.setDevType(list.get(2).toString());
            device.setConnectPointId(list.get(3).toString());
            device.setServiceMode(list.get(4).toString());
            device.setEndUserName(list.get(5).toString());
            device.setEndUserInfo(list.get(6).toString());
            device.setLocation(list.get(7).toString());
            device.setLongitude((double) list.get(8));
            device.setLatitude((double) list.get(9));
            device.setAreaCode(list.get(10).toString());

            dataList.add(device);
        });

        String dataListJson = JSON.toJSONString(dataList);

        // 组装device-batch接口调用参数，调用iot接口

        // 插入batch记录
        DeviceBatch deviceBatch = new DeviceBatch();
        deviceBatch.setTaskId(Math.random() + "");
        deviceBatch.setDataList(dataListJson);
        deviceBatch.setcreateTime(new Date());
        deviceBatch.setcreateManager(managerId);
        deviceBatchMapper.insertSelective(deviceBatch);
    }

    @Override
    public DeviceBatch getByTaskId(String taskId) {
        return deviceBatchMapper.selectByTaskId(taskId);
    }

    @Override
    public void update(DeviceBatch deviceBatch) {
        deviceBatchMapper.updateByPrimaryKey(deviceBatch);
    }
}
