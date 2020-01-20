package com.sign.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sign.com.AppManager;
import com.sign.com.Constant_EventMsg;
import com.sign.com.EventMsgString;
import com.sign.com.StatusBar;
import com.sign.util.LogUtils;
import com.sign.util.UIUtils;
import com.sign.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 基本BaseActivity
 */

public class BaseActivity extends FragmentActivity {
    //接收消息：一旦是发送方发送了消息，接收方就可以得到消息！接受的前提加一个注解!
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMsgString event) {
        if (event.sign == Constant_EventMsg.Token_Error) {
            LogUtils.e("EventBus", "EventBus__Token_Error消息收到");
//            SimpleAlertDialogUtils.getInstance().createAlertDialog(this, "提示", "遇到一个错误，您的账号可能在别处登录。", new SimpleAlertDialogUtils.OnMyDialogClickListner() {
//                @Override
//                public void setPositive() {
//                    finish();
//                    //执行后续的关闭资源的一系列操作
//                    //将所有的Activity移除
//                    AppManager.getInstance().removeAll();
//                    //关闭当前的进程
//                    android.os.Process.killProcess(android.os.Process.myPid());
//                    //关闭虚拟机
//                    System.exit(0);
//                }
//            });
        }
    }

    protected LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        // 状态栏透明
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 虚拟导航栏透明
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setStatusBar(true);
        StatusBar.transportStatus(this);
        setAndroidNativeLightStatusBar(this,true);
        EventBus.getDefault().register(this);
        AppManager.getInstance().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppManager.getInstance().remove(this);
    }

    /**
     * 设置透明状态栏与导航栏
     *
     * @param navi true不设置导航栏|false设置导航栏
     */
    public void setStatusBar(boolean navi) {
        //api>21,全透明状态栏和导航栏;api>19,半透明状态栏和导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            if (navi) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏不会被隐藏但activity布局会扩展到状态栏所在位置
////                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//导航栏不会被隐藏但activity布局会扩展到导航栏所在位置 --- 加上会导致底部栏出现app的操作！！！
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.setNavigationBarColor(getResources().getColor(R.color.white));//设置底部操作栏的颜色！
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (navi) {
                //半透明导航栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            //半透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 判断连续点击事件时间差
            if (UIUtils.isFastClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
