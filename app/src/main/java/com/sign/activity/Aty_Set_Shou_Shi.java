package com.sign.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sign.R;
import com.sign.base.BaseActivity;
import com.sign.com.Constants;
import com.sign.util.SPUtils;
import com.sign.view.LockView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Aty_Set_Shou_Shi extends BaseActivity {
    @Bind(R.id.lv_lock)
    LockView lvLock;
    @Bind(R.id.btn_cancle)
    Button btnCancle;
    @Bind(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_set_shou_shi);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btnOk.setEnabled(false);
        btnOk.setTextColor(Color.GRAY);
        btnCancle.setEnabled(false);
        btnCancle.setTextColor(Color.GRAY);
        lvLock.setOnDrawFinish(new LockView.OnDrawFinish() {
            @Override
            public void oneDraw(List<Integer> passwords) {
                btnOk.setEnabled(true);
                btnOk.setText("继续");
                btnOk.setTextColor(Color.BLACK);
                btnCancle.setText("重绘");
                btnCancle.setEnabled(true);
                btnOk.setTextColor(Color.BLACK);
            }

            @Override
            public void twoDraw(boolean isOk) {
                if (isOk) {
                    btnOk.setEnabled(true);
                    btnOk.setText("确定");
                    btnOk.setTextColor(Color.BLACK);
                    btnCancle.setText("重绘");
                    btnCancle.setEnabled(true);
                    btnOk.setTextColor(Color.BLACK);
                } else {
                    btnOk.setText("两次绘制不一致");
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnOk.getText().equals("确定")) {
                    Toast.makeText(Aty_Set_Shou_Shi.this, "设置密码成功！", Toast.LENGTH_SHORT).show();
                    lvLock.cancel();
                    finish();
                } else {
                    lvLock.drawTwo();
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance(Aty_Set_Shou_Shi.this).save(Constants.sp_key_shou_shi, "");
                lvLock.cancel();
                btnOk.setEnabled(false);
                btnOk.setText("继续");
                btnOk.setTextColor(Color.GRAY);
                btnCancle.setEnabled(false);
                btnOk.setTextColor(Color.GRAY);
            }
        });
    }
}
