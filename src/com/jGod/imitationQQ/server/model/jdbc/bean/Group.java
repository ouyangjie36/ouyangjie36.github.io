package com.jGod.imitationQQ.server.model.jdbc.bean;

public class Group {
    private int id;
    private String name;
    private int number;
    private int head;
    private int master_id;

    public Group() {

    }

    public Group(int id, String name, int number,int master_id) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.master_id = master_id;
    }

    public int getMaster_id() {
        return master_id;
    }

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }
}
