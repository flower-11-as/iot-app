package com.scrawl.iot.web.service.impl;

import com.mysql.fabric.ServerMode;
import com.scrawl.iot.paper.exception.IotHttpException;
import com.scrawl.iot.paper.exception.PaperHttpException;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.response.IotServerResponse;
import com.scrawl.iot.paper.http.response.IotServiceModeResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.Server;
import com.scrawl.iot.web.dao.entity.ServiceMode;
import com.scrawl.iot.web.dao.mapper.ServiceModeMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.ServerService;
import com.scrawl.iot.web.service.ServiceModeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class ServiceModeServiceImpl implements ServiceModeService {

    @Autowired
    private ServiceModeMapper serviceModeMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IotHttpService iotHttpService;

    @Autowired
    private ServerService serverService;

    @Override
    public List<ServiceMode> list() {
        return serviceModeMapper.selectList();
    }

    @Override
    @Transactional
    public void syncServiceModes() {
        // 获取一个有效账户
        Account account = accountService.getAvailableAccount();
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());
        IotServiceModeResponse response;

        List<Server> servers = serverService.list();
        for (Server server : servers) {
            try {
                Map<String, Object> urlParams = new HashMap<>();
                urlParams.put("iotserverId", server.getServerId());
                response = iotHttpService.getServiceModes(urlParams, header);
            } catch (IotHttpException | PaperHttpException e) {
                throw new BizException("SYS60001", e.getMessage());
            }

            if (null != response.getServiceModeList()) {
                serviceModeMapper.deleteByServerId(server.getServerId());

                Date now = new Date();
                response.getServiceModeList().forEach(iotServiceMode -> {
                    ServiceMode serviceMode = new ServiceMode();
                    serviceMode.setServiceMode(iotServiceMode.getServiceMode());
                    serviceMode.setDescription(iotServiceMode.getDesc());
                    serviceMode.setIsDefault(iotServiceMode.getIsDefault());
                    serviceMode.setServerId(server.getServerId());
                    serviceMode.setCreateTime(now);

                    serviceModeMapper.insertSelective(serviceMode);
                });
            }
        }
    }
}
