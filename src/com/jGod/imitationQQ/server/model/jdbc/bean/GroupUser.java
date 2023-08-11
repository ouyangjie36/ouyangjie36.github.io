package com.jGod.imitationQQ.server.model.jdbc.bean;

public class GroupUser {
    private int id;
    private int user_id;
    private String remark;

    public GroupUser() {

    }

    public GroupUser(int id, String remark,int user_id) {
        this.id = id;
        this.remark = remark;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
