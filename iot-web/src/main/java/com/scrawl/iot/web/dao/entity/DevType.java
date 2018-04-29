package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class DevType {
    private Integer id;

    private String serverId;

    private String devType;

    private Byte delFlag;

    private String alarmService;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getAlarmService() {
        return alarmService;
    }

    public void setAlarmService(String alarmService) {
        this.alarmService = alarmService;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}