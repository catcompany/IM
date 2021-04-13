package com.imorning.im.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.imorning.im.R;
import com.imorning.im.util.PermissionUtils;
import com.imorning.im.util.SpUtil;

public class WelcomeActivity extends AppCompatActivity {
    private Context mContext;
    private ImageView mImageView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        findView();
        init();
    }

    private void findView() {
        mImageView = (ImageView) findViewById(R.id.iv_welcome);
    }

    private void init() {
        mImageView.postDelayed(() -> {
            SpUtil.getInstance();
            sp = SpUtil.getSharePerference(mContext);
            SpUtil.getInstance();
            boolean isFirst = SpUtil.isFirst(sp);
            if (!isFirst) {
                SpUtil.getInstance();
                SpUtil.setBooleanSharedPerference(sp, "isFirst", true);
            }
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }, 2000);

    }
}
