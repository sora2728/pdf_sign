package com.sign.com;

import com.sign.R;
import com.sign.fragment.Frm0;
import com.sign.fragment.Frm2;
import com.sign.fragment.Frm3;

/**
 * Tab的资源！
 */
public class TabContent {
    //显示多少个item就修改这里就可以！
    public static String[] getTabsTxt() {
        String[] tabs = {"主页", "圈子", "我"};
        return tabs;
    }

    public static int[] getTabsImg() {
        int[] ids = {R.mipmap.icon_0, R.mipmap.icon_1, R.mipmap.icon_2};
        return ids;
    }

    public static int[] getTabsImgLight() {
        int[] ids = {R.mipmap.icon_0_press, R.mipmap.icon_1_press, R.mipmap.icon_2_press};
        return ids;
    }

    public static Class[] getFragments() {
        Class[] clz = {Frm0.class, Frm3.class, Frm2.class, Frm3.class};
        return clz;
    }
}
