package com.sign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sign.R;
import com.sign.base.BaseFragmentVP;
import com.sign.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Frm3 extends BaseFragmentVP {
    @Bind(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;
    /**
     * 这里定义一个TextView组件，可以替换为任意的布局View！
     */
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = UIUtils.getXmlView(mContext, R.layout.fragment_3);
        ButterKnife.bind(this, view);
        tvTitle.setText("标题");
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
