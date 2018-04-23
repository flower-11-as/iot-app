package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class DeviceCommandDetail {
    private Integer id;

    private Integer commandid;

    private String paramName;

    private String paramValue;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommandid() {
        return commandid;
    }

    public void setCommandid(Integer commandid) {
        this.commandid = commandid;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}