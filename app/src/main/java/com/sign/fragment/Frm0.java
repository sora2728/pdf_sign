package com.sign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.R;
import com.sign.activity.Aty_Shou_Shi;
import com.sign.activity.Aty_Tu_Biao;
import com.sign.activity.aty_pdf.AtyGetPdf;
import com.sign.base.BaseFragmentVP;
import com.sign.com.EventMsgString;
import com.sign.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Frm0 extends BaseFragmentVP implements View.OnClickListener {
    @Bind(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.ll_title)
    RelativeLayout llTitle;
    @Bind(R.id.btn_pdf)
    Button btnPdf;
    @Bind(R.id.btn_tu_biao)
    Button btnTuBiao;
    @Bind(R.id.btn_shou_shi)
    Button btnShouShi;
    /**
     * 这里定义一个TextView组件，可以替换为任意的布局View！
     */
    private View view;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMsgString event) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = UIUtils.getXmlView(mContext, R.layout.fragment_0);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        tvTitle.setText("主页");
        initView();
        initClick();
        return view;
    }

    private void initView() {
        ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageResource(R.mipmap.icon_search);
    }

    private void initClick() {
        ivTitleRight.setOnClickListener(this);
        btnPdf.setOnClickListener(this);
        btnTuBiao.setOnClickListener(this);
        btnShouShi.setOnClickListener(this);
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
    protected void onFragmentFirstVisible() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_right:
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_pdf:
                startActivity(new Intent(mContext, AtyGetPdf.class));
                break;
            case R.id.btn_tu_biao:
                startActivity(new Intent(mContext, Aty_Tu_Biao.class));
                break;
            case R.id.btn_shou_shi:
                startActivity(new Intent(mContext, Aty_Shou_Shi.class));
                break;
        }
    }
}