package com.jGod.imitationQQ.server.model.service.serviceInterface;

public interface ManagerMenuInterface extends Service{
    void showOnline(); //显示在线情况

    void kickOut(int id); //强制下线

    void showMessage(); //显示用户发送消息情况

    void showLog(); //显示日志

    void broadcastMessage(); //广播消息

    void open(); //开启服务

    void close(); //关闭服务
}
