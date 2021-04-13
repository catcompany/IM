package com.imorning.im.action;

import com.imorning.im.bean.ApplicationData;
import com.imorning.im.bean.ChatEntity;
import com.imorning.im.bean.TranObject;
import com.imorning.im.bean.TranObjectType;
import com.imorning.im.bean.User;
import com.imorning.im.global.Result;
import com.imorning.im.network.NetService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserAction {

    private static final NetService mNetService = NetService.getInstance();

    public static void accountVerify(String account) throws IOException {

        TranObject t = new TranObject(account, TranObjectType.REGISTER_ACCOUNT);
        mNetService.send(t);

    }

    public static void register(User user) throws IOException {

        TranObject t = new TranObject(user, TranObjectType.REGISTER);
        mNetService.send(t);

    }

    public static void loginVerify(User user) throws IOException {
        TranObject t = new TranObject(user, TranObjectType.LOGIN);
        mNetService.send(t);
    }

    public static void searchFriend(String message) throws IOException {
        TranObject t = new TranObject(message, TranObjectType.SEARCH_FRIEND);
        mNetService.send(t);
    }

    public static void sendFriendRequest(Result result, Integer id) {
        TranObject t = new TranObject();
        t.setReceiveId(id);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.getDefault());
        String sendTime = sdf.format(date);
        t.setSendTime(sendTime);
        User user = ApplicationData.getInstance().getUserInfo();
        t.setResult(result);
        t.setSendId(user.getId());
        t.setTranType(TranObjectType.FRIEND_REQUEST);
        t.setSendName(user.getUserName());
        try {
            mNetService.send(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(ChatEntity message) {

        TranObject t = new TranObject();
        t.setTranType(TranObjectType.MESSAGE);
        t.setReceiveId(message.getReceiverId());
        t.setSendName(ApplicationData.getInstance().getUserInfo().getUserName());
        t.setObject(message);
        try {
            mNetService.send(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
