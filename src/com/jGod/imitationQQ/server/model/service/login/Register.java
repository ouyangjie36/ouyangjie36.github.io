package com.jGod.imitationQQ.server.model.service.login;

import com.jGod.imitationQQ.server.controller.TaskPackage;
import com.jGod.imitationQQ.server.model.jdbc.dao.UserDAO;
import com.jGod.imitationQQ.server.model.service.ServiceMenu;

import java.net.Socket;

public class Register {
    public static void register(Socket socket, TaskPackage taskPackage) {
        String sql = "insert into user values(?,?,?,?,?,?,password(?))";
        int id = Integer.parseInt(taskPackage.getInfo("id"));
        String sql1 = "select id from user where id = ?";
        Object o = new UserDAO().getScalar(sql1,id);
        if(o!=null){
            ServiceMenu.getWarn().sendWarn(socket, 4, "账号已存在,注册失败！", 14, 0);
        }
        String name = taskPackage.getInfo("name");
        int head = Integer.parseInt(taskPackage.getInfo("head"));
        int age = Integer.parseInt(taskPackage.getInfo("age"));
        String gender = taskPackage.getInfo("gender");
        String birthday = taskPackage.getInfo("birthday");
        String password = taskPackage.getInfo("password");
        UserDAO userDAO = new UserDAO();
        int num = userDAO.update(sql, id, name, head, age, gender, birthday, password);
        if (num == 0) {
            ServiceMenu.getWarn().sendWarn(socket, 4, "注册失败！", 14, 0);
        } else {
            ServiceMenu.getWarn().sendWarn(socket, 4, "注册成功！", 14, 1);
        }
    }
}
