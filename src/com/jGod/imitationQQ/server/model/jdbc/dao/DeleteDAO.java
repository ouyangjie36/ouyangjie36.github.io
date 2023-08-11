package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.Delete;
import com.jGod.imitationQQ.server.model.service.serviceImp.UserMenuImp;

import java.util.ArrayList;
import java.util.List;

public class DeleteDAO extends BasicDAO<Delete>{
    public ArrayList<String> getDeleteInformation(int id, UserMenuImp userMenuImp){
        String sql = "select * from delete_table where id = ? or friend_id = ?";
        List<Delete> list = getQueryMany(sql,Delete.class,id,id);
        ArrayList<String> res = new ArrayList<>();
        res.add("deleteNum");
        res.add(String.valueOf(list.size()));
        int i =0;
        for (Delete delete :list) {
            res.add("user"+i+"@id");
            res.add(String.valueOf(delete.getId()));
            res.add("user"+i+"@name");
            res.add(userMenuImp.getUser(delete.getId()).getName());
            res.add("user"+i+"@friend_id");
            res.add(String.valueOf(delete.getFriend_id()));
            res.add("user"+i+"@friend_name");
            res.add(userMenuImp.getUser(delete.getFriend_id()).getName());
            res.add("user"+i+"@date");
            res.add(String.valueOf(delete.getDate().getTime()));
            i++;
        }
        return res;
    }
}
