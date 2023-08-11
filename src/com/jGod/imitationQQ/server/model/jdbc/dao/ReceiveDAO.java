package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.Receive;
import com.jGod.imitationQQ.server.model.service.serviceImp.UserMenuImp;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDAO extends BasicDAO<Receive>{
    public ArrayList<String> getReceiveList(int id, UserMenuImp userMenuImp){
        String sql = "select * from receive_table where id = ? or applicant_id = ?";
        ArrayList<String> res = new ArrayList<>();
        List<Receive> list = getQueryMany(sql,Receive.class,id,id);
        res.add("receiveNum");
        res.add(String.valueOf(list.size()));
        int i = 0;
        for (Receive receive :list) {
            res.add("user"+i+"@id");
            res.add(String.valueOf(receive.getId()));
            res.add("user"+i+"@name");
            res.add(userMenuImp.getUser(receive.getId()).getName());
            res.add("user"+i+"@applicant_id");
            res.add(String.valueOf(receive.getApplicant_id()));
            res.add("user"+i+"@applicant_name");
            res.add(userMenuImp.getUser(receive.getApplicant_id()).getName());
            res.add("user"+i+"@text");
            res.add(receive.getText());
            res.add("user"+i+"@date");
            res.add(String.valueOf(receive.getDate().getTime()));
            i++;
        }
        return res;
    }

}
