package com.imorning.im.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.imorning.im.R;
import com.imorning.im.action.UserAction;
import com.imorning.im.adapter.FriendSearchResultAdapter;
import com.imorning.im.bean.ApplicationData;
import com.imorning.im.bean.User;
import com.imorning.im.global.Result;
import com.imorning.im.view.TitleBarView;

import java.util.List;

public class FriendSearchResultActivity extends Activity {


    private ListView mListviewOfResults;
    private TitleBarView mTitleBarView;
    private List<User> mFriendList;
    private User requestee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend_search_result);
        initView();
        initEvent();
    }

    private void initView() {
        mListviewOfResults = findViewById(R.id.friend_search_result_listview);
        mTitleBarView = findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("查找好友结果");
    }

    private void initEvent() {

        mFriendList = ApplicationData.getInstance().getFriendSearched();
        System.out.println(mFriendList.size() + "friendSearch result");

        mListviewOfResults.setAdapter(new FriendSearchResultAdapter(FriendSearchResultActivity.this, mFriendList));

        mListviewOfResults.setOnItemClickListener((parent, view, position, id) -> {
            User mySelf = ApplicationData.getInstance().getUserInfo();
            requestee = mFriendList.get(position);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FriendSearchResultActivity.this);

            alertDialogBuilder.setTitle(null);
            if (mySelf.getId() == requestee.getId()) {
                alertDialogBuilder
                        .setMessage("你不能添加自己为好友")
                        .setCancelable(true)
                        .setPositiveButton("确定", (dialog, id1) -> dialog.cancel());
            } else if (!hasFriend(ApplicationData.getInstance().getFriendList(), requestee)) {
                alertDialogBuilder
                        .setMessage("确定发送请求？")
                        .setCancelable(true)
                        .setPositiveButton("是", (dialog, id12) -> UserAction.sendFriendRequest(Result.MAKE_FRIEND_REQUEST, requestee.getId()))
                        .setNegativeButton("否", (dialog, id13) -> dialog.cancel());
            } else {
                alertDialogBuilder
                        .setMessage("你们已经是好友")
                        .setCancelable(true)
                        .setPositiveButton("确定", (dialog, id14) -> dialog.cancel());
            }

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean hasFriend(List<User> friendList, User person) {
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getId() == person.getId())
                return true;
        }
        return false;
    }

}
