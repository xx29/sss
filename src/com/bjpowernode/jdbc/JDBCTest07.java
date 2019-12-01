package com.bjpowernode.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: 动力节点
 * 2019/6/18
 *
 * 1、要实现的功能：
 *      模拟用户登录，用户输入用户名和密码之后，后台数据库验证是否有效，有效登录成功，无效登录失败。
 *      前端我们采用dos命令窗口实现。
 *
 * 2、准备数据库表：用户表
 *      t_user
 *      id      name        pwd       email
 *      -------------------------------------------------------
 *      1        zs         123         zs@bjpowernode.com
 *      ....
 *      drop table if exists t_user;
 *      create table t_user(
 *          id int primary key auto_increment,
 *          name varchar(255),
 *          pwd varchar(255),
 *          email varchar(255)
 *      );
 *      insert into t_user(name,pwd,email) values('zs','123','zs@bjpowernode.com');
 *      insert into t_user(name,pwd,email) values('ls','456','ls@bjpowernode.com');
 *      commit;
 *      select * from t_user;
 *
 * 3、当前程序存在的问题：存在sql注入现象。
 *      什么是sql注入？
 *          用户提供的信息中含有sql语句的关键字，和底层的sql语句拼接在一起，导致sql语句的原意被扭曲了。
 *      导致sql注入的根本原因是什么？
 *          用户提供的信息中含有sql语句的关键字，并且这些关键字编译进去了。导致sql语句原意扭曲了。
 *
 */
public class JDBCTest07 {
    public static void main(String[] args) {
        // 初始化用户界面,接收用户名和密码信息
        Map<String,String> userMap = initUI();
        // 验证用户名和密码信息
        boolean loginSuccess = login(userMap.get("name"), userMap.get("pwd"));
        // 输出结果
        System.out.println(loginSuccess ? "登录成功" : "登录失败");
    }

    /**
     * 登录
     * @param name 用户名
     * @param pwd 密码
     * @return true表示登录成功，false表示失败
     */
    private static boolean login(String name, String pwd) {
        boolean flag = false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root","123456");
            // 获取操作对象
            stmt = conn.createStatement();
            // 执行sql
            String sql = "select * from t_user where name='"+name+"' and pwd='"+pwd+"'"; // 先进行sql语句的拼接，关键字拼进去了。
            System.out.println(sql);
            rs = stmt.executeQuery(sql); // 发送sql语句给数据库，数据库将这些关键字也编译进去了。
            // 处理结果集
            if(rs.next()){
                // 登录成功
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
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
        return flag;
    }

    /*
    private static LoginResult login(String name, String pwd) {
        // return LoginResult.FAIL;
        // return LoginResult.SUCCESS;
        return LoginResult.TIMEOUT;
    }
    */

    /**
     * 初始化界面
     */
    private static Map<String, String> initUI() {
        System.out.println("欢迎使用购物商城，请先登录！");
        Scanner s = new Scanner(System.in);
        System.out.print("用户名：");
        String name = s.nextLine();
        System.out.print("密码：");
        String pwd = s.nextLine();
        Map<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("pwd", pwd);
        return map;
    }
}

























