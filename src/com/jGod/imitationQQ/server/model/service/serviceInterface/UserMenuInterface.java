package com.jGod.imitationQQ.server.model.service.serviceInterface;

import com.jGod.imitationQQ.server.controller.TaskPackage;


public interface UserMenuInterface extends Service{
    void show(int id);//显示主界面

    void addFriend(int id, TaskPackage taskPackage);//添加好友

    void deleteFriend(int id,TaskPackage taskPackage); //删除好友

    void modifySelfInfo(int id, TaskPackage taskPackage); //修改个人信息

    void modifyFriendRemark(int id,TaskPackage taskPackage); //修改好友备注

    void createGroup(int id, TaskPackage taskPackage); //创建群聊
    void addGroup(int id,TaskPackage taskPackage,boolean show); //加入群聊

    void exit(int id); //退出

    void scanApplication(int id,TaskPackage taskPackage); //查看好友申请

    void receiveApplication(int id,TaskPackage taskPackage); //接受请求

    void refuseApplication(int id, TaskPackage taskPackage); //拒绝请求

    void scanReceive(int id,TaskPackage taskPackage); //查看接受添加好友

    void scanRefuse(int id, TaskPackage taskPackage); //查看拒绝添加好友

    void deleteInformation(int id, TaskPackage taskPackage); //查看删除信息
}
