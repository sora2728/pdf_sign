package com.sign.com;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import static com.sign.com.MyApplication.context;

/**
 * 程序中的未捕获的全局异常的捕获（单例），独自在分线程执行，所以使用单例！
 * 这里使用的是懒汉式(不用考虑同步)！
 * 此类就是一个单独的线程运行的！
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    private CrashHandler() {
    }

    private static CrashHandler crashHandler = null;

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    /**
     * 初始化做了两件事，一件事：获取到系统处理异常的类！
     * 一件事是将当前类设置为处理异常的默认处理！
     */
    public void init() {
        //获取系统默认的处理未捕获的异常的实现类！
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前自定义的UncaughtExceptionHandler的实现类作为出现未捕获异常时的处理类！
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当程序执行时，出现未捕获的异常时，自动执行如下的代码
     *
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (throwable == null) {
            defaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        } else {
            Log.e("TAG", "CrashHandler抓到异常 --- 出现异常！！");
            handleException(throwable);
        }
    }

    /**
     * 出现异常的处理方式！
     *
     * @param throwable
     */
    private void handleException(Throwable throwable) {
        new Thread() {
            public void run() {
                Looper.prepare();
                //执行在主线程的代码
                Toast.makeText(context, "不好意思，亲，出现了一个小异常！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //收集用户的异常信息，并发送给服务器
        collectionException(throwable);
        //执行后续的关闭资源的一系列操作
        try {
            Thread.sleep(2000);
            //将所有的Activity移除
            AppManager.getInstance().removeAll();
            //关闭当前的进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //关闭虚拟机
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收集异常信息，并且发送给服务器！
     */
    private void collectionException(Throwable throwable) {
        //获取异常的相关信息
        final String msg_err = throwable.getMessage();
        //获取手机的信息
        final String productInfo = Build.DEVICE + "---" + Build.PRODUCT + "---" + Build.MODEL + "---" + Build.VERSION.SDK_INT;

        //联网将异常信息发送给服务器端！--需要服务器端开一个接口专门处理这些信息！
        new Thread() {
            public void run() {
                Log.e("TAG", "productInfo " + productInfo + " --  msg_err=" + msg_err);
            }
        }.start();
    }
}
