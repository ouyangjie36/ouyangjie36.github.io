package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.bean.Group;
import com.jGod.imitationQQ.server.model.jdbc.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.jGod.imitationQQ.server.controller.MainController.online;

public class GroupDAO extends BasicDAO<Group>{
    public ArrayList<String> getGroupListInformation(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select group_list.id,number,head,name,master_id from group_list ,(select * from " +
                "my_group where " +
                "id = ?) as tbl where group_list.id=tbl.group_id";
        try {
            connection = DruidUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> strings = new ArrayList<>();
            strings.add("groupNum");
            strings.add("0");
            int i = 0;
            while(resultSet.next()){
                strings.add("group"+i+"@id");
                strings.add(String.valueOf(resultSet.getInt(1)));
                strings.add("group"+i+"@number");
                strings.add(String.valueOf(resultSet.getInt(2)));
                strings.add("group"+i+"@head");
                strings.add(String.valueOf(resultSet.getInt(3)));
                strings.add("group"+i+"@name");
                strings.add(resultSet.getString(4));
                strings.add(("group"+i+"@master_id"));
                strings.add(String.valueOf(resultSet.getInt(5)));
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

    public ArrayList<String> getGroupUserInformation(int group_id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select user.id,head,name,remark from user ,(select user_id,remark from " +
                "group_user where id = ?) as tbl where user.id=tbl.user_id";
        try {
            connection = DruidUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,group_id);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> strings = new ArrayList<>();
            strings.add("userNum");
            strings.add("0");
            int i = 0;
            while(resultSet.next()){
                strings.add("user"+i+"@id");
                strings.add(String.valueOf(resultSet.getInt(1)));
                strings.add("user"+i+"@head");
                strings.add(String.valueOf(resultSet.getInt(2)));
                strings.add("user"+i+"@name");
                strings.add(resultSet.getString(3));
                strings.add("user"+i+"@remark");
                if(resultSet.getString(4)==null){
                    strings.add("");
                }else{
                    strings.add(resultSet.getString(4));
                }
                strings.add("user"+i+"@state");
                if(online.get(resultSet.getInt(1))!=null){
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
