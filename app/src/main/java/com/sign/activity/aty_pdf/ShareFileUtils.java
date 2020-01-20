package com.sign.activity.aty_pdf;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * 分享文件、图片、文本
 * Created by 她叫我小渝 on 2016/10/15.
 */

public class ShareFileUtils {

    /**
     * 分享文本
     *
     * @param context
     * @param path
     */
    public static void shareUrl(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        checkFileUriExposure();

        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, path);
        it.setType("text/plain");
        context.startActivity(Intent.createChooser(it, "分享APP"));
    }

    /**
     * 分享文件
     *
     * @param context
     * @param path
     */
    public static void shareFile(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        checkFileUriExposure();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));  //传输图片或者文件 采用流的方式
        intent.setType("*/*");   //分享文件
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    /**
     * 检测手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true-安装，false-未安装
     */
    public static boolean isAppInstall(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 分享前必须执行本代码，主要用于兼容SDK18以上的系统
     */
    private static void checkFileUriExposure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }
}