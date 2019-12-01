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
public class JDBCTest17 { // 执行DML update语句。
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            // 虽然是DQL语句，但要想使用行级锁，需要手动开启事务
            DBUtil.beginTransaction(conn);
            String sql = "update emp set sal = sal * 1.5 where job = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "MANAGER");
            int count = ps.executeUpdate();
            DBUtil.commitTransaction(conn);
            System.out.println("更新了"+count+"条记录");
        } catch (SQLException e) {
            DBUtil.rollbackTransaction(conn);
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        System.out.println("你好啊");
        System.out.println("你好啊");
        System.out.println("你好啊");
        System.out.println("你好啊");
        System.out.println("你好啊");
        System.out.println("你好啊");
    }
}
