package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.Receive;
import com.jGod.imitationQQ.server.model.jdbc.bean.Refuse;
import com.jGod.imitationQQ.server.model.service.serviceImp.UserMenuImp;

import java.util.ArrayList;
import java.util.List;

public class RefuseDAO extends BasicDAO<Refuse>{
    public ArrayList<String> getRefuseList(int id, UserMenuImp userMenuImp){
        String sql = "select * from refuse_table where id = ? or applicant_id = ?";
        ArrayList<String> res = new ArrayList<>();
        List<Refuse> list = getQueryMany(sql,Refuse.class,id,id);
        res.add("refuseNum");
        res.add(String.valueOf(list.size()));
        int i = 0;
        for (Refuse refuse :list) {
            res.add("user"+i+"@id");
            res.add(String.valueOf(refuse.getId()));
            res.add("user"+i+"@name");
            res.add(userMenuImp.getUser(refuse.getId()).getName());
            res.add("user"+i+"@applicant_id");
            res.add(String.valueOf(refuse.getApplicant_id()));
            res.add("user"+i+"@applicant_name");
            res.add(userMenuImp.getUser(refuse.getApplicant_id()).getName());
            res.add("user"+i+"@text");
            res.add(refuse.getText());
            res.add("user"+i+"@date");
            res.add(String.valueOf(refuse.getDate().getTime()));
            i++;
        }
        return res;
    }
}
