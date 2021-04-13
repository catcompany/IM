package com.imorning.im.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 使用数据库连接池加大响应速度
 */
public class DBPool {
    private static final String dbUserName = "morning2021_f";
    private static final String dbPwd = "morning20210401";
    private static final String dbHost = "co3jhe2l.2419.dnstoo.com";
    private static final String dbPort = "5501";

    private DBPool() {
    }

    public static Connection getConnection() {
        try {
            //加载jdbc驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName(com.mysql.jdbc.Driver.class.getName());
            return DriverManager.getConnection("jdbc:mysql://" +
                            dbHost + ":" +
                            dbPort + "/" +
                            ServerDatabaseInfo.DBNAME +
                            "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC",
                    dbUserName, dbPwd);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
