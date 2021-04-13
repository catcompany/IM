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
    private final ClientActivity client;
    private final ObjectInputStream read;
    private boolean isRunning;

    public ClientListenThread(ObjectInputStream read, ClientActivity client) {
        this.read = read;
        this.client = client;
        isRunning = true;
    }

    @Override
    public void run() {
        SocketAddress s = client.getmClient().getRemoteSocketAddress();
        while (isRunning) {
            readMsg();
        }
    }

    private void readMsg() {
        SocketAddress s = client.getmClient().getRemoteSocketAddress();
        try {
            TranObject tran = (TranObject) read.readObject();
            TranObjectType type = tran.getTranType();
            switch (type) {
                case REGISTER_ACCOUNT:
                    String account = (String) tran.getObject();
                    client.checkAccount(account);
                    break;
                case REGISTER:
                    client.regist(tran);
                    break;
                case LOGIN:
                    client.login(tran);
                    break;
                case SEARCH_FRIEND:
                    client.searchFriend(tran);
                    break;
                case FRIEND_REQUEST:
                    client.friendRequset(tran);
                    break;
                case MESSAGE:
                    client.sendMessage(tran);
                default:
                    break;
            }
        } catch (EOFException e) {
            client.close();
            e.printStackTrace();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        isRunning = false;
    }

}
