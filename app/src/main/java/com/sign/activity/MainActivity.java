package com.sign.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.sign.R;
import com.sign.base.BaseActivity;
import com.sign.com.Constants;
import com.sign.com.EventMsgString;
import com.sign.com.FragmentTabHost;
import com.sign.com.TabContent;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主Activity
 */

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener, View.OnClickListener {
    @Bind(R.id.iv_camara)
    ImageView ivCamara;
    private FragmentTabHost tabHost;
    private RelativeLayout ll_00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tabHost = (FragmentTabHost) super.findViewById(android.R.id.tabhost);
        //1、初始化！
        tabHost.setup(this, super.getSupportFragmentManager(), R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        //TabHost切换监听！
        tabHost.setOnTabChangedListener(this);
        initTab();
        initClick();
    }

    private void initClick() {
        ivCamara.setOnClickListener(this);
    }

    /**
     * 新建TabSpec  !
     */
    private void initTab() {
        String tabs[] = TabContent.getTabsTxt();
        for (int i = 0; i < tabs.length; i++) {
            //选择显示文字和显示的View！
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec, TabContent.getFragments()[i], null);
            tabHost.setTag(i);
        }
        //----------这部分代码必须得有，假如想进入界面默认显示别的选项卡-------如果是默认第一个可以去掉这些代码--------------------------------------------
        // 设置mCurrentTab为非-1,addtab时候不会进入setCurrentTab()
        try {
            Field idcurrent = tabHost.getClass().getDeclaredField("mCurrentTab");
            idcurrent.setAccessible(true);
            idcurrent.setInt(tabHost, -2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置mCurrentTab与tadid不同，并且不能数组越界(0-2)，保证第一次进入tab的setCurrentTab()方法正常运行
        try {
            Field idcurrent = tabHost.getClass().getDeclaredField("mCurrentTab");
            idcurrent.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabHost.setCurrentTab(0);
        updateTab();
    }

    private View getTabView(int idx) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        ((TextView) view.findViewById(R.id.tvTab)).setText(TabContent.getTabsTxt()[idx]);
        if (idx == 1) {
//            tab_unread_count = view.findViewById(R.id.tab_unread_count);
            ll_00 = view.findViewById(R.id.ll_00);
        }
        if (idx == 0) {
            ((TextView) view.findViewById(R.id.tvTab)).setTextColor(Color.RED);
            ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(TabContent.getTabsImgLight()[idx]);

        } else {
            ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(TabContent.getTabsImg()[idx]);
        }
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        updateTab();
    }

    /**
     * 切换显示的标签:
     */
    private void updateTab() {
        TabWidget tabw = tabHost.getTabWidget();
        for (int i = 0; i < tabw.getChildCount(); i++) {
            View view = tabw.getChildAt(i);
            ImageView iv = (ImageView) view.findViewById(R.id.ivImg);
            TextView tv = (TextView) view.findViewById(R.id.tvTab);
            if (i == tabHost.getCurrentTab()) {
                tv.setTextColor(getResources().getColor(R.color.green));
                iv.setImageResource(TabContent.getTabsImgLight()[i]);
            } else {
                tv.setTextColor(getResources().getColor(R.color.foot_txt_gray));
                iv.setImageResource(TabContent.getTabsImg()[i]);
            }
        }
    }

    //**************************************************************
    private long firstTime; //第一次按下back键的时间

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - firstTime) > 2000) {
                //提示
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                //记录时间
                firstTime = System.currentTimeMillis();
                return true;
            } else {
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 得到的是扫描后返回的扫描结果！
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            byte[] result = data.getByteArrayExtra(CaptureActivity.KEY_RESULT);
            if (result == null || result.length == 0) return;
            String payCode = new String(result);
            EventBus.getDefault().post(new EventMsgString(Constants.msg_result_scan, payCode));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_camara:
                Toast.makeText(MainActivity.this, "照相！", Toast.LENGTH_SHORT).show();
                break;
        }
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
                .permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
//                .permission(Permission.CAMERA)
//                .permission(Permission.RECORD_AUDIO)
//                .permission(Permission.CALL_PHONE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                        } else {
                            Toast.makeText(MainActivity.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(MainActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                            //如果是被永久拒绝就跳转到应用权限系统设置页面!
                            XXPermissions.gotoPermissionSettings(MainActivity.this);
                        } else {
                            Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}