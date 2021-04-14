package com.imorning.im.database;

import com.imorning.im.bean.ChatEntity;
import com.imorning.im.bean.TranObject;
import com.imorning.im.bean.TranObjectType;
import com.imorning.im.bean.User;
import com.imorning.im.global.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * 对保存信息表的操作
 */
public class SaveMsgDao {
    private static final HashMap<Integer, Result> idResult = new HashMap<>();
    private static final HashMap<Result, Integer> resultId = new HashMap<>();
    private static final HashMap<Integer, TranObjectType> idTintype = new HashMap<>();
    private static final HashMap<TranObjectType, Integer> tintypeId = new HashMap<>();

    static {
        //为Result枚举添加映射
        idResult.put(0, null);
        resultId.put(null, 0);
        idResult.put(1, Result.FRIEND_REQUEST_RESPONSE_ACCEPT);
        resultId.put(Result.FRIEND_REQUEST_RESPONSE_ACCEPT, 1);
        idResult.put(2, Result.FRIEND_REQUEST_RESPONSE_REJECT);
        resultId.put(Result.FRIEND_REQUEST_RESPONSE_REJECT, 2);
        idResult.put(3, Result.MAKE_FRIEND_REQUEST);
        resultId.put(Result.MAKE_FRIEND_REQUEST, 3);
        //为TranObjectType枚举添加映射
        idTintype.put(0, null);
        tintypeId.put(null, 0);
        idTintype.put(1, TranObjectType.FRIEND_REQUEST);
        tintypeId.put(TranObjectType.FRIEND_REQUEST, 1);
        idTintype.put(2, TranObjectType.MESSAGE);
        tintypeId.put(TranObjectType.MESSAGE, 2);
    }

    private SaveMsgDao() {

    }

    /**
     * 插入消息
     */
    public static void insertSaveMsg(int send_id, TranObject tran) {
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "insert into " + DataBaseConfig.TABLE_MSG + "(sendid,getid,msg,trantype,time,resultType,messageType,sendname)" +
                "values(?,?,?,?,?,?,?,?)";
        Connection con = DBPool.getConnection();
        try {
            if (con != null) {
                con.setAutoCommit(false);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String msg = "";
        int messageType = ChatEntity.RECEIVE;
        PreparedStatement ps;
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setInt(MSG_COLUMN.SEND_ID.value, send_id);
            ps.setInt(MSG_COLUMN.GET_ID.value, tran.getReceiveId());
            //message信息，时间来自ChatEntity对象，否则在tran中
            if (tran.getTranType() == TranObjectType.MESSAGE) {
                ChatEntity chatEntity = (ChatEntity) tran.getObject();
                msg = chatEntity.getContent();
                messageType = chatEntity.getMessageType();
                ps.setString(MSG_COLUMN.TIME.value, chatEntity.getSendTime());
            } else {
                ps.setString(MSG_COLUMN.TIME.value, tran.getSendTime());
            }
            ps.setString(MSG_COLUMN.MSG.value, msg);
            ps.setInt(MSG_COLUMN.TRAN_TYPE.value, tintypeId.get(tran.getTranType()));
            ps.setInt(MSG_COLUMN.RESULT_TYPE.value, resultId.get(tran.getResult()));
            ps.setInt(MSG_COLUMN.MESSAGE_TYPE.value, messageType);
            ps.setString(MSG_COLUMN.SEND_NAME.value, tran.getSendName());
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 删除保存的离线信息
     */
    public static void deleteSaveMsg(int getid) {
        String sql0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "delete from  " + DataBaseConfig.TABLE_MSG +
                " where getid=?";
        Connection con = DBPool.getConnection();
        PreparedStatement ps;
        try {
            if (con != null) {
                con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sql0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setInt(1, getid);
            ps.execute();
            con.commit();

        } catch (SQLException e) {
            try {
                Objects.requireNonNull(con).rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 查询所有的离线消息
     */
    public static ArrayList<TranObject> selectMsg(int id) {
        ArrayList<TranObject> msgList = new ArrayList<>();
        String sq0 = "use " + DataBaseConfig.DBNAME;
        String sql1 = "select * from "
                + DataBaseConfig.TABLE_MSG +
                " where getid=?";
        Connection con = DBPool.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = Objects.requireNonNull(con).prepareStatement(sq0);
            ps.execute();
            ps = con.prepareStatement(sql1);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                TranObject tran = new TranObject();
                tran.setSendId(rs.getInt("sendid"));
                tran.setTranType(idTintype.get(rs.getInt("trantype")));
                tran.setSendName(rs.getString("sendname"));
                tran.setResult(idResult.get(rs.getInt("resultType")));
                if (idTintype.get(rs.getInt("trantype")) == TranObjectType.MESSAGE) {
                    ChatEntity chatEntity = new ChatEntity();
                    chatEntity.setContent(rs.getString("msg"));
                    chatEntity.setMessageType(rs.getInt("messageType"));
                    chatEntity.setReceiverId(tran.getReceiveId());
                    chatEntity.setSenderId(tran.getSendId());
                    chatEntity.setSendTime(rs.getString("time"));
                    tran.setObject(chatEntity);
                } else if (idResult.get(rs.getInt("resultType")) == Result.FRIEND_REQUEST_RESPONSE_ACCEPT) {
                    ArrayList<User> list = UserDao.selectFriendByAccountOrID(tran.getSendId());
                    tran.setObject(list.get(0));
                    tran.setSendTime(rs.getString("time"));
                } else {
                    tran.setSendTime(rs.getString("time"));
                }
                msgList.add(tran);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return msgList;
    }

}
