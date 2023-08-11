package com.jGod.imitationQQ.client.Bean;

import java.util.HashMap;

public class Group {
    private int id;
    private String name;
    private int head;
    private int number;
    private int master_id;
    private HashMap<Integer,User> hashMap = new HashMap<>();

    public Group() {

    }

    public Group(int id, String name, int head, int number, int master_id, HashMap<Integer, User> hashMap) {
        this.id = id;
        this.name = name;
        this.head = head;
        this.number = number;
        this.master_id = master_id;
        this.hashMap = hashMap;
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

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMaster_id() {
        return master_id;
    }

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public HashMap<Integer, User> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, User> hashMap) {
        this.hashMap = hashMap;
    }
}
