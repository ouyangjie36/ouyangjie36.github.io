package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.PersonMessage;

import java.util.ArrayList;
import java.util.List;

public class PersonMessageDAO extends BasicDAO<PersonMessage>{
    public ArrayList<String> getPersonMessage(int id,int friend_id){
        String sql = "select from_id,to_id,content,type,date from two_person_message where (from_id = ? and to_id = ?) or (from_id = ? and to_id = " +
                "?) order by date desc limit 50";
        List<PersonMessage> list = new PersonMessageDAO().getQueryMany(sql,PersonMessage.class,id,friend_id,friend_id,id);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("messageNum");
        strings.add(String.valueOf(list.size()));
        int i = 0;
        for (PersonMessage personMessage :list) {
            strings.add("message"+i+"@from_id");
            strings.add(String.valueOf(personMessage.getFrom_id()));
            strings.add("message"+i+"@to_id");
            strings.add(String.valueOf(personMessage.getTo_id()));
            strings.add("message"+i+"@content");
            strings.add(new String(personMessage.getContent()));
            strings.add("message"+i+"@type");
            strings.add(String.valueOf(personMessage.getType()));
            strings.add("message"+i+"@date");
            strings.add(String.valueOf(personMessage.getDate().getTime()));
            i++;
        }
        return strings;
    }
}
