package com.sign.pdf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by CHH on 2018/2/28.
 */

public class PermissionControl {
    /**
     * @param permission 要检验的权限
     * @return ture：有权限
     */
    public static boolean check(final Activity activity, final String permission) {
        int i = ContextCompat.checkSelfPermission(activity, permission);
        if (i == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param sm          说明读取权限的原因
     * @param permission  要请求的权限
     * @param requestCode 请求码
     */
    public static void reqstPermission(final Activity activity, String sm, final String permission, final int requestCode) {
        //是否被拒绝过：被拒绝后，会返回true
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            //说明原因
            new AlertDialog.Builder(activity)
                    .setMessage(sm)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //请求权限
                            String[] permissions = new String[]{permission};
                            ActivityCompat.requestPermissions(activity, permissions, requestCode);
                        }
                    })
                    .show();
        } else {
            String[] permissions = new String[]{permission};
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    // 启动应用的“设置权限页”
    public static  void startAppSettings(Context activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }


    /**
     * 回调例子
     */
    public static void onRequestPermissionsResult(Context context, int requestCode, @NonNull int[] grantResults, int myRstCode, String permission) {
        if (requestCode == myRstCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限申请已经通过
            } else {
                //用户勾选了不再询问,下面这个方法返回false。
                //提示用户手动打开权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                    // >=23 生效，<23 永远返回false
                    PermissionControl.startAppSettings(context);
                    Toast.makeText(context, "您已拒绝该权限，请前往权限管理中开启", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
