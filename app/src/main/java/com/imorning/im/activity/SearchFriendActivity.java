package com.imorning.im.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.imorning.im.BaseActivity;
import com.imorning.im.R;
import com.imorning.im.action.UserAction;
import com.imorning.im.bean.ApplicationData;
import com.imorning.im.bean.TranObject;
import com.imorning.im.bean.User;
import com.imorning.im.util.VerifyUtils;
import com.imorning.im.view.TitleBarView;

import java.io.IOException;
import java.util.ArrayList;

public class SearchFriendActivity extends BaseActivity implements
        OnClickListener {

    private static boolean mIsReceived;
    private TitleBarView mTitleBarView;
    private EditText mSearchEtName;
    private Button mBtnSearchByName;
    private Spinner mSearchSpLowage;
    private Spinner mSearchSpHighage;
    private RadioGroup mRgpSex;
    private Button mBtnSearchByElse;
    private boolean flag = false;

    public static void messageArrived(TranObject mReceived) {
        ArrayList<User> list = (ArrayList<User>) mReceived.getObject();
        ApplicationData.getInstance().setFriendSearched(list);
        mIsReceived = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfriend);

        initViews();
        initEvents();
    }

    @Override
    protected void initViews() {
        mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("查找朋友");
        mSearchEtName = (EditText) findViewById(R.id.search_friend_by_name_edit_name);
        mBtnSearchByName = (Button) findViewById(R.id.search_friend_by_name_btn_search);

        mSearchSpLowage = (Spinner) findViewById(R.id.search_friend_by_else_spinner_lowage);
        mSearchSpHighage = (Spinner) findViewById(R.id.search_friend_by_else_spinner_highage);
        mRgpSex = (RadioGroup) findViewById(R.id.search_friend_by_else_rgp_choose_sex);
        mBtnSearchByElse = (Button) findViewById(R.id.search_friend_by_else_btn_search);

    }

    @Override
    protected void initEvents() {
        mIsReceived = false;
        mBtnSearchByName.setOnClickListener(this);
        mBtnSearchByElse.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_friend_by_name_btn_search:
                flag = false;
                String searchName = mSearchEtName.getText().toString();
                if (searchName.equals("")) {
                    showCustomToast("请填写账号");
                    mSearchEtName.requestFocus();
                } else if (!VerifyUtils.matchAccount(searchName)) {
                    showCustomToast("账号格式错误");
                    mSearchEtName.requestFocus();
                } else {
                    try {
                        flag = true;
                        UserAction.searchFriend("0" + " " + searchName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.search_friend_by_else_btn_search:
                flag = false;
                int minAge = mSearchSpLowage.getSelectedItemPosition() + 5;
                int maxAge = minAge + 35;
                if (minAge > maxAge)
                    showCustomToast("年龄选择有误，请重新选择！");
                else {
                    int sex = 3;// 默认全部
                    int choseId = mRgpSex.getCheckedRadioButtonId();
                    switch (choseId) {
                        case R.id.search_friend_by_else_rdbtn_female:
                            sex = 0;
                            break;
                        case R.id.search_friend_by_else_rdbtn_male:
                            sex = 1;
                            break;
                        default:
                            break;
                    }
                    try {
                        flag = true;
                        UserAction.searchFriend("1" + " " + minAge + " " + maxAge + " "+ sex);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        if (flag) {
            mIsReceived = false;
            showLoadingDialog("正在查找...",false);
            while (!mIsReceived) {

            }
            System.out.println("准备跳转查找结果页面");
            Intent intent = new Intent(this, FriendSearchResultActivity.class);
            //Bundle mBundle = new Bundle();
            //mBundle.putSerializable("result", mReceivedMessage);
            //intent.putExtras(mBundle);
            startActivity(intent);
            finish();
        }

    }

}
