package com.bjpowernode.jdbc;

import java.sql.*;

/**
 * Author: 动力节点
 * 2019/6/20
 */
public class JDBCTest12 {
    public static void main(String[] args) {
        // 使用PreparedStatement完成模糊查询
        // 查询名字当中含有A字母的。
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String userInput = "I";
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456");
            // 获取预编译的数据库操作对象
            String sql = "select ename from emp where ename like ?";
            // select ename from emp where ename like '%?%' 这是错误的。
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + userInput + "%");
            // 执行sql
            rs = ps.executeQuery();
            // 处理结果集
            while(rs.next()){
                System.out.println(rs.getString("ename"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
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
    }
}
