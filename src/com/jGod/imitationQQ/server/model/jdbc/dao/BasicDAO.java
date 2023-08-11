package com.jGod.imitationQQ.server.model.jdbc.dao;

import com.jGod.imitationQQ.server.model.jdbc.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T> {
    private static QueryRunner qr = new QueryRunner();

    public List<T> getQueryMany(String sql, Class<T> clazz, Object... parameters){
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return qr.query(connection,sql,new BeanListHandler<T>(clazz),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.close(null,null,connection);
        }
    }

    public T getQuerySingle(String sql,Class<T> clazz,Object... parameters){
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return qr.query(connection,sql,new BeanHandler<>(clazz),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.close(null,null,connection);
        }
    }

    public Object getScalar(String sql,Object... parameters){
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return qr.query(connection,sql,new ScalarHandler(),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.close(null,null,connection);
        }
    }

    public int update(String sql,Object... parameters){
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return qr.update(connection,sql,parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.close(null,null,connection);
        }
    }
}
