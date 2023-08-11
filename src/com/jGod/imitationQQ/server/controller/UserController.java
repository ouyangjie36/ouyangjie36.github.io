package com.jGod.imitationQQ.server.controller;

import com.jGod.imitationQQ.server.model.jdbc.bean.User;
import com.jGod.imitationQQ.server.model.service.ServiceMenu;
import com.jGod.imitationQQ.server.model.service.login.*;

import java.net.Socket;

import static com.jGod.imitationQQ.server.controller.MainController.networkPackage;
import static com.jGod.imitationQQ.server.controller.MainController.online;
import static java.lang.Thread.onSpinWait;
import static java.lang.Thread.sleep;


public class UserController implements Runnable {
    private User user;
    private Socket socket;
    private int state = 1; //1表示在线
    private int task = 0; //0表示无任务
    /*
        任务1：修改个人信息
        任务2：添加好友
        任务3：删除好友
        任务4：进入双人聊天窗口
        任务5：修改备注
        任务6：下线
        ----------------------
        任务7：发送信息
        任务8：发送文件
        任务9：修改群聊备注
        任务10：群聊广发
    * */

    public UserController(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (state == 1) {
            TaskPackage taskPackage = networkPackage.getPackage(socket);
            if (taskPackage != null) {
                task = taskPackage.getId();
            }
            switch (task) {
                case 0:
                    break;
                case 1:
                    ServiceMenu.getUserMenu().modifySelfInfo(user.getId(), taskPackage);
                    break;
                case 2:
                    ServiceMenu.getUserMenu().addFriend(user.getId(), taskPackage);
                    break;
                case 3:
                    ServiceMenu.getUserMenu().deleteFriend(user.getId(), taskPackage);
                    break;
                case 4:
                    ServiceMenu.getChatPerson().show(user.getId(), taskPackage);
                    break;
                case 5:
                    ServiceMenu.getUserMenu().modifyFriendRemark(user.getId(), taskPackage);
                    break;
                case 6:
                    if(user==null) return;
                    System.out.println(user.getId()+"-"+user.getName()+"下线");
                    ServiceMenu.getUserMenu().exit(user.getId());
                    break;
                case 7:
                    ServiceMenu.getChatPerson().getMessageFromClient(user.getId(), taskPackage);
                    break;
                case 8:
                    ServiceMenu.getChatGroup().modifySelfRemark(user.getId(), taskPackage);
                    break;
                case 9:
                    ServiceMenu.getUserMenu().createGroup(user.getId(), taskPackage);
                    break;
                case 10:
                    ServiceMenu.getChatGroup().disbandGroup(user.getId(), taskPackage);
                    break;
                case 11:
                    ServiceMenu.getUserMenu().scanApplication(user.getId(), taskPackage);
                    break;
                case 12:
                    ServiceMenu.getUserMenu().receiveApplication(user.getId(), taskPackage);
                    break;
                case 13:
                    ServiceMenu.getUserMenu().refuseApplication(user.getId(), taskPackage);
                    break;
                case 14:
                    ServiceMenu.getUserMenu().scanRefuse(user.getId(), taskPackage);
                    break;
                case 15:
                    ServiceMenu.getUserMenu().scanReceive(user.getId(), taskPackage);
                    break;
                case 16:
                    ServiceMenu.getUserMenu().deleteInformation(user.getId(), taskPackage);
                    break;
                case 17:
                    user = Login.login(socket, taskPackage);
                    if(user!=null){
                        online.put(user.getId(),this);
                        ServiceMenu.getUserMenu().show(user.getId());
                        ServiceMenu.getUserMenu().friendOnline(user.getId());
                    }
                    break;
                case 18:
                    Register.register(socket, taskPackage);
                    break;
                case 19:
                    ServiceMenu.getChatGroup().show(user.getId(),taskPackage);
                    break;
                case 20:
                    ServiceMenu.getUserMenu().addGroup(user.getId(),taskPackage,true);
                    break;
                case 21:
                    ServiceMenu.getUserMenu().exitGroup(user.getId(),Integer.parseInt(taskPackage.getInfo("group_id")));
                    break;
                case 22:
                    ServiceMenu.getChatGroup().getMessageFromClient(user.getId(),taskPackage);
                    break;
            }
            task = 0;
        }
    }


    public void setTask(int num) {
        task = num;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTask() {
        return task;
    }

    public void shutDown() {
        state = 0;
    }

    public boolean isOnline() {
        if (state == 1) {
            return true;
        }
        return false;
    }
}
