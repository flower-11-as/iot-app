package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class DeviceCommand {
    private Integer id;

    private Integer deviceId;

    private Byte status;

    private String reqCommandId;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getReqCommandId() {
        return reqCommandId;
    }

    public void setReqCommandId(String reqCommandId) {
        this.reqCommandId = reqCommandId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}