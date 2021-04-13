package com.imorning.im.network;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetConnect {

	// TODO: 2021/4/12 绑定服务器
    private static final String SERVER_IP = "192.168.1.107";
    private static final int SERVER_PORT = 1124;
    private Socket mClientSocket = null;
    private boolean mIsConnected = false;

    public NetConnect() {
    }

    public void startConnect() {
        try {
            mClientSocket = new Socket();
            mClientSocket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), 3000);
            Log.d("Network", "服务器连接成功");
			mIsConnected = mClientSocket.isConnected();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.d("Network", "服务器地址无法解析");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Network", "Socket io异常");
        }
    }

    public boolean getIsConnected() {
        return mIsConnected;
    }

    public Socket getSocket() {
        return mClientSocket;
    }

}
