package com.imorning.im.interfaces;

import com.imorning.im.types.FriendInfo;
import com.imorning.im.types.MessageInfo;


public interface IUpdateData {
    void updateData(MessageInfo[] messages, FriendInfo[] friends, FriendInfo[] unApprovedFriends, String userKey);

}
