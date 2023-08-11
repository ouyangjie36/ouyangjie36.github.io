package com.jGod.imitationQQ.client.Bean;

public class Delete {
    private int id;
    private String name;
    private int friend_id;
    private String friend_name;
    private long date;

    public Delete() {

    }

    public Delete(int id, String name, int friend, String friendName, long date) {
        this.id = id;
        this.name = name;
        this.friend_id = friend;
        this.friend_name = friendName;
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

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }


    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
