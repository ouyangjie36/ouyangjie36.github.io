package com.jGod.imitationQQ.server.model.jdbc.bean;

public class Application {
    private int uid;
    private int applicant_id;
    private String text;
    private int head;
    private String name;

    public Application() {

    }

    public Application(int uid, int applicant_id, String information) {
        this.uid = uid;
        this.applicant_id = applicant_id;
        this.text = information;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Application{" +
                "uid=" + uid +
                ", applicant_id=" + applicant_id +
                ", text='" + text + '\'' +
                ", head=" + head +
                ", name='" + name + '\'' +
                '}';
    }
}
