package com.jGod.imitationQQ.server.model.jdbc.bean;

import java.sql.Timestamp;

public class GroupMessage extends Message{
    private int group_id;
    private int type;

    public GroupMessage() {

    }

    public GroupMessage(int group_id, int type) {
        this.group_id = group_id;
        this.type = type;
    }

    public GroupMessage(int id, int from_id, String content, Timestamp date, int group_id, int type1) {
        super(id, from_id, content, date);
        this.group_id = group_id;
        this.type = type1;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
