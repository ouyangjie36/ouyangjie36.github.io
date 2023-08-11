package com.jGod.imitationQQ.client.Bean;

public class GroupMessage {
    private int from_id;
    private int group_id;
    private String content;
    private int type;
    private long date;
    private int senderHead;
    private String senderName;
    private String senderRemark;

    public GroupMessage() {

    }

    public GroupMessage(int from_id, int group_id, String content, int type, long date,int senderHead,String senderName,String senderRemark) {
        this.from_id = from_id;
        this.group_id = group_id;
        this.content = content;
        this.type = type;
        this.date = date;
        this.senderHead = senderHead;
        this.senderName = senderName;
        this.senderRemark = senderRemark;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getSenderHead() {
        return senderHead;
    }

    public void setSenderHead(int senderHead) {
        this.senderHead = senderHead;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public String getSenderRemark() {
        return senderRemark;
    }

    public void setSenderRemark(String senderRemark) {
        this.senderRemark = senderRemark;
    }
}
