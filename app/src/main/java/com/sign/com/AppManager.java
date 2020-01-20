package com.sign.com;

import android.app.Activity;

import java.util.Stack;

/**
 * 提供页面中显示的Activity的管理：AppManager （设置为单例模式）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 */
public class AppManager {
    //单例模式的体现：
    private AppManager() {
    }

    private static AppManager instance = new AppManager();

    public static AppManager getInstance() {
        return instance;
    }

    private Stack<Activity> activityStack = new Stack<>();

    /**
     * activity的添加
     */
    public void add(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 删除指定的activity
     */
    public void remove(Activity activity) {
        if (activity != null) {
            for (int i = activityStack.size() - 1; i >= 0; i--) {
                //判断两个activity所属的类是否一致
                if (activity.getClass() == activityStack.get(i).getClass()) {
                    //销毁当前的activity
                    activityStack.get(i).finish();
                    //从栈空间移除
                    activityStack.remove(i);
                }

            }

        }
    }

    /**
     * 删除当前的activity
     */
    public void removeCurrent() {
        activityStack.lastElement().finish();
//        activityStack.remove(activityStack.size() - 1);
        activityStack.remove(activityStack.lastElement());
    }

    /**
     * 删除所有的activity
     */
    public void removeAll() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            //销毁当前的activity
            activityStack.get(i).finish();
            //从栈空间移除
            activityStack.remove(i);
        }
    }

    /**
     * 返回activity栈的大小
     */
    public int getSize() {
        return activityStack.size();
    }

}
