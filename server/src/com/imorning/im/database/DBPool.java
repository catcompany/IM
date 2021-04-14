package com.imorning.im.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 使用数据库连接池加大响应速度
 */
public class DBPool {
    // TODO: 2021/4/13 数据库配置
    private final static String dbUserName = DataBaseConfig.dbUserName;
    private final static String dbPwd = DataBaseConfig.dbPwd;
    private final static String dbHost = DataBaseConfig.dbHost;
    private final static String dbPort = DataBaseConfig.dbPort;

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
                            DataBaseConfig.DBNAME +
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
