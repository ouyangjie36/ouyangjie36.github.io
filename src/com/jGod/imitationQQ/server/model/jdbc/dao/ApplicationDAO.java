package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends BasicDAO<Application>{
    public ArrayList<String> getApplication(String sql,Object... parameters){
        ArrayList<String> res = new ArrayList<>();
        List<Application> list = new ApplicationDAO().getQueryMany(sql,Application.class,parameters);
        res.add("applicantNum");
        res.add(String.valueOf(list.size()));
        int i = 0;
        for (Application application :list) {
            res.add("user"+i+"@id");
            res.add(String.valueOf(application.getApplicant_id()));
            res.add("user"+i+"@text");
            res.add(application.getText());
            res.add("user"+i+"@name");
            res.add(application.getName());
            res.add("user"+i+"@head");
            res.add(String.valueOf(application.getHead()));
            i++;
        }
        return res;
    }
}
