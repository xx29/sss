package com.bjpowernode.jdbc;

import com.bjpowernode.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author: 动力节点
 * 2019/6/20
 */
public class JDBCTest15 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            DBUtil.beginTransaction(conn);
            String sql = "update t_act set balance = ? where actno = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, 40000.0);
            ps.setString(2, "act-001");
            int count = ps.executeUpdate(); // 更新1条记录
            ps.setDouble(1, 10000.0);
            ps.setString(2, "act-002");
            count += ps.executeUpdate(); // 更新1条记录，总共累计更新2条表示成功。
            DBUtil.commitTransaction(conn);
            System.out.println(count == 2 ? "转账成功" : "转账失败");
        } catch (Exception e) {
            DBUtil.rollbackTransaction(conn);
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}
