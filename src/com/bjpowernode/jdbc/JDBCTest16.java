package com.bjpowernode.jdbc;

import com.bjpowernode.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: 动力节点
 * 2019/6/20
 *
 * 演示行级锁机制：
 *      执行DQL语句查询job='MANAGER'的数据，在本事务没有结束
 *      之前，任何事务不允许修改job='MANAGER'的数据。此时需要
 *      使用行级锁机制，怎么实现？
 *          在DQL语句最后面添加for update。
 *
 * 行级锁，锁定了表格当中的某些行数据，被锁定的行受到保护，没有锁定的行是可以被其他事务修改的。
 * 行级锁又被称为悲观锁。（后期做项目的时候会使用悲观锁和乐观锁。）
 */
public class JDBCTest16 { // 执行DQL
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 虽然是DQL语句，但要想使用行级锁，需要手动开启事务
            DBUtil.beginTransaction(conn);
            String sql = "select empno,ename,sal from emp where job = ? for update";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "MANAGER");
            rs = ps.executeQuery();
            while(rs.next()){
                int empno = rs.getInt("empno");
                String ename = rs.getString("ename");
                double sal = rs.getDouble("sal");
                System.out.println(empno + "," + ename + "," + sal);
            }
            DBUtil.commitTransaction(conn);
        } catch (SQLException e) {
            DBUtil.rollbackTransaction(conn);
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
    }
}


























