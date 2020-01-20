package com.sign.com;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 全局初始化类！
 */

public class MyApplication extends Application {
    //定义全局使用的变量
    public static Handler handler;
    public static Thread mainThread;
    public static int mainThreadId;
    public static Context context;
    private static Activity sActivity;
    private static MyApplication mApp;
    //震动初始化！
    public static Vibrator vibrator;
    private static MyApplication mInstance;
    public static Context mContext;
//    public static BeanMsgDao beanMsgDao;
    public static int screenWidth;//屏幕宽度！
    public static int screenHeight;//屏幕高度！
    public static float screenDensity;//屏幕密度！

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();//主线程
        mainThreadId = android.os.Process.myTid();//当前线程的
        //震动初始化！
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //初始化未捕获异常处理方式的类的初始化---- 这一行代码搞定开发bug收集
//        CrashHandler.getInstance().init();
        //获取到顶层栈顶的Activity!
        init_stact_listener();
        closeAndroidPDialog();
        initScreenSize();
        mInstance = this;
    }



    /**
     * 初始化当前设备屏幕宽高!
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    public static Activity getActivity() {
        return sActivity;
    }

    private void init_stact_listener() {
        mApp = this;
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                LogUtils.e("YWK", activity + "onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
//                LogUtils.e("YWK", activity + "onActivityStarted");
                sActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    /**
     * 适配android9.0的弹出错误窗口的bug
     */
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
