package com.jGod.imitationQQ.server.controller;

import com.jGod.imitationQQ.server.model.jdbc.bean.User;

import java.util.List;

public class ManagerController implements Runnable{
    private ManagerController mc;

    public ManagerController(ManagerController mc) {
        this.mc = mc;
    }

    public ManagerController getMc() {
        return mc;
    }

    public void setMc(ManagerController mc) {
        this.mc = mc;
    }

    @Override
    public void run() {

    }

//    public void open(){
//        mc.open();
//    }
//
//    public void close(){
//        mc.close();
//    }
//
//    public List<User> getOnlineInfo(){
//        return null;
//    }
}
