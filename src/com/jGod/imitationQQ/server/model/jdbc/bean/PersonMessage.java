package com.jGod.imitationQQ.server.model.jdbc.bean;

import java.sql.Timestamp;

public class PersonMessage extends Message{
    private int to_id;
    private int type;

    public PersonMessage() {

    }

    public PersonMessage(int to_id, int type) {
        this.to_id = to_id;
        this.type = type;
    }

    public PersonMessage(int id, int from_id, String content, Timestamp date, int to_id, int type1) {
        super(id, from_id, content, date);
        this.to_id = to_id;
        this.type = type1;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
