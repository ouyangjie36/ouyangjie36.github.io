package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.Group;
import com.jGod.imitationQQ.server.model.jdbc.bean.GroupMessage;
import com.jGod.imitationQQ.server.model.jdbc.bean.PersonMessage;

import java.util.ArrayList;
import java.util.List;

public class GroupMessageDAO extends BasicDAO<GroupMessage>{
    public ArrayList<String> getGroupMessage(int group_id){
        String sql = "select from_id,group_id,content,type,date from group_message where group_id = ? order by date desc limit 50";
        List<GroupMessage> list = new GroupMessageDAO().getQueryMany(sql,GroupMessage.class,group_id);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("messageNum");
        strings.add(String.valueOf(list.size()));
        int i = 0;
        for (GroupMessage groupMessage :list) {
            strings.add("message"+i+"@from_id");
            strings.add(String.valueOf(groupMessage.getFrom_id()));
            strings.add("message"+i+"@group_id");
            strings.add(String.valueOf(groupMessage.getGroup_id()));
            strings.add("message"+i+"@content");
            strings.add(new String(groupMessage.getContent()));
            strings.add("message"+i+"@type");
            strings.add(String.valueOf(groupMessage.getType()));
            strings.add("message"+i+"@date");
            strings.add(String.valueOf(groupMessage.getDate().getTime()));
            i++;
        }
        return strings;
    }
}
