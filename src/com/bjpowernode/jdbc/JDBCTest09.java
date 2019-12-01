package com.bjpowernode.jdbc;

import java.sql.*;
import java.util.Scanner;

/**
 * Author: 动力节点
 * 2019/6/20
 */
public class JDBCTest09 {
    public static void main(String[] args) {
        // 什么时候我们必须使用Statement：有的业务是需要sql注入的。
        // 例如京东商城的商品按照price价格的升序和降序排列。

        // 升序/降序
        Scanner s = new Scanner(System.in);
        System.out.print("请输入asc升序，输入desc降序：");
        String orderKey = s.nextLine(); // desc asc

        // JDBC程序(按照emp表中的sal升序或者降序)
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456");
            ps = conn.prepareStatement("select empno,ename,sal from emp order by sal ?");
            // select empno,ename,sal from emp order by sal 'asc'
            // select empno,ename,sal from emp order by sal 'desc'
            ps.setString(1, orderKey);
            rs = ps.executeQuery();
            while(rs.next()){
                int empno = rs.getInt("empno");
                String ename = rs.getString("ename");
                double sal = rs.getDouble("sal");
                System.out.println(empno + "," + ename + "," + sal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
