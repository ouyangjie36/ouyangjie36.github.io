package com.jGod.imitationQQ.server.model.jdbc.bean;

public class Head {
    private int id;
    private String url;

    public Head() {

    }

    public Head(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
