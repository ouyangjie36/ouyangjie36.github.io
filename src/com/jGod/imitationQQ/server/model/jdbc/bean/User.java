package com.jGod.imitationQQ.server.model.jdbc.bean;

public class User {
    private int id;
    private String name;
    private int head;
    private String birthday;
    private String gender;
    private int age;
    private int state = 0;

    public User(){

    }

    public User(int id, String name, int head, String birthday, String gender,int age, int state) {
        this.id = id;
        this.name = name;
        this.head = head;
        this.birthday = birthday;
        this.gender = gender;
        this.age = age;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String  gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
