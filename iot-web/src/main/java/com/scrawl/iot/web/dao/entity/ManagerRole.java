package com.scrawl.iot.web.dao.entity;

import java.util.Date;

public class ManagerRole {
    private Integer id;

    private Integer managerId;

    private Integer roleId;

    private Date createTime;

    private Integer createManager;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
}