package com.jGod.imitationQQ.server.model.jdbc.bean;

import java.sql.Timestamp;

public class Delete {
    private int id;
    private int friend_id;
    private Timestamp date;

    public Delete() {

    }

    public Delete(int id, int friend_id,Timestamp date) {
        this.id = id;
        this.friend_id = friend_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
