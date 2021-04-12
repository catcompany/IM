package com.imorning.im.dialog;

import android.content.Context;

import com.imorning.im.BaseDialog;
import com.imorning.im.R;
import com.imorning.im.view.FlippingImageView;
import com.imorning.im.view.HandyTextView;

public class FlippingLoadingDialog extends BaseDialog {

    private HandyTextView mHtvText;
    private String mText;

    public FlippingLoadingDialog(Context context, String text) {
        super(context);
        mText = text;
        init();
    }

    private void init() {
        setContentView(R.layout.common_flipping_loading_diloag);
        FlippingImageView flippingImageView = (FlippingImageView) findViewById(R.id.loadingdialog_fiv_icon);
        mHtvText = (HandyTextView) findViewById(R.id.loadingdialog_htv_text);
        flippingImageView.startAnimation();
        mHtvText.setText(mText);
    }

    public void setText(String text) {
        mText = text;
        mHtvText.setText(mText);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
