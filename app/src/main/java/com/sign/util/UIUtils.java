package com.sign.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sign.com.MyApplication;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import static com.sign.com.MyApplication.context;


/**
 * UI工具类
 */

public class UIUtils {

    public static int getColor(int colorId) {
        return context.getResources().getColor(colorId);
    }

    public static View getXmlView(Context mContext, int viewId) {
        return View.inflate(mContext, viewId, null);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 快速得到资源文件
     *
     * @param arrId
     * @return
     */
    public static String[] getStringArr(int arrId) {
        return context.getResources().getStringArray(arrId);
    }

    /**
     * 1dp---1px;
     * 1dp---0.75px;
     * 1dp---0.5px;
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);//四舍五入
    }

    public static int px2dp(int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变:
     * 实现方式：
     * textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//字号
     */


    public static Handler getHandler() {
        return MyApplication.handler;
    }

    /**
     * 保证runnable对象的run方法是运行在主线程当中，执行运行的方法在主线程中！
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    /**
     * 判断当前线程是否是主线程！
     *
     * @return
     */
    private static boolean isInMainThread() {
        //当前线程的id
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId) {
            return true;
        }
        return false;
    }

    /**
     * Toast的快捷方式！
     *
     * @param text
     * @param isLong
     */
    public static void Toast(Context context, String text, boolean isLong) {
        Toast.makeText(context, text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取版本号！
     *
     * @param context
     * @return
     */
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取版本号！
     *
     * @param context
     * @return
     */
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String codeName = "";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            codeName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return codeName;
    }

    /**
     * 强制隐藏软键盘
     *
     * @param activity
     */
    public static void hide_keyboard(Activity activity) {
        // 强制隐藏软键盘
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 强制显示软键盘
     *
     * @param activity
     */
    public static void show_keyboard(Activity activity) {
        // 强制隐藏软键盘
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 判断是否隐藏软件欧盘
     */
    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom;
    }

    //
    public static void show_Shake(final long milliseconds1) {
        //震动160毫秒
        new Thread() {
            public void run() {
                if (true)
                    MyApplication.vibrator.vibrate(milliseconds1);
            }
        }.start();
    }

    public static SpannableStringBuilder setNumColor(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9' || a == '.') {
                style.setSpan(new ForegroundColorSpan(Color.RED), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }

    public static SpannableStringBuilder setNumColorOrange(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9' || a == '.') {
                style.setSpan(new ForegroundColorSpan(Color.parseColor("#ffcc33")), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }

    /**
     * 判断进程是否存活
     *
     * @param context
     * @param proessName
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    //提示音
    public static void startAlarm(Context context) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (notification == null) return;
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

    /**
     * 作者：guoyzh
     * 时间：2018年6月14日
     * 功能：判断是否是进行了快速点击的操作
     */

    private static long lastClickTime = 0;//上次点击的时间
    private static int spaceTime = 500;//时间间隔

    public static boolean isFastClick() {

        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击

        if (currentTime - lastClickTime > spaceTime) {

            isAllowClick = false;
        } else {
            isAllowClick = true;
        }
        lastClickTime = currentTime;
        return isAllowClick;
    }

    public static boolean pan_duan_response(String response, Context context) {
        if (response.equals("")) {
            Toast.makeText(context, "返回数据为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
        String rtncode = jsonObject.get("code").getAsString();
        if (rtncode.equals("500")) {
            String msg = jsonObject.get("msg").getAsString();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (rtncode.equals("200")) {
            return true;
        }
        return false;
    }

    /**
     * 联网失败！
     *
     * @param s
     */
    public static void call_net_error(Context context, String s) {
        ProgressDialogUtils.dismissProgressDialog();
        LogUtils.e("call_net_error", "错误路径返回数据： " + s);
        Activity context1 = (Activity) context;
//        if (context1 instanceof ActivitySplash) {//如果是启动页的提醒token失败，就不需要提醒了！
//            return;
//        }
        if (s.contains("SocketTimeoutException")) {
            ToastUtil.show(context, "无响应，请检查网络连接是否正常");
        } else if (s.contains("500")) {
            ToastUtil.show(context, "操作失败，错误代码：500");
        } else if (s.contains("666")) {
            onConnectionConflict((Activity) context);
        } else if (s.contains("ConnectException")) {
            ToastUtil.show(context, "连接服务器失败");
        } else {
            ToastUtil.show(context, "请求服务器失败:" + s);
        }
    }

    /**
     * 实现主线程提醒：异常登录，请重新登录！
     */
    private static void onConnectionConflict(final Activity taskTop) {//被踢下线处理
//        final Activity taskTop = MyApplication.getActivity();
//        if (taskTop == null) {
//            return;
//        }
//        if (taskTop.isFinishing()) {
//            return;
//        }
        taskTop.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleAlertDialog2Utils.getInstance().createAlertDialog(taskTop, "提示", "您的账号出现访问异常，请重新登录", new SimpleAlertDialog2Utils.OnMyDialogClickListner() {
                    @Override
                    public void setPositive() {
//                        MyApplication.clearCache();
//                        Intent intent = new Intent(taskTop, ActivityLogin.class);
//                        taskTop.startActivity(intent);
                    }

                    @Override
                    public void setNegative() {
//                        MyApplication.clearCache();
//                        Intent intent = new Intent(taskTop, ActivityLogin.class);
//                        taskTop.startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu(Activity mContext) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = mContext.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = mContext.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 获取手机的系统信息！
     * 设备厂商 -- 型号
     *
     * @return
     */
    public static String getSystemInfo() {
        return (Build.MANUFACTURER + Build.MODEL).toLowerCase();// 设备型号
    }
}
