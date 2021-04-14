package com.imorning.im.database;

import com.imorning.im.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class FriendDao {
    //防止初始化
    private FriendDao() {
    }

    public static ArrayList<User> getFriend(int id) {
        ArrayList<User> list = new ArrayList<>();
        String sql0 = String.format("use %s", DataBaseConfig.DBNAME);
        String sql1 = "select * from " + DataBaseConfig.TABLE_FRIEND_LIST +
                " as f left outer join " + DataBaseConfig.TABLE_USER + " as u " +
                "on f.friendid=u.id " +
                "where master=?";
        Connection con = DBPool.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("friendid"));
                user.setAccount(rs.getString("account"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhoto(rs.getBytes("photo"));
                user.setGender(rs.getInt("gender"));
                user.setIsOnline(rs.getInt("isOnline") == 1);
                user.setUserName(rs.getString("name"));
                user.setLocation(rs.getString("location"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con != null) {
            DBPool.close(con);
        }
        return list;
    }

    public static void addFriend(int id, int friendID) {
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "insert into " + DataBaseConfig.TABLE_FRIEND_LIST + "(master,friendid) " +
                "values(?,?)";
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
            ps.setInt(1, id);
            ps.setInt(2, friendID);
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            try {
                //System.out.println("正在发生回滚...");
                e.printStackTrace();
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        DBPool.close(con);
    }

}
