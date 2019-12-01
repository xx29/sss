package com.bjpowernode.jdbc;

import java.sql.*;

/**
 * Author: 动力节点
 * 2019/6/20
 */
public class JDBCTest11 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456");
            // 获取预编译的数据库操作对象
            /*
            // 使用PreparedStatement完成insert delete update
            String sql1 = "insert into dept(deptno,dname,loc) values(?,?,?)";
            ps = conn.prepareStatement(sql1);
            ps.setInt(1, 80);
            ps.setString(2, "销售部");
            ps.setString(3, "北京");
            // 执行sql
            int count = ps.executeUpdate(); // 返回影响数据库中的记录条数
            System.out.println(count == 1 ? "insert success" : "insert fail");
            */

            /*
            String sql2 = "update dept set dname = ? , loc = ? where deptno = ?";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, "人事部");
            ps.setString(2, "上海");
            ps.setInt(3, 80);
            int count = ps.executeUpdate();
            System.out.println(count == 1 ? "update success" : "update fail");
            */

            String sql3 = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql3);
            ps.setInt(1, 80);
            int count = ps.executeUpdate();
            System.out.println(count == 1 ? "delete success" : "delete fail");
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
