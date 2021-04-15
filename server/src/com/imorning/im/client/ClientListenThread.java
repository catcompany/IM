package com.imorning.im.client;

import com.imorning.im.bean.TranObject;
import com.imorning.im.bean.TranObjectType;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;

/**
 * 服务器对客户端的监听监听
 */
public class ClientListenThread implements Runnable {
    private final ClientActivity clientActivity;
    private ObjectInputStream objectInputStream;
    private boolean isRunning;

    public ClientListenThread(ObjectInputStream objectInputStream, ClientActivity clientActivity) {
        this.objectInputStream = objectInputStream;
        this.clientActivity = clientActivity;
        isRunning = true;
    }

    @Override
    public void run() {
        SocketAddress s = clientActivity.getmClient().getRemoteSocketAddress();
        while (isRunning) {
            readMsg();
        }
    }

    private void readMsg() {
        SocketAddress s = clientActivity.getmClient().getRemoteSocketAddress();
        try {
            TranObject tran = (TranObject) objectInputStream.readObject();
            TranObjectType type = tran.getTranType();
            switch (type) {
                case REGISTER_ACCOUNT:
                    String account = (String) tran.getObject();
                    clientActivity.checkAccount(account);
                    break;
                case REGISTER:
                    clientActivity.regist(tran);
                    break;
                case LOGIN:
                    clientActivity.login(tran);
                    break;
                case SEARCH_FRIEND:
                    clientActivity.searchFriend(tran);
                    break;
                case FRIEND_REQUEST:
                    clientActivity.friendRequset(tran);
                    break;
                case MESSAGE:
                    clientActivity.sendMessage(tran);
                default:
                    break;
            }
        } catch (EOFException e) {
            // TODO: 2021/4/14 此处会抛出 EOFException
            e.printStackTrace();
            clientActivity.close();
            System.exit(0);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        isRunning = false;
        objectInputStream = null;
    }

}
