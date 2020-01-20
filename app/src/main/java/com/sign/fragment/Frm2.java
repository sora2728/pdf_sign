package com.sign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sign.R;
import com.sign.base.BaseFragmentVP;
import com.sign.util.UIUtils;

import butterknife.ButterKnife;

/**
 * 测试！
 */
public class Frm2 extends BaseFragmentVP {
    /**
     * 这里定义一个TextView组件，可以替换为任意的布局View！
     */
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = UIUtils.getXmlView(mContext, R.layout.fragment_2);
        ButterKnife.bind(this, view);
//        tvTitle.setText("我");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    @Override
    protected void onFragmentFirstVisible() {
    }
}
