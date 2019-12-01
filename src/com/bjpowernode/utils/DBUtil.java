package com.bjpowernode.utils;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * 数据库工具类
 * Author: 动力节点
 * 2019/6/20
 */
public class DBUtil {

    /**
     * 类加载的时候绑定jdbc.properties资源文件
     */
    private static ResourceBundle bundle = ResourceBundle.getBundle("resources.jdbc");

    /**
     * 一般情况下，工具类的构造方法都是private修饰的。
     * 不提供创建对象的入口。只能使用类名直接调用方法。
     */
    private DBUtil(){
    }

    /*
     DBUtil类加载的时候只执行一次，完成数据库驱动的注册。
      */
    static {
        try {
            String driver = bundle.getString("driver");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接对象
     * @return
     */
    public static Connection getConnection() throws SQLException { //注意：工具类当中的方法都是静态的static，这是为了方便，不需要new对象，直接使用类名方式调用。
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    /**
     * 释放资源
     * @param conn
     * @param ps
     * @param rs
     */
    public static void close(Connection conn, Statement ps, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启事务
     * @param conn
     */
    public static void beginTransaction(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
    }

    /**
     * 提交事务
     * @param conn
     */
    public static void commitTransaction(Connection conn) throws SQLException {
        conn.commit();
    }

    /**
     * 回滚事务
     * @param conn
     */
    public static void rollbackTransaction(Connection conn){
        if(conn != null){
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
