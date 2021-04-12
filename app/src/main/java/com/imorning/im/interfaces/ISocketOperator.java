package com.imorning.im.interfaces;


public interface ISocketOperator {

    String sendHttpRequest(String params);

    int startListening(int port);

    void stopListening();

    void exit();

    int getListeningPort();

}
