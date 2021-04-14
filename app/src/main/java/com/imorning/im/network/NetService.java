package com.imorning.im.network;

import java.io.IOException;
import java.net.Socket;

import com.imorning.im.bean.TranObject;

import android.annotation.SuppressLint;
import android.content.Context;

public class NetService {

	@SuppressLint("StaticFieldLeak")
	private static NetService mInstance = null;

	private ClientListenThread mClientListenThread = null;
	private ClientSendThread mClientSendThread = null;
	private NetConnect netConnect = null;
	private Socket mClientSocket = null;
	private boolean mIsConnected = false;
	private Context mContext = null;

	private NetService() {

	}

	public void onInit(Context context) {
		this.mContext = context;
	}

	public static NetService getInstance() {
		if (mInstance == null) {
			mInstance = new NetService();
		}
		return mInstance;
	}

	public void setupConnection() {
		netConnect = new NetConnect();
		netConnect.startConnect();
		if (netConnect.getIsConnected()) {
			mIsConnected = true;
			mClientSocket = netConnect.getSocket();
			startListen(mClientSocket);
		} else {
			mIsConnected = false;
		}
	}

	public boolean isConnected() {
		return mIsConnected;
	}

	public void startListen(Socket socket) {
		mClientSendThread = new ClientSendThread(socket);
		mClientListenThread = new ClientListenThread(socket);
		mClientListenThread.start();
	}

	public void send(TranObject t) throws IOException {
		mClientSendThread.sendMessage(t);
	}

	public void closeConnection() {
		if (mClientListenThread != null) {
			mClientListenThread.close();
		}
		try {
			if (mClientSocket != null)
				mClientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		mIsConnected = false;
		mClientSocket = null;
	}
}
