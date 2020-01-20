package com.sign.activity.aty_pdf;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sign.R;
import com.sign.activity.AtyCompany;
import com.sign.base.BaseActivity;
import com.sign.util.LogUtils;
import com.sign.util.SimpleAlertDialogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.sign.com.Constants.FILE_SELECTOR_CODE;
import static com.sign.com.MyApplication.mContext;

public class AtyGetPdf extends BaseActivity implements View.OnClickListener {

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
    @Bind(R.id.rv)
    XRecyclerView rv;
    @Bind(R.id.iv_get_pdf)
    ImageView ivGetPdf;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    private Adapter_pdf_item adapter_pdf_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_get_pdf);
        ButterKnife.bind(this);
        initView();
        initClick();
    }

    private void initView() {
        tvTitle.setText("pdf文件");
        tvTitleRight.setText("选择");
        tvTitleRight.setTextColor(Color.WHITE);
        tvTitleRight.setVisibility(View.VISIBLE);
        initRecyclerView();
//        init_refresh_date();
        set_refresh();
        rv.setLoadingMoreEnabled(false);
    }

    /**
     * 初始化刷新操作！
     */
    private void set_refresh() {
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                btnGetPdf();
                rv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    private void initRecyclerView() {
        List<String> listDate = new ArrayList<>();
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter_pdf_item = new Adapter_pdf_item(this, R.layout.item_pdf_rv, listDate);
        rv.setAdapter(adapter_pdf_item);
    }

    private void initClick() {
        ivGetPdf.setOnClickListener(this);
        tvCompany.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_get_pdf:
                Toast.makeText(AtyGetPdf.this, "获取pdf文件", Toast.LENGTH_SHORT).show();
                btnGetPdf();
                break;
            case R.id.tv_company:
                startActivity(new Intent(AtyGetPdf.this, AtyCompany.class));
                break;
            case R.id.tv_title_right:
                openFileSelector();
                break;
        }
    }

    /**
     * 打开本地文件器
     */
    private void openFileSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_SELECTOR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECTOR_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String path = uri.getPath().toString();
            if (path.endsWith("pdf")) {
                Intent intent = new Intent(AtyGetPdf.this, Aty_Pdf.class);
                String realFilePath = getRealFilePath(AtyGetPdf.this, uri);
                intent.putExtra("pdf_address", realFilePath);
                AtyGetPdf.this.startActivity(intent);
            } else {
                SimpleAlertDialogUtils.getInstance().createAlertDialog(AtyGetPdf.this, "温馨提示", "您选择得文件不是pdf", new SimpleAlertDialogUtils.OnMyDialogClickListner() {
                    @Override
                    public void setPositive() {
                    }
                });
            }
        }
    }

    /**
     * 先实现打开本地文件中的pdf文件！
     */
    public void btnGetPdf() {
        open_loc_pdf_file();
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
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                        } else {
                            Toast.makeText(AtyGetPdf.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(AtyGetPdf.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                            //如果是被永久拒绝就跳转到应用权限系统设置页面!
                            XXPermissions.gotoPermissionSettings(AtyGetPdf.this);
                        } else {
                            Toast.makeText(AtyGetPdf.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void open_loc_pdf_file() {
        LogUtils.e("1111111111111111111111111111111111111111");
        AbstructProvider provider = new FileProvider(this);
        List<File> list = provider.getList("%.pdf");
        List<String> listDate = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String absolutePath = list.get(i).getAbsolutePath();
                LogUtils.e(absolutePath);
                listDate.add(absolutePath);
            }
            ivGetPdf.setVisibility(View.GONE);
        } else {
//            Toast.makeText(AtyGetPdf.this, "本地没有pdf文件！", Toast.LENGTH_SHORT).show();
            SimpleAlertDialogUtils.getInstance().createAlertDialog(AtyGetPdf.this, "温馨提示", "本地没有发现pdf文件", new SimpleAlertDialogUtils.OnMyDialogClickListner() {
                @Override
                public void setPositive() {

                }
            });
        }
        adapter_pdf_item.refresh(listDate);
    }

    /**
     * 获取真实路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
