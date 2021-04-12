package com.imorning.im.interfaces;

import java.io.UnsupportedEncodingException;


public interface IAppManager {

    String getUsername();

    String sendMessage(String username, String tousername, String message) throws UnsupportedEncodingException;

    String authenticateUser(String usernameText, String passwordText) throws UnsupportedEncodingException;

    void messageReceived(String username, String message);

    //void setUserKey(String value);
    boolean isNetworkConnected();

    boolean isUserAuthenticated();

    String getLastRawFriendList();

    void exit();

    String signUpUser(String usernameText, String passwordText, String email);

    String addNewFriendRequest(String friendUsername);

    String sendFriendsReqsResponse(String approvedFriendNames, String discardedFriendNames);


}
