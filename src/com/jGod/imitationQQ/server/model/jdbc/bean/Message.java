package com.jGod.imitationQQ.server.model.jdbc.bean;

import java.sql.Timestamp;

public class Message {
    private int id;
    private int from_id;
    private String content;
    private Timestamp date;

    public Message() {

    }

    public Message(int id, int from_id, String content, Timestamp date) {
        this.id = id;
        this.from_id = from_id;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
