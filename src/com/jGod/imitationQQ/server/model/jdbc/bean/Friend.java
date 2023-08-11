package com.jGod.imitationQQ.server.model.jdbc.bean;

public class Friend {
    private int id;
    private int friend_id;
    private String remark;

    public Friend() {

    }

    public Friend(int id, int friend_id, String remark) {
        this.id = id;
        this.friend_id = friend_id;
        this.remark = remark;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
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
}
