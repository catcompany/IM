package com.imorning.im.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 使用数据库连接池加大响应速度
 */
public class DBPool {
    private static DataSource ds;

    private DBPool() {
    }

    static {
        /*try {
            InputStream in = DBPool.class.getClassLoader().getResourceAsStream("db_config.properties");
            Properties pro = new Properties();
            pro.load(in);
            ds = BasicDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            //con = ds.getConnection();
            //加载jdbc驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/im_database?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC", "root", "root");
            //System.out.println("成功连接到数据库");
        } catch (SQLException | ClassNotFoundException e) {
            //System.out.println("获取数据库连接失败....");
            e.printStackTrace();
        }
        return con;
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
