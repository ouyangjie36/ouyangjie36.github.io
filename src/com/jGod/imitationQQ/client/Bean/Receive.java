package com.jGod.imitationQQ.client.Bean;

public class Receive {
    private int id;
    private String name;
    private int applicant_id;
    private String applicant_name;
    private String text;
    private long date;

    public Receive() {

    }

    public Receive(int id, String name, int applicant_id, String applicant_name, String text, long date) {
        this.id = id;
        this.name = name;
        this.applicant_id = applicant_id;
        this.applicant_name = applicant_name;
        this.text = text;
        this.date = date;
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

    public int getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
