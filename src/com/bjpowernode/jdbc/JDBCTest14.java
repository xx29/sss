package com.bjpowernode.jdbc;

import com.bjpowernode.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * Author: 动力节点
 * 2019/6/20
 *
 * 准备数据
 *      drop table if exists t_act;
 *      create table t_act(
 *          id int primary key auto_increment,
 *          actno varchar(255) unique,
 *          balance double(10,2)
 *      );
 *      insert into t_act(actno,balance) values('act-001',50000);
 *      insert into t_act(actno,balance) values('act-002',0);
 *      commit;
 *      select * from t_act;
 *
 * JDBC事务必须掌握以下三行代码：
 *      conn.setAutoCommit(false);
 *      conn.commit();
 *      conn.rollback();
 */
public class JDBCTest14 {
    public static void main(String[] args) {
        // JDBC的事务机制。
        // 先来研究一下JDBC当中的事务是否是自动提交机制:结果是JDBC默认情况下采用的是自动提交事务机制。
        // 什么是自动提交？只要在JDBC中执行任何一条DML语句，则自动提交。
        // 在实际的开发中，通常一个业务都是由多条DML语句联合来完成的，必须保证这多条DML语句同时成功或者同时失败。
        // 结论：JDBC的自动提交机制必须修改成手动提交。
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            // 开启事务为手动提交机制，将自动提交机制关闭
            conn.setAutoCommit(false);

            // act-001向act-002转账10000
            // 两条update语句，先将act-001账户减去10000，再将act-002账户加上10000.
            String sql = "update t_act set balance = ? where actno = ?";
            ps = conn.prepareStatement(sql);
            // 给?传值
            ps.setDouble(1, 40000.0);
            ps.setString(2, "act-001");
            int count = ps.executeUpdate(); // 更新1条记录

            /*String s = null;
            s.toString();*/

            // 给?传值
            ps.setDouble(1, 10000.0);
            ps.setString(2, "act-002");
            count += ps.executeUpdate(); // 更新1条记录，总共累计更新2条表示成功。

            // 以上程序都没有发生任何异常，程序才能执行到此处。
            // 完整业务完成了，事务应该提交了
            conn.commit();

            System.out.println(count == 2 ? "转账成功" : "转账失败");
        } catch (Exception e) {
            // 回滚事务
            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}
