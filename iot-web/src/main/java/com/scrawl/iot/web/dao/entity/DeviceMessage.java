package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class DeviceMessage {
    private Integer id;

    private Integer deviceId;

    private Integer syncId;

    private String devTypeMessageId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSyncId() {
        return syncId;
    }

    public void setSyncId(Integer syncId) {
        this.syncId = syncId;
    }

    public String getDevTypeMessageId() {
        return devTypeMessageId;
    }

    public void setDevTypeMessageId(String devTypeMessageId) {
        this.devTypeMessageId = devTypeMessageId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}