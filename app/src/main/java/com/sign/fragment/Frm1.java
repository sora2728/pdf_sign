package com.sign.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sign.R;
import com.sign._ding_dan.adapter.Adapter_frm0_item;
import com.sign.base.BaseFragmentVP;
import com.sign.com.EventMsgString;
import com.sign.util.LogUtils;
import com.sign.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Frm1 extends BaseFragmentVP {
    @Bind(R.id.rv)
    XRecyclerView rv;
    /**
     * 这里定义一个TextView组件，可以替换为任意的布局View！
     */
    private View view;
    private Adapter_frm0_item adapter;

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~在这里显示接收的数据~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~在这里显示接收的数据~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~在这里显示接收的数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMsgString event) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = UIUtils.getXmlView(mContext, R.layout.fragment_1);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initRV();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("Frm1  -----  每次都执行显示操作！！");
    }

    @Override
    protected void onFragmentFirstVisible() {
    }

    /**
     * 消息列表初始化！
     */
    private void initRV() {
        //创建数据对象！但是先不提供数据！
        List<String> list_date = new ArrayList<>();
        list_date.add("11111111");
        list_date.add("11111111");
        list_date.add("11111111");
        list_date.add("11111111");
        list_date.add("11111111");
        list_date.add("11111111");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new Adapter_frm0_item(mContext, R.layout.item_frm0_rv, list_date);
        rv.setAdapter(adapter);
        rv.setItemAnimator(null);//设置后上拉刷新不会有闪屏界面出现
    }
}
