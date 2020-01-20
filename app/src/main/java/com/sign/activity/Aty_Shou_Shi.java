package com.sign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.R;
import com.sign.base.BaseActivity;
import com.sign.com.Constants;
import com.sign.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Aty_Shou_Shi extends BaseActivity {

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
    @Bind(R.id.activity_zhe_xian)
    LinearLayout activityZheXian;
    @Bind(R.id.btn_shou_shi)
    Button btnShouShi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shou_shi);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("测试手势实现");
        btnShouShi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtils.getInstance(Aty_Shou_Shi.this).getString(Constants.sp_key_shou_shi, "").equals("")) {
                    Toast.makeText(Aty_Shou_Shi.this, "进入输入密码界面！", Toast.LENGTH_SHORT).show();
                    //进入设置密码界面！
                    startActivity(new Intent(Aty_Shou_Shi.this,Aty_Set_Shou_Shi.class));
                } else {
                    Toast.makeText(Aty_Shou_Shi.this, "进入验证密码界面！", Toast.LENGTH_SHORT).show();
                    //进入输入密码界面！
                    startActivity(new Intent(Aty_Shou_Shi.this,Aty_Verify_Shou_Shi.class));
                }
            }
        });
    }
}
