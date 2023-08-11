package com.jGod.imitationQQ.server.model.service.serviceImp;

import com.jGod.imitationQQ.server.model.jdbc.bean.Friend;
import com.jGod.imitationQQ.server.model.jdbc.bean.PersonMessage;
import com.jGod.imitationQQ.server.model.jdbc.bean.User;
import com.jGod.imitationQQ.server.model.jdbc.dao.FriendDAO;
import com.jGod.imitationQQ.server.model.jdbc.dao.UserDAO;
import com.jGod.imitationQQ.server.model.service.serviceInterface.SelectService;

import java.util.List;

import static com.jGod.imitationQQ.server.controller.MainController.online;

public class GetInformation implements SelectService {
    @Override
    public List<Friend> getFriend(int id) {
        String sql = "select friend_id,remark from friend where id = ?";
        return new FriendDAO().getQueryMany(sql,Friend.class,id);
    }

    @Override
    public User getUser(int id) {
        if(online.get(id)!=null && online.get(id).getUser()!=null) return online.get(id).getUser();
        String sql = "select * from user where id = ?";
        return new UserDAO().getQuerySingle(sql,User.class,id);
    }

}
