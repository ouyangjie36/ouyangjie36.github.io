package com.jGod.imitationQQ.server.model.service.serviceImp;

import com.jGod.imitationQQ.server.controller.TaskPackage;
import com.jGod.imitationQQ.server.model.jdbc.bean.Friend;
import com.jGod.imitationQQ.server.model.jdbc.bean.Group;
import com.jGod.imitationQQ.server.model.jdbc.bean.GroupUser;
import com.jGod.imitationQQ.server.model.jdbc.bean.User;
import com.jGod.imitationQQ.server.model.jdbc.dao.*;
import com.jGod.imitationQQ.server.model.service.ServiceMenu;
import com.jGod.imitationQQ.server.model.service.serviceInterface.UserMenuInterface;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

import static com.jGod.imitationQQ.server.controller.MainController.networkPackage;
import static com.jGod.imitationQQ.server.controller.MainController.online;

public class UserMenuImp extends GetInformation implements UserMenuInterface {
    @Override
    public void show(int id) {
        String[] parameters = new String[6];
        parameters[0] = "head";
        parameters[1] = "name";
        parameters[2] = "id";
        parameters[3] = "birthday";
        parameters[4] = "gender";
        parameters[5] = "age";

        ArrayList<String> strings = new FriendDAO().getFriendListInformation(id);
        strings.addAll(new GroupDAO().getGroupListInformation(id));
        networkPackage.sendPackage(online.get(id).getSocket(), getUser(id), 1,
                parameters, strings.toArray(new String[strings.size()]));
    }

    @Override
    public void addFriend(int id, TaskPackage taskPackage) {
        User user;
        int friend_id = Integer.parseInt(taskPackage.getInfo("friend_id"));
        if ((user = getUser(friend_id)) == null) {
            String text = "不存在该id的用户！";
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, text, 3, 0);
            return;
        }
        String sql0 = "select id from friend where id = ? and friend_id = ?";
        Object o = new FriendDAO().getScalar(sql0,id,friend_id);
        if(o!=null){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "已添加了该好友！", 3, 0);
            return;
        }
        String sql = "insert into application values(?,?,?)";
        int num = new ApplicationDAO().update(sql, friend_id, id, taskPackage.getInfo("text"));
        if (num == 0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "申请好友失败！", 3, 0);
        } else {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "已申请好友！", 3, 1);
            if (online.get(friend_id) != null) {
                String[] s = new String[3];
                s[0] = "id";
                s[1] = "head";
                s[2] = "name";
                String[] s1 = new String[2];
                s1[0] = "text";
                s1[1] = taskPackage.getInfo("text");
                networkPackage.sendPackage(online.get(friend_id).getSocket(), user, 5, s, s1);
            }
        }
    }

    @Override
    public void deleteFriend(int id, TaskPackage taskPackage) {
        int friend_id = Integer.parseInt(taskPackage.getInfo("friend_id"));
        String sql = "delete from friend where id=? and friend_id=?";
        int num = new FriendDAO().update(sql, id, friend_id);
        if (num == 0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "删除好友失败！", 4, 0);
        } else {
            String text1 = "已删除好友" + getUser(friend_id).getName() + "！";
            String sql1 = "insert into delete_table values(?,?,?)";
            new DeleteDAO().update(sql1, id, friend_id,new Timestamp(System.currentTimeMillis()));
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, text1, 4, 1);
            if (online.get(friend_id) != null) {
                String text2 = "你已被" + getUser(id).getName() + "删除好友";
                ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, text2, 4, 1);
            }
        }
    }

    @Override
    public void modifySelfInfo(int id, TaskPackage taskPackage) {
        String sql = "update user set ";
        String[] parameters = new String[taskPackage.getLen() + 1];
        int i = 0;
        User own = getUser(id);
        for (Map.Entry entry : taskPackage.getHashMap().entrySet()) {
            switch ((String) entry.getKey()){
                case "head":
                    own.setHead(Integer.parseInt((String) entry.getValue()));
                    break;
                case "name":
                    own.setName((String) entry.getValue());
                    break;
                case "birthday":
                    own.setBirthday((String) entry.getValue());
                    break;
                case "gender":
                    own.setGender((String) entry.getValue());
                    break;
                case "age":
                    own.setAge(Integer.parseInt((String) entry.getValue()));
                    break;
            }
            if (i != 0) {
                sql += ", ";
            }
            parameters[i] = (String) entry.getValue();
            if(entry.getKey()=="password") parameters[i] = "password("+(String)entry.getValue()+")";
            i++;
            sql += entry.getKey() + " = ?";
        }
        sql += " where id = ?";
        parameters[i] = String.valueOf(id);
        UserDAO userDAO = new UserDAO();
        int num = userDAO.update(sql, parameters);
        if (num == 0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "修改个人信息失败！", 1, 0);
        } else {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "修改个人信息成功！", 1, 1);
        }
    }

    @Override
    public void modifyFriendRemark(int id, TaskPackage taskPackage) {
        String sql = "update friend set remark = ? where id = ? and friend_id = ?";
        UserDAO userDAO = new UserDAO();
        int num = userDAO.update(sql, taskPackage.getInfo("remark"), id,
                Integer.parseInt(taskPackage.getInfo("friend_id")));
        if (num == 0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "修改好友备注失败！", 2, 0);
        } else {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "修改好友备注成功！", 2, 1);
        }
    }

    @Override
    public void createGroup(int id, TaskPackage taskPackage) {
        String test = "select id from group_list where id = ?";
        Object o = new GroupDAO().getScalar(test,Integer.parseInt(taskPackage.getInfo("group_id")));
        if(o!=null){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "该群号已存在,创建群聊失败！", 6, 0);
            return;
        }
        String sql = "insert into group_list values(?,?,?,?,?,?)";
        int num = new GroupDAO().update(sql, taskPackage.getInfo("group_id"), new Timestamp(System.currentTimeMillis()),
                0,id, Integer.parseInt(taskPackage.getInfo("head")), taskPackage.getInfo("name"));
        if (num == 0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "创建群聊失败！", 6, 0);
        } else {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "创建群聊成功！", 6, 1);
            addGroup(id, taskPackage, false);
        }
    }


    @Override
    public void addGroup(int id, TaskPackage taskPackage, boolean show) {
        String sql1 = "update group_list set number = number+1 where id = ?";
        String sql2 = "insert into group_user values(?,?,null)";
        String sql3 = "select * from group_list where id = ?";
        String sql4 = "insert into my_group values(?,?,null)";
        int group_id = Integer.parseInt(taskPackage.getInfo("group_id"));
        Group group = new GroupDAO().getQuerySingle(sql3, Group.class, group_id);
        if(group==null){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "该群号不存在！", 7, 0);
        }
        group.setNumber(group.getNumber()+1);
        int num1 = new GroupDAO().update(sql1, group_id);
        int num2 = new GroupUserDAO().update(sql2, group_id, id);
        int num3 = new GroupDAO().update(sql4,id,group_id);
        if (num1 == 0 || num2 == 0 || num3==0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "加入群聊失败！", 7, 0);
        } else {
            String[] s = new String[5];
            s[0] = "id";
            s[1] = "head";
            s[2] = "name";
            s[3] = "number";
            s[4] = "master_id";
            networkPackage.sendPackage(online.get(id).getSocket(), group, 9, s);
            TaskPackage t = new TaskPackage(0,6,new HashMap<>());
            t.getHashMap().put("group_id",String.valueOf(group_id));
            t.getHashMap().put("id",String.valueOf(id));
            t.getHashMap().put("head",String.valueOf(getUser(id).getHead()));
            t.getHashMap().put("state",String.valueOf(1));
            t.getHashMap().put("name",getUser(id).getName());
            t.getHashMap().put("remark","");
            noticeGroupMembers(id,t,16);
            if (show) ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "加入群聊成功！", 7, 1);
        }
    }

    @Override
    public void exit(int id) {
        networkPackage.removeSocket(online.get(id).getSocket());
        online.get(id).shutDown();
        online.remove(id);
    }

    @Override
    public void scanApplication(int id, TaskPackage taskPackage) {
        String sql = "select tbl.id as uid,applicant_id,name,head,text from user,(select applicant_id,id,text from application where id " +
                "= ?) as tbl where user.id = tbl.applicant_id";
        ArrayList<String> list = new ApplicationDAO().getApplication(sql, id);
        networkPackage.sendPackage(online.get(id).getSocket(), null, 11, null,
                (list==null?null:list.toArray(new String[list.size()])));
    }

    @Override
    public void receiveApplication(int id, TaskPackage taskPackage) {
        int friend_id = Integer.parseInt(taskPackage.getInfo("friend_id"));
        String sql1 = "delete from application where id = ? and applicant_id = ?";
        String sql2 = "insert into friend values(?,?,null)";
        String sql3 = "insert into receive_table values(?,?,?,?)";
        int num1 = new ApplicationDAO().update(sql1, id, friend_id);
        int num2 = new FriendDAO().update(sql2, id, friend_id);
        int num3 = new FriendDAO().update(sql2, friend_id, id);
        int num4 = new ReceiveDAO().update(sql3,id,friend_id,taskPackage.getInfo("text"),
                new Timestamp(System.currentTimeMillis()));
        if (num1 == 0 || num2 == 0 || num3 == 0 || num4 == 0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "接受好友请求失败！", 11, 0);
        } else {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "接受成功！", 11, 1);
            String[] s = {"head","name","id"};
            String[] extra = {"state","1","remark",""};
            if(online.get(friend_id)!=null){
                networkPackage.sendPackage(online.get(friend_id).getSocket(),getUser(id),2,s,extra);
                networkPackage.sendPackage(online.get(id).getSocket(),getUser(friend_id),2,s,extra);
                ServiceMenu.getWarn().sendWarn(online.get(friend_id).getSocket(), 4,
                        "好友"+id+"("+getUser(id).getName()+")"+ "申请已接受！", 12, 1);
            }else{
                extra[1] = "0";
                networkPackage.sendPackage(online.get(id).getSocket(),getUser(friend_id),2,s,extra);
            }
        }
    }

    @Override
    public void refuseApplication(int id, TaskPackage taskPackage) {
        int friend_id = Integer.parseInt(taskPackage.getInfo("friend_id"));
        String sql1 = "delete from application where id = ? and applicant_id = ?";
        String sql2 = "insert into refuse_table values(?,?,?,?)";
        int num1 = new ApplicationDAO().update(sql1,id,friend_id);
        int num2 = new RefuseDAO().update(sql2,id,friend_id,taskPackage.getInfo("text"),
                new Timestamp(System.currentTimeMillis()));
        if (num1 == 0 || num2 == 0) {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "拒绝好友请求失败！", 13, 0);
        } else {
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "拒绝成功！", 13, 1);
            if(online.get(friend_id)!=null){
                ServiceMenu.getWarn().sendWarn(online.get(friend_id).getSocket(), 4,
                        "好友"+id+"("+getUser(id).getName()+")"+ "申请已拒绝！", 12, 0);
            }
        }
    }

    @Override
    public void scanReceive(int id, TaskPackage taskPackage) {
        ArrayList<String> list = new ReceiveDAO().getReceiveList(id,this);
        networkPackage.sendPackage(online.get(id).getSocket(),null,13,null,
                (list==null?null:list.toArray(new String[list.size()])));
    }

    @Override
    public void scanRefuse(int id, TaskPackage taskPackage) {
        ArrayList<String> list = new RefuseDAO().getRefuseList(id,this);
        networkPackage.sendPackage(online.get(id).getSocket(),null,14,null,
                (list==null?null:list.toArray(new String[list.size()])));
    }

    @Override
    public void deleteInformation(int id, TaskPackage taskPackage) {
        ArrayList<String> list = new DeleteDAO().getDeleteInformation(id,this);
        networkPackage.sendPackage(online.get(id).getSocket(),null,15,null,
                (list==null?null:list.toArray(new String[list.size()])));
    }

    public void friendOnline(int id){
        String sql = "select * from friend where id = ?";
        List<Friend> list = new FriendDAO().getQueryMany(sql,Friend.class,id);
        for (Friend friend :list) {
            String[] s = {"friend_id",String.valueOf(friend.getFriend_id())};
            if(online.get(friend.getFriend_id())!=null){
                networkPackage.sendPackage(online.get(friend.getFriend_id()).getSocket(),null,10,null,s);
            }
        }
    }

    public void exitGroup(int id,int group_id){
        String sql1 = "update group_list set number = number-1 where id = ?";
        String sql2 = "delete from my_group where id = ? and group_id = ?";
        int num1 = new GroupDAO().update(sql1,group_id);
        int num2 = new GroupDAO().update(sql2,id,group_id);
        if(num1==0 || num2==0){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "退出群聊失败！", 8, 0);
        }else{
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(), 4, "退出群聊成功！", 8, 1);
            TaskPackage t = new TaskPackage(0,2,new HashMap<>());
            t.getHashMap().put("id",String.valueOf(id));
            t.getHashMap().put("group_id",String.valueOf(group_id));
            noticeGroupMembers(id,t,17);
        }
    }

    public void noticeGroupMembers(int id,TaskPackage taskPackage,int task_id){
        int group_id = Integer.parseInt(taskPackage.getInfo("group_id"));
        ArrayList<String> list = Utils.messageToString(taskPackage);
        String sql = "select * from group_user where id = ?";
        List<GroupUser> userList = new GroupUserDAO().getQueryMany(sql,GroupUser.class,group_id);
        for (GroupUser groupUser :userList) {
            if(groupUser.getUser_id()!=id && online.containsKey(groupUser.getUser_id())){
                networkPackage.sendPackage(online.get(groupUser.getUser_id()).getSocket(),null,task_id,null,
                        list.toArray(new String[list.size()]));
            }
        }
    }
}
