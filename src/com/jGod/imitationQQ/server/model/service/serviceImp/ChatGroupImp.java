package com.jGod.imitationQQ.server.model.service.serviceImp;

import com.jGod.imitationQQ.server.controller.TaskPackage;
import com.jGod.imitationQQ.server.model.jdbc.bean.GroupUser;
import com.jGod.imitationQQ.server.model.jdbc.dao.GroupDAO;
import com.jGod.imitationQQ.server.model.jdbc.dao.GroupMessageDAO;
import com.jGod.imitationQQ.server.model.jdbc.dao.GroupUserDAO;
import com.jGod.imitationQQ.server.model.jdbc.dao.PersonMessageDAO;
import com.jGod.imitationQQ.server.model.service.ServiceMenu;
import com.jGod.imitationQQ.server.model.service.serviceInterface.ChatGroupInterface;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jGod.imitationQQ.server.controller.MainController.networkPackage;
import static com.jGod.imitationQQ.server.controller.MainController.online;

public class ChatGroupImp extends GetInformation implements ChatGroupInterface {

    @Override
    public void show(int id, TaskPackage taskPackage) {
        int group_id = Integer.parseInt(taskPackage.getInfo("group_id"));
        ArrayList<String> strings = new GroupDAO().getGroupUserInformation(group_id);
        strings.addAll(new GroupMessageDAO().getGroupMessage(group_id));
        strings.add("group_id");
        strings.add(taskPackage.getInfo("group_id"));
        networkPackage.sendPackage(online.get(id).getSocket(),null,8,null,
                strings.toArray(new String[strings.size()]));
    }

    @Override
    public void sendMessageToClient(int id, TaskPackage taskPackage) {
        int group_id = Integer.parseInt(taskPackage.getInfo("group_id"));
        ArrayList<String> list = Utils.messageToString(taskPackage);
        String sql = "select * from group_user where id = ?";
        List<GroupUser> userList = new GroupUserDAO().getQueryMany(sql,GroupUser.class,group_id);
        for (GroupUser groupUser :userList) {
            if(groupUser.getUser_id()!=id && online.containsKey(groupUser.getUser_id())){
                networkPackage.sendPackage(online.get(groupUser.getUser_id()).getSocket(),null,3,null,
                        list.toArray(new String[list.size()]));
            }
        }
    }

    @Override
    public void getMessageFromClient(int id, TaskPackage taskPackage) {
        String sql = "insert into group_message values(?,?,?,?,?,null)";
        int num = new PersonMessageDAO().update(sql, taskPackage.getInfo("from_id"),
                taskPackage.getInfo("group_id"), taskPackage.getInfo("content").getBytes(),
                Integer.parseInt(taskPackage.getInfo("type")), new Timestamp(Long.parseLong(taskPackage.getInfo("date"))));
        if(num==0){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"发送消息失败！",5,0);
        }else{
            sendMessageToClient(id, taskPackage);
        }
    }

    @Override
    public void modifySelfRemark(int id, TaskPackage taskPackage) {
        String sql = "update group_user set remark = ? where id = ? and user_id = ?";
        int num = new GroupUserDAO().update(sql,taskPackage.getInfo("remark"),
                taskPackage.getInfo("group_id"),id);
        if(num == 0){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"修改群聊备注失败！",10,0);
        }else{
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"修改群聊备注成功！",10,1);
        }
    }


    @Override
    public void exitGroup(int id, TaskPackage taskPackage) {
        String sql1 = "delete from group_user where id = ? and user_id = ?";
        String sql2 = "update group_list set number = number-1 where id = ?";
        int group_id = Integer.parseInt(taskPackage.getInfo("id"));
        int num1 = new GroupDAO().update(sql2, group_id);
        int num2 = new GroupUserDAO().update(sql1, group_id, id);
        if(num1==0 || num2==0){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"退出群聊失败！",8,0);
        }else{
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"退出群聊成功！",8,1);
        }
    }

    @Override
    public void disbandGroup(int id, TaskPackage taskPackage) {
        String sql = "select master_id from group_list where id = ?";
        int group_id = Integer.parseInt(taskPackage.getInfo("id"));
        Object master_id = new GroupDAO().getScalar(sql,group_id);
        if(id != (int) master_id){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"你不是群主，解散群聊失败！",9,0);
        }
        String sql1 = "delete from group_user where id = ?";
        String sql2 = "delete from group_list where id = ?";
        String sql3 = "delete from my_group where id = ? and group_id = ?";
        int num1 = new GroupUserDAO().update(sql1, group_id);
        int num2 = new GroupDAO().update(sql2, group_id);
        int num3 = new GroupDAO().update(sql3,id,group_id);
        if(num1==0 || num2 ==0 || num3==0){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"解散群聊失败！",9,0);
        }else{
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"解散群聊成功！",9,1);
        }
    }


}
