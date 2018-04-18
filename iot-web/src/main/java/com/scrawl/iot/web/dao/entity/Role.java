package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class Role {
    private Integer id;

    private String name;

    private Date createTime;

    private Integer createManager;

    private Date updateTime;

    private Integer updateManager;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateManager() {
        return createManager;
    }

    public void setCreateManager(Integer createManager) {
        this.createManager = createManager;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateManager() {
        return updateManager;
    }

    public void setUpdateManager(Integer updateManager) {
        this.updateManager = updateManager;
    }
}