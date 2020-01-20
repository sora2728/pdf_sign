package com.sign._ding_dan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sign.R;
import com.sign.base.BaseFragmentVP;
import com.sign.util.LogUtils;
import com.sign.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页显示的数据列表！
 */

public class Frm0_item extends BaseFragmentVP implements View.OnClickListener {
    @Bind(R.id.rv)
    XRecyclerView rv;
    private String type_date;
    private View view;
    private Adapter_frm0_item adapter_frm0_item;
    private int currentPage = 1;

    public static Frm0_item newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        Frm0_item fragment = new Frm0_item();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type_date = bundle.get("name").toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = UIUtils.getXmlView(mContext, R.layout.frm0_item);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            LogUtils.e("type_date = " + type_date);
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        initRecyclerView();
        init_refresh_date();
        set_refresh();
    }


    private void initRecyclerView() {
        List<String> listDate = new ArrayList<>();
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        listDate.add("11111");
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter_frm0_item = new Adapter_frm0_item(mContext, R.layout.item_frm0_rv, listDate);
        rv.setAdapter(adapter_frm0_item);
//        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
//        rv.setNoMore(true);
//        rv.setLoadingMoreEnabled(true);
    }

    /**
     * 初始化刷新操作！
     */

    private void set_refresh() {
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                init_refresh_date();
                rv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                currentPage = currentPage + 1;
                init_refresh_date();
                rv.loadMoreComplete();
            }
        });
    }

    private void init_refresh_date() {

    }
}
