package com.jGod.imitationQQ.server.model.service.login;

import com.jGod.imitationQQ.server.controller.TaskPackage;
import com.jGod.imitationQQ.server.model.jdbc.bean.User;
import com.jGod.imitationQQ.server.model.jdbc.dao.UserDAO;
import com.jGod.imitationQQ.server.model.service.ServiceMenu;

import java.net.Socket;


public class Login {
    public static User login(Socket socket, TaskPackage taskPackage){
        String sql = "select id,name,head,birthday,gender,age from user where id = ? and password = password(?)";
        int id = Integer.parseInt(taskPackage.getInfo("id"));
        String password = taskPackage.getInfo("password");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getQuerySingle(sql,User.class,id,password);
        if(user==null){
            ServiceMenu.getWarn().sendWarn(socket, 4, "账号或密码错误！", 15, 0);
        }
        return user;
    }
}
