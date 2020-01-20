package com.sign.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP存储的工具类！
 */

public class SPUtils {

    public static final String IS_NEW_INVITE = "is_new_invite";//新的邀请标记
    private static SPUtils instace = new SPUtils();
    private static SharedPreferences mSp = null;
    private static String APP_ID = "com.xxxx";

    public static SPUtils getInstance(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        }
        return instace;
    }

    // 保存
    public void save(String key, Object value) {
        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            mSp.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            mSp.edit().putLong(key, (Long) value).commit();
        }
    }

    // 读取
    // 读取String类型数据
    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    // 读取boolean类型数据
    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    // 读取int类型数据
    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    // 读取int类型数据
    public long getLong(String key, long defValue) {
        return mSp.getLong(key, defValue);
    }
    //取出List集合
    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(APP_ID,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
