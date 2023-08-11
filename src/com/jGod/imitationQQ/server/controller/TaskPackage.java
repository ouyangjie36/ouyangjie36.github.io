package com.jGod.imitationQQ.server.controller;

import java.util.HashMap;

public class TaskPackage {
    private int id;
    private int len;
    private HashMap<String, String> hashMap;

    public TaskPackage() {

    }

    public TaskPackage(int id, int len, HashMap<String, String> hashMap) {
        this.id = id;
        this.len = len;
        this.hashMap = hashMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }

    public String getInfo(String s){
        if(hashMap.get(s)==null) throw new RuntimeException();
        return hashMap.get(s);
    }

    @Override
    public String toString() {
        return "TaskPackage{" +
                "id=" + id +
                ", len=" + len +
                ", hashMap=" + hashMap +
                '}';
    }
}
