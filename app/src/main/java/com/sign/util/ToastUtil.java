package com.sign.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast的封装操作：主要是封装Toast的显示，并且还可以自定义一些效果！这里暂时使用的是系统给出的！
 */
public class ToastUtil {

    public static void show(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int contentId) {
        Toast.makeText(context, contentId, Toast.LENGTH_SHORT).show();
    }
}