package com.imorning.im.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class PermissionUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void requestPermission(Context mContext) {
        context = mContext;

        String[] permissionLists = new String[]{
                Permission.NOTIFICATION_SERVICE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.CAMERA,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_FINE_LOCATION
        };
        XXPermissions.with(mContext)
                .permission(permissionLists)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            toast("获取权限成功");
                        } else {
                            toast("获取部分权限成功，但部分权限未正常授予");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            toast("被永久拒绝授权，请手动授予权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(context, permissions);
                        } else {
                            toast("获取权限失败");
                        }
                    }
                });

    }

    private static void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
