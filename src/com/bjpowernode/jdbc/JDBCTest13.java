package com.bjpowernode.jdbc;

import com.bjpowernode.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: 动力节点
 * 2019/6/20
 */
public class JDBCTest13 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 获取数据库操作对象
            String sql = "select ename from emp where empno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 7369);
            // 执行sql
            rs = ps.executeQuery();
            // 处理结果集
            while(rs.next()){
                System.out.println(rs.getString("ename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            // 释放资源
            DBUtil.close(conn, ps, rs);
        }
    }
}
