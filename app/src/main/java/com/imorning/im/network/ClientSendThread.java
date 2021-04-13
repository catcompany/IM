package com.imorning.im.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.imorning.im.bean.TranObject;

public class ClientSendThread {
	private Socket mSocket;
	private ObjectOutputStream oos = null;
	public ClientSendThread(Socket socket) {
		this.mSocket = socket;
		try {
			oos = new ObjectOutputStream(mSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	// TODO: 2021/4/13 此方法会造成android.os.NetworkOnMainThreadException，后续修改优化
	public void sendMessage(TranObject t) throws IOException{
		oos.writeObject(t);
		oos.flush();
	}
}
