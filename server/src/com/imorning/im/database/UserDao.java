package com.imorning.im.database;

import com.imorning.im.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 数据库操作
 */
public class UserDao {

    private UserDao() {
    }

    /**
     * 查询账号是否存在
     */
    public static boolean selectAccount(String account) {
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "select * from " + DataBaseConfig.TABLE_USER + " where account=?";
        Connection con = DBPool.getConnection();
        try {
            Objects.requireNonNull(con).setAutoCommit(false);
            PreparedStatement ps;
            ps = con.prepareStatement(sql0);
            ps.execute();

            ps = con.prepareStatement(sql1);
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();
            return rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            DBPool.close(con);
        }
        return false;
    }

    /**
     * 向数据库中添加账户
     *
     * @return 用户数
     */
    public static int insertInfo(User user) {
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "insert into " + DataBaseConfig.TABLE_USER + " (account,name,photo,birthday,password,gender)"
                + " values(?,?,?,?,?,?)";
        Connection con = DBPool.getConnection();
        try {
            Objects.requireNonNull(con).setAutoCommit(false);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        PreparedStatement ps;
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setString(1, user.getAccount());
            ps.setString(2, user.getUserName());
            ps.setBytes(3, user.getPhoto());
            ps.setDate(4, new java.sql.Date(user.getBirthday().getTime()));
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getGender());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                //System.out.println("插入数据库异常，正在进行回滚..");
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return getLastID(con);
    }

    /**
     * 得到最后一次插入的值
     */
    public static int getLastID(Connection con) {
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "select MAX(id) as ID from " + DataBaseConfig.TABLE_USER;// 注意:使用MAX(ID) 必须加上 as
        // id 翻译
        PreparedStatement ps;
        ResultSet rs;
        int id = -1;
        try {
            ps = con.prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();
            if (rs.first())
                id = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBPool.close(con);
        return id;

    }

    /**
     * 进行登录的验证
     */
    public static boolean login(User user) {
        boolean isExisted = false;
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "select * from " + DataBaseConfig.TABLE_USER + " where account=? and password=?";
        Connection con = DBPool.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setString(1, user.getAccount());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            if (rs.first()) {
                isExisted = true;
                // 为用户添加自己的id
                user.setId(rs.getInt("id"));
                user.setAccount(rs.getString("account"));
                user.setBirthday(rs.getDate("birthday"));
                user.setGender(rs.getInt("gender"));
                user.setPassword(rs.getString("password"));
                user.setUserName(rs.getString("name"));
                user.setPhoto(rs.getBytes("photo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            DBPool.close(con);
        }
        return isExisted;
    }

    /**
     * 更新在线状态
     */
    public static void updateIsOnline(int id, int isOnline) {
        if (id < 0) {
            return;
        }
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "update " + DataBaseConfig.TABLE_USER + " set isOnline=? where id=?";
        Connection con = DBPool.getConnection();
        try {
            Objects.requireNonNull(con).setAutoCommit(false);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        PreparedStatement ps;
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setInt(1, isOnline);
            ps.setInt(2, id);
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                Objects.requireNonNull(con).rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        if (con != null) {
            DBPool.close(con);
        }
    }

    /**
     * 更新在线状态
     *
     * @param id       用户id
     * @param isOnline 是否在线
     */
    public static void updateIsOnline(int id, boolean isOnline) {
        if (id < 0) {
            return;
        }
        updateIsOnline(id, isOnline ? 1 : 0);
    }

    public static ArrayList<User> selectFriendByAccountOrID(Object accountOrId) {
        ArrayList<User> list = new ArrayList<>();
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "";
        int conFlag = 0;// 默认是0 表示使用id查找 1为使用id
        if (accountOrId instanceof String) {
            sql1 = "select * from " + DataBaseConfig.TABLE_USER + " where account=?";
            conFlag = 1;
        } else if (accountOrId instanceof Integer)
            sql1 = "select * from " + DataBaseConfig.TABLE_USER + " where id=?";
        Connection con = DBPool.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            if (conFlag == 1)
                ps.setString(1, (String) accountOrId);
            else if (accountOrId instanceof Integer) {
                ps.setInt(1, (Integer) accountOrId);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                User friend = new User();
                friend.setId(rs.getInt("id"));
                friend.setAccount(rs.getString("account"));
                friend.setBirthday(rs.getDate("birthday"));
                friend.setGender(rs.getInt("gender"));
                friend.setUserName(rs.getString("name"));
                friend.setIsOnline(rs.getInt("isOnline") == 1);
                friend.setPhoto(rs.getBytes("photo"));
                friend.setLocation(rs.getString("location"));
                list.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            DBPool.close(con);
        }
        return list;
        //return list.size() > 0 ? list : null;
    }

    public static ArrayList<User> selectFriendByMix(String[] mix) {
        ArrayList<User> list = new ArrayList<User>();
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "select * "
                + "from " + DataBaseConfig.TABLE_USER
                + " where ((YEAR(CURDATE())-YEAR(birthday))-(RIGHT(CURDATE(),5)<RIGHT(birthday,5))) "
                + "between ? and ? ";
        Connection con = DBPool.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            if (mix[3].equals("3"))
                sql1 += "and gender=1 or gender=0";
            else if (mix[3].equals("1"))
                sql1 += "and gender=1";
            else if (mix[3].equals("0"))
                sql1 += "and gender=0";
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setInt(1, Integer.parseInt(mix[1]));
            ps.setInt(2, Integer.parseInt(mix[2]));
            rs = ps.executeQuery();
            while (rs.next()) {
                User friend = new User();
                friend.setId(rs.getInt("id"));
                friend.setAccount(rs.getString("account"));
                friend.setBirthday(rs.getDate("birthday"));
                friend.setGender(rs.getInt("gender"));
                friend.setUserName(rs.getString("name"));
                friend.setIsOnline(rs.getInt("isOnline") == 1);
                friend.setPhoto(rs.getBytes("photo"));
                friend.setLocation(rs.getString("location"));
                list.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            DBPool.close(con);
        }
        return list;
    }

}
