package com.bjpowernode.jdbc;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * Author: 动力节点
 * 2019/6/18
 */
public class JDBCTest06 {
    public static void main(String[] args) throws Exception{
        // 通常配置文件是放在类路径当中的。（不是必须的）
        // 什么是类路径：src目录下就是类的根路径。
        // 通常配置文件、资源文件会放到一个resources目录下。（不是必须的）

        // 读取配置文件
        // 以下这种方式只适合于资源存放在类路径下。默认从类的根路径下开始找资源。
        /*InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("resources/jdbc.properties");
        Properties pro = new Properties();
        pro.load(in);
        String driver = pro.getProperty("driver");
        String url = pro.getProperty("url");
        String user = pro.getProperty("user");
        String password = pro.getProperty("password");

        System.out.println(driver);
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);*/

        // 资源绑定器(常用的工具类)，专门用来绑定属性资源文件。但要求资源文件必须存放在类路径下。
        ResourceBundle bundle = ResourceBundle.getBundle("resources.jdbc");

        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        jdbc(driver,url,user,password);

    }

    /**
     * JDBC代码
     * @param driver
     * @param url
     * @param user
     * @param password
     */
    private static void jdbc(String driver, String url, String user, String password) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 注册驱动
            Class.forName(driver);
            // 获取连接
            conn = DriverManager.getConnection(url, user, password);
            // 获取操作对象
            stmt = conn.createStatement();
            // 执行sql
            String sql = "select t.job,t.maxsal,s.grade from (select job,max(sal) as maxsal from emp group by job) t join salgrade s on t.maxsal between s.losal and s.hisal";
            rs = stmt.executeQuery(sql);
            // 处理结果集
            while(rs.next()){
                String job = rs.getString("job");
                String maxsal = rs.getString("maxsal");
                String grade = rs.getString("grade");
                System.out.println(job + "," + maxsal + "," + grade);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
            if(stmt != null){
                try {
                    stmt.close();
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
