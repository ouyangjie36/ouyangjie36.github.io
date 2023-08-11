package com.jGod.imitationQQ.server.model.service.serviceImp;

import com.jGod.imitationQQ.server.controller.TaskPackage;

import java.util.ArrayList;
import java.util.Map;

public class Utils {
    public static ArrayList<String> messageToString(TaskPackage taskPackage){
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry entry :taskPackage.getHashMap().entrySet()) {
            list.add((String) entry.getKey());
            list.add((String) entry.getValue());
        }
        return list;
    }
}
