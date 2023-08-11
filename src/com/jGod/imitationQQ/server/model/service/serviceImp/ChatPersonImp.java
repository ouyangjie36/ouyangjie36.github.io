package com.jGod.imitationQQ.server.model.service.serviceImp;

import com.jGod.imitationQQ.server.controller.TaskPackage;
import com.jGod.imitationQQ.server.model.jdbc.dao.PersonMessageDAO;
import com.jGod.imitationQQ.server.model.service.ServiceMenu;
import com.jGod.imitationQQ.server.model.service.serviceInterface.ChatPersonInterface;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static com.jGod.imitationQQ.server.controller.MainController.networkPackage;
import static com.jGod.imitationQQ.server.controller.MainController.online;

public class ChatPersonImp extends GetInformation implements ChatPersonInterface {
    @Override
    public void show(int id, TaskPackage taskPackage) {
        ArrayList<String> strings = new ArrayList<>();
        int friend_id = Integer.parseInt(taskPackage.getInfo("friend_id"));
        strings.add("friend_id");
        strings.add(taskPackage.getInfo("friend_id"));
        strings.addAll(new PersonMessageDAO().getPersonMessage(id,friend_id));
        networkPackage.sendPackage(online.get(id).getSocket(), null, 6, null,
                strings.toArray(new String[strings.size()]));
    }

    @Override
    public void sendMessageToClient(int id, TaskPackage taskPackage) {
        int friend_id = Integer.parseInt(taskPackage.getInfo("to_id"));
        ArrayList<String> list = Utils.messageToString(taskPackage);
        if(online.get(friend_id)!=null){
            networkPackage.sendPackage(online.get(friend_id).getSocket(),null,7,null,
                    list.toArray(new String[list.size()]));
        }
    }

    @Override
    public void getMessageFromClient(int id, TaskPackage taskPackage) {
        String sql = "insert into two_person_message values(?,?,?,?,?,null)";
        int num = new PersonMessageDAO().update(sql, taskPackage.getInfo("from_id"),
                taskPackage.getInfo("to_id"), taskPackage.getInfo("content").getBytes(),
                Integer.parseInt(taskPackage.getInfo("type")), new Timestamp(Long.parseLong(taskPackage.getInfo("date"))));
        if(num==0){
            ServiceMenu.getWarn().sendWarn(online.get(id).getSocket(),4,"发送消息失败！",5,0);
        }else{
            sendMessageToClient(id, taskPackage);
        }
    }
}
