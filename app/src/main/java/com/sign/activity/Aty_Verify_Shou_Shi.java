package com.sign.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.R;
import com.sign.base.BaseActivity;
import com.sign.com.Constants;
import com.sign.util.LogUtils;
import com.sign.util.SPUtils;
import com.sign.view.LockView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Aty_Verify_Shou_Shi extends BaseActivity {

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
    @Bind(R.id.lv_lock)
    LockView lvLock;
    @Bind(R.id.activity_zhe_xian)
    RelativeLayout activityZheXian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_verify_shou_shi);
        ButterKnife.bind(this);
        initView();
        init_verify();
    }

    private void initView() {
        tvTitle.setText("验证手势");
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init_verify() {
        lvLock.setOnDrawFinish(new LockView.OnDrawFinish() {
            @Override
            public void oneDraw(List<Integer> passwords) {
                LogUtils.e("密码输入是：" + passwords.toString());
                String sp_pwd = SPUtils.getInstance(Aty_Verify_Shou_Shi.this).getString(Constants.sp_key_shou_shi, "");
                LogUtils.e("存储的密码：" + sp_pwd);
                if (sp_pwd.equals(passwords.toString())) {
                    Toast.makeText(Aty_Verify_Shou_Shi.this, "验证正确，跳转下一级", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Aty_Verify_Shou_Shi.this, "密码失败！", Toast.LENGTH_SHORT).show();
                    lvLock.cancel();
                }
            }

            @Override
            public void twoDraw(boolean isOk) {
            }
        });
    }
}
