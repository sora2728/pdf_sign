package com.sign._ding_dan;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class Adapter_frm0_item extends CommonAdapter<String> {
    private List<String> mList;

    public Adapter_frm0_item(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        mList = datas;
    }

    //下面两个方法提供给页面刷新和加载时调用
    // 刷新数据的方法
    public void refresh(List<String> listRefresh) {
        if (listRefresh != null && listRefresh.size() >= 0) {
            mList.removeAll(mList);
            mList.addAll(listRefresh);
            notifyDataSetChanged();
        }
    }

    public void add(List<String> addMessageList) {
        //增加数据
        int position = mList.size();
        mList.addAll(position, addMessageList);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {

    }
}
