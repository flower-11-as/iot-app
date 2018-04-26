package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class DevTypeCommandParamExt {
    private Integer id;

    private Integer commandParamId;

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

    public Integer getCommandParamId() {
        return commandParamId;
    }

    public void setCommandParamId(Integer commandParamId) {
        this.commandParamId = commandParamId;
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