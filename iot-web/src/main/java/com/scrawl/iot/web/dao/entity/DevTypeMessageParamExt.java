package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class DevTypeMessageParamExt {
    private Integer id;

    private Integer messageParamId;

    private String json;

    private Integer createManager;

    private Date createTime;

    private Integer updateManager;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageParamId() {
        return messageParamId;
    }

    public void setMessageParamId(Integer messageParamId) {
        this.messageParamId = messageParamId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Integer getCreateManager() {
        return createManager;
    }

    public void setCreateManager(Integer createManager) {
        this.createManager = createManager;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateManager() {
        return updateManager;
    }

    public void setUpdateManager(Integer updateManager) {
        this.updateManager = updateManager;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}