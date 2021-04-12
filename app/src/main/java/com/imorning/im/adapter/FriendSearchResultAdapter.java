package com.imorning.im.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imorning.im.R;
import com.imorning.im.bean.User;
import com.imorning.im.util.PhotoUtils;

import java.util.Calendar;
import java.util.List;

public class FriendSearchResultAdapter extends BaseAdapter {
    private final List<User> mVector;
    private final LayoutInflater mInflater;
    private final Context mContext0;

    public FriendSearchResultAdapter(Context context, List<User> vector) {
        this.mVector = vector;
        mInflater = LayoutInflater.from(context);
        mContext0 = context;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
	public View getView(int position, View convertView, ViewGroup root) {
        ImageView avatarView;
        TextView nameView;
        TextView ageView;
        TextView sexView;

        User user = mVector.get(position);
        Bitmap photo = PhotoUtils.getBitmap(user.getPhoto());
        String name = user.getUserName();
		//int age = new Date().getYear() - user.getBirthday().getYear();
        Calendar today = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(user.getBirthday());
        int age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        int gender = user.getGender();

        convertView = mInflater.inflate(R.layout.friend_search_result_item,
                null);
        avatarView = (ImageView) convertView
                .findViewById(R.id.cc0_friend_search_result_item_avatar);
        nameView = (TextView) convertView
                .findViewById(R.id.cc0_friend_search_result_item_name);
        ageView = (TextView) convertView
                .findViewById(R.id.cc0_friend_search_result_item_age);
        sexView = (TextView) convertView
                .findViewById(R.id.cc0_friend_search_result_item_gender);

        nameView.setText(name);

        if (photo != null) {
            avatarView.setImageBitmap(photo);
        }
        ageView.setText(age + "岁");
        if (gender == 0) {
            sexView.setText("女");
        } else {
            sexView.setText("男");
        }

        return convertView;
    }

    public int getCount() {
        return mVector.size();
    }

    public Object getItem(int position) {
        return mVector.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

}
