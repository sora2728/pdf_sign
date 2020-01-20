package com.sign.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串的判断类！
 */

public class StringUtil {
    /**
     * 判断字符串是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断字符串是否包含字母
     *
     * @param str
     * @return
     */
    public static boolean isContainLetters(String str) {
        for (char i = 'A'; i <= 'Z'; i++) {
            if (str.contains(String.valueOf(i))) {
                return true;
            }
        }
        for (char i = 'a'; i <= 'z'; i++) {
            if (str.contains(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }
}
