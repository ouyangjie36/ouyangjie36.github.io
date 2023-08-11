package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.Friend;
import com.jGod.imitationQQ.server.model.jdbc.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.jGod.imitationQQ.server.controller.MainController.online;

public class FriendDAO extends BasicDAO<Friend>{
    public ArrayList<String> getFriendListInformation(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select head,name,tbl.remark,user.id from user ,(select * from friend where " +
                "id = ?) as tbl where user.id=tbl.friend_id";
        try {
            connection = DruidUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> strings = new ArrayList<>();
            strings.add("friendNum");
            strings.add("0");
            int i = 0;
            while(resultSet.next()){
                strings.add("friend"+i+"@head");
                strings.add(String.valueOf(resultSet.getInt(1)));
                strings.add("friend"+i+"@name");
                strings.add(resultSet.getString(2));
                strings.add("friend"+i+"@remark");
                if(resultSet.getString(3)==null) {
                    strings.add("");
                }else{
                    strings.add(resultSet.getString(3));
                }
                strings.add("friend"+i+"@id");
                strings.add(String.valueOf(resultSet.getInt(4)));
                strings.add("friend"+i+"@state");
                if(online.get(resultSet.getInt(4))!=null){
                    strings.add("1");
                }else{
                    strings.add("0");
                }
                i++;
            }
            strings.set(1,String.valueOf(i));
            return strings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.close(resultSet,preparedStatement,connection);
        }
    }
}
