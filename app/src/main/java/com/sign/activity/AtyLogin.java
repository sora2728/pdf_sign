package com.sign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.sign.base.BaseActivity;
import com.sign.com.Constants;
import com.sign.util.SPUtils;
import com.sign.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录界面!!
 */
public class AtyLogin extends BaseActivity implements View.OnClickListener {
    private static final java.lang.String TAG = "login";
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.iv_del_phone)
    ImageView ivDelPhone;
    @Bind(R.id.et_key)
    EditText etKey;
    @Bind(R.id.iv_del_pwd)
    ImageView ivDelPwd;
    @Bind(R.id.btn_forget_pwd)
    TextView btnForgetPwd;
    @Bind(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        ButterKnife.bind(this);
        initView();
        initClick();
        call_net_login();
    }

    /**
     * 初始化点击事件
     */
    private void initClick() {
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                SPUtils.getInstance(AtyLogin.this).save(Constants.sp_id_User, etPhone.getText().toString().trim());
                // 进入主界面
                startActivity(new Intent(AtyLogin.this, MainActivity.class));
                // 同时关闭登陆界面
                finish();
                break;
        }
    }

    private void initView() {

    }

    /**
     * 执行登录的请求操作！
     */
    private void call_net_login() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        exe_request_permission();
    }

    /**
     * 请求权限！！
     */
    private void exe_request_permission() {
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                .permission(Permission.Group.STORAGE, Permission.Group.LOCATION) //不指定权限则自动获取清单中的危险权限
                .permission(Permission.CAMERA)
                .permission(Permission.RECORD_AUDIO)
                .permission(Permission.CALL_PHONE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                        } else {
                            Toast.makeText(AtyLogin.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(AtyLogin.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                            //如果是被永久拒绝就跳转到应用权限系统设置页面!
                            XXPermissions.gotoPermissionSettings(AtyLogin.this);
                        } else {
                            Toast.makeText(AtyLogin.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}