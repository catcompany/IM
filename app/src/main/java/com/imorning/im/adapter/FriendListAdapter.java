package com.imorning.im.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imorning.im.R;
import com.imorning.im.bean.ApplicationData;
import com.imorning.im.bean.User;

import java.util.List;

public class FriendListAdapter extends BaseAdapter {
    private static final String TAG = "FriendListAdapter";

    private final List<User> mFriendList;
    private final LayoutInflater mInflater;

    public FriendListAdapter(Context context, List<User> vector) {
        this.mFriendList = vector;
        mInflater = LayoutInflater.from(context);
        System.out.println("初始化FriendAdapter");
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup root) {
        ImageView avatarView;
        TextView nameView;
        ImageView isOnline;
        TextView introView;
        User user = mFriendList.get(position);
        Bitmap photo = (ApplicationData.getInstance().getFriendPhotoMap()).get(user.getId());
        String name = user.getUserName();
        String briefIntro = user.getUserBriefIntro();
        convertView = mInflater.inflate(R.layout.friend_list_item, null);
        avatarView = (ImageView) convertView
                .findViewById(R.id.user_photo);
        nameView = (TextView) convertView
                .findViewById(R.id.friend_list_name);
        isOnline = (ImageView) convertView.findViewById(R.id.stateicon);

        introView = (TextView) convertView
                .findViewById(R.id.friend_list_brief);

        nameView.setText(name);

        if (!user.isOnline()) {
            isOnline.setVisibility(View.GONE);
        }
        if (photo != null) {
            avatarView.setImageBitmap(photo);
        }
        introView.setText(briefIntro);


        return convertView;
    }

    public int getCount() {
        if (mFriendList == null) {
            Log.e(TAG, "friend list is null");
            return 0;
        }
        return mFriendList.size();
    }

    public Object getItem(int position) {
        return mFriendList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

}
