package com.bjpowernode.jdbc;

import java.sql.*;
import java.util.Scanner;

/**
 * Author: 动力节点
 * 2019/6/18
 *
 * 解决sql注入：
 *      1、需要使用java.sql.PreparedStatement接口，这个接口的父接口是java.sql.Statement。
 *      PreparedStatement是预编译的数据库操作对象。
 *      2、怎么解决的sql注入？
 *          使用PreparedStatement对sql语句框架进行预先编译，然后再给占位符?传值。
 *          即使用户提供的信息中含有sql语句的关键字，但是这些关键字没有参与编译，不起作用。
 *      3、Statement和PreparedStatement对比？
 *          第一：Statement存在sql注入现象。PreparedStatement避免sql注入现象。
 *          第二：Statement没有PreparedStatement效率高。
 *              Statement是编译一次执行一次。
 *              PreparedStatement是编译一次，可执行N次。
 *          第三：PreparedStatement在编译阶段会进行类型安全检查。
 *      4、Statement也有需要的时候：
 *              在实际开发中，当需要sql注入的时候，必须使用Statement，PreparedStatement无法完成sql注入。
 *
 */
public class JDBCTest08 {

    public static void main(String[] args) {

        // 接收用户的键盘输入
        Scanner s = new Scanner(System.in);
        System.out.print("用户名：");
        String name = s.nextLine();
        System.out.print("密码：");
        String pwd = s.nextLine();

        Connection conn = null;
        PreparedStatement ps = null; // 预编译的数据库操作对象。
        ResultSet rs = null;
        try {
            // 1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode", "root", "123456");
            // 3.获取预编译的数据库操作对象PreparedStatement
            // ? 是占位符，注意：? 两边不能加单引号，加上单引号'?'就不是占位符了，就变成普通的问号字符了。
            // 以下带有?占位符的sql语句被称为框架sql。（只提供了一个sql语句的框架，没有具体的值。将来占位符上传值。）
            String sql = "select * from t_user where name = ? and pwd = ?";
//            String sql2 = "insert into t_user(name,pwd,email) values(?,?,?)";
//            String sql3 = "update t_user set name = ?, pwd = ?, email = ? where id = ?";
//            String sql4 = "delete from t_user where id = ?";
            ps = conn.prepareStatement(sql); // 这行代码表示将sql语句发送给数据库管理系统，数据库管理系统对这个sql语句框架进行预编译。
            // 给?占位符传值（注意：JDBC中所有下标都是从1开始，第1个问号的下标是1，第2个问号的下标是2）
            ps.setString(1, name); // 为什么调用setString而不是setInt呢？因为name是varchar类型。
            ps.setString(2, pwd); // 由于setString方法执行了，占位符?将来会自动添加单引号。
            // 4.执行sql
            rs = ps.executeQuery(); // 不要将sql参数传进来。
            // 5.处理结果集
            if(rs.next()){
                System.out.println("login success!");
            }else{
                System.out.println("login error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6.释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
