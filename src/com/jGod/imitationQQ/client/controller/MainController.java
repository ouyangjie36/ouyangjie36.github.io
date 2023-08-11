package com.jGod.imitationQQ.client.controller;

import com.jGod.imitationQQ.client.Bean.Group;
import com.jGod.imitationQQ.client.Bean.User;
import com.jGod.imitationQQ.client.view.*;

import javax.swing.text.View;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import static java.lang.Thread.sleep;

@SuppressWarnings("all")
public class MainController {
    public static Socket socket = null;
    public static boolean state = true;
    public static NetworkPackage networkPackage = new NetworkPackage();
    /*----------------*/
    private MainView mainView;
    private ApplicationView applicationView;
    private ReceiveView receiveView;
    private RefuseView refuseView;
    private DeleteView deleteView;

    public static void main(String[] args) throws IOException, InterruptedException {
        MainController mainController = new MainController();
        socket = new Socket("http://32661240.r3.cpolar.top",8001);
        networkPackage.setSocket(socket);
        networkPackage.init();
        Thread thread = new Thread(networkPackage);
        thread.start();
        Login login = new Login(mainController);
        login.setVisible(true);
        MainView mv = null;
        while(state){
            if(mainController.mainView==null && login==null) close();
            TaskPackage t = networkPackage.getTaskPackage();
            if(t == null) continue;
            if(t!=null){
                System.out.println(t);
            }
            if(mv!=null){
                if(mainController.applicationView!=null && mv.getApplicationView()==0) mainController.applicationView = null;
                if(mainController.receiveView!=null && mv.getReceiveView()==0) mainController.receiveView = null;
                if(mainController.refuseView!=null && mv.getRefuseView()==0) mainController.refuseView = null;
                if(mainController.deleteView!=null && mv.getDeleteView()==0) mainController.deleteView = null;
            }
            int task_id = t.getId();
            if(mv==null && (task_id!=1 && task_id!=4)) continue;
            switch (task_id){
                case 1:
                    login.close();
                    mainController.mainView = new MainView(t,mainController);
                    mv = mainController.mainView;
                    mv.setVisible(true);
                    break;
                case 2:
                    mv.addNewFriend(t);
                    break;
                case 3:
                    int group_id = Integer.parseInt(t.getInfo("group_id"));
                    if(!mv.getGroupChats().containsKey(group_id) || mv.getGroupChats().get(group_id)==null) break;
                    mv.getGroupChats().get(group_id).unpackMessage(t);
                    break;
                case 4:
                    String content = t.getInfo("content");
                    int success = Integer.parseInt(t.getInfo("success"));
                    int response = Integer.parseInt(t.getInfo("response"));
                    if(response==15 && success==0 && login!=null){
                        login.getjEnter().setEnabled(true);
                        login.setRegist(true);
                    }
                    new Warn(content).setVisible(true);
                    mainController.notice(response,success);
                    break;
                case 5:
                    String con1 = "id为" + t.getInfo("id")+"昵称为"+t.getInfo("name")+"的用户申请添加你为好友！";
                    new Warn(con1).setVisible(true);
                    break;
                case 6:
                    int friend_id = Integer.parseInt(t.getInfo("friend_id"));
                    if(!mv.getPersonChats().containsKey(friend_id) || mv.getPersonChats().get(friend_id)!=null) break;
                    User friend = null;
                    for (User user :mv.getFriends()) {
                        if(user.getId()==friend_id){
                            friend = user;
                            break;
                        }
                    }
                    if(friend==null) break;
                    ChatView c = new ChatView(mv.getOwn(),friend,t,mv);
                    c.setVisible(true);
                    mv.getPersonChats().put(friend_id,c);
                    break;
                case 7:
                    int friend_id2 = Integer.parseInt(t.getInfo("from_id"));
                    if(!mv.getPersonChats().containsKey(friend_id2) || mv.getPersonChats().get(friend_id2)==null) break;
                    mv.getPersonChats().get(friend_id2).unpackMessage(t);
                    break;
                case 8:
                    int group_id1 = Integer.parseInt(t.getInfo("group_id"));
                    if(!mv.getGroupChats().containsKey(group_id1) || mv.getGroupChats().get(group_id1)!=null) break;
                    Group group = null;
                    for (Group g :mv.getGroups()) {
                        if(g.getId()==group_id1){
                            group = g;
                            break;
                        }
                    }
                    if(group==null) break;
                    GroupView groupView = new GroupView(mv.getOwn(),group,t,mv);
                    mv.getGroupChats().put(group_id1,groupView);
                    groupView.setVisible(true);
                    break;
                case 9:
                    mv.addNewGroup(t);
                    break;
                case 10:
                    mv.friendOnline(t);
                    break;
                case 11:
                    if(mv.getApplicationView()>0){
                        mainController.applicationView = new ApplicationView(t,mv);
                        mainController.applicationView.setVisible(true);
                    }
                    break;
                case 12:
                    break;
                case 13:
                    if(mv.getReceiveView()>0){
                        mainController.receiveView = new ReceiveView(t,mv);
                        mainController.receiveView.setVisible(true);
                    }
                    break;
                case 14:
                    if(mv.getRefuseView()>0){
                        mainController.refuseView = new RefuseView(t,mv);
                        mainController.refuseView.setVisible(true);
                    }
                    break;
                case 15:
                    if(mv.getDeleteView()>0){
                        mainController.deleteView = new DeleteView(t,mv);
                        mainController.deleteView.setVisible(true);
                    }
                    break;
                case 16:
                    int group_id2 = Integer.parseInt(t.getInfo("group_id"));
                    if(mv.getGroupChats().containsKey(group_id2) && mv.getGroupChats().get(group_id2)!=null){
                        mv.getGroupChats().get(group_id2).newUser(t);
                    }
                    break;
                case 17:
                    int group_id3 = Integer.parseInt(t.getInfo("group_id"));
                    if(mv.getGroupChats().containsKey(group_id3) && mv.getGroupChats().get(group_id3)!=null){
                        mv.getGroupChats().get(group_id3).removeUser(t);
                    }
                    break;
            }
        }
    }

    public void notice(int response,int success){
        switch (response){
            case 1:
                mainView.updateSelf();
                break;
            case 2:
                mainView.updateFriendRemark();
                break;
            case 10:
                mainView.updateGroupRemark();
                break;
        }
    }

    public static void close(){
        TaskPackage t = new TaskPackage(6,0,null);
        networkPackage.sendPackageToController(t);
        networkPackage.close();
        state = false;
    }
}
