package com.imorning.im.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.imorning.im.R;
import com.imorning.im.view.TitleBarView;

public class UserInfoFragment extends Fragment {
    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_userinfo, null);
        findView();
        init();
        return mBaseView;
    }

    private void findView() {
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
    }

    private void init() {
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("个人信息");
    }
}

