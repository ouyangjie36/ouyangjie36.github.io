package com.jGod.imitationQQ.client.Bean;

public class Application {
    private int id;
    private String text;
    private String name;
    private int head;

    public Application() {

    }

    public Application(int id, String text, String name, int head) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.head = head;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
