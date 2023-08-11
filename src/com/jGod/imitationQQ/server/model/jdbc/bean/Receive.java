package com.jGod.imitationQQ.server.model.jdbc.bean;

import java.sql.Timestamp;

public class Receive {
    private int id;
    private int applicant_id;
    private Timestamp date;
    private String text;

    public Receive() {

    }

    public Receive(int id, int applicant_id, Timestamp date,String text) {
        this.id = id;
        this.applicant_id = applicant_id;
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
