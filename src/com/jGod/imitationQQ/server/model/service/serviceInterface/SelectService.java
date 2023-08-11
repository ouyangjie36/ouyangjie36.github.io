package com.jGod.imitationQQ.server.model.service.serviceInterface;

import com.jGod.imitationQQ.server.model.jdbc.bean.Friend;
import com.jGod.imitationQQ.server.model.jdbc.bean.PersonMessage;
import com.jGod.imitationQQ.server.model.jdbc.bean.User;

import java.util.List;

public interface SelectService {
    List<Friend> getFriend(int id);

    User getUser(int id);


}
