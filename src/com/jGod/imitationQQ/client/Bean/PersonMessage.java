package com.jGod.imitationQQ.client.Bean;

public class PersonMessage {
    private int from_id;
    private int to_id;
    private String content;
    private int type;
    private long date;
    private String name;
    private String friendName;
    private int senderHead;

    public PersonMessage() {

    }

    public PersonMessage(int from_id, int to_id, String content, int type, long date) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.content = content;
        this.type = type;
        this.date = date;
    }

    public PersonMessage(int from_id, int to_id, String content, int type, long date, String name, String friendName, int senderHead) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.content = content;
        this.type = type;
        this.date = date;
        this.name = name;
        this.friendName = friendName;
        this.senderHead = senderHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getSenderHead() {
        return senderHead;
    }

    public void setSenderHead(int senderHead) {
        this.senderHead = senderHead;
    }
}
