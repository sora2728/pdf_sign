package com.sign.activity.aty_pdf;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artifex.mupdf.viewer.MuPDFCore;
import com.artifex.mupdf.viewer.PageAdapter;
import com.sign.R;
import com.sign.base.BaseActivity;
import com.sign.com.EventMsgString;
import com.sign.pdf.ComposeSignatureToPdf;
import com.sign.pdf.FileUtils;
import com.sign.pdf.PdfReaderView;
import com.sign.pdf.PermissionControl;
import com.sign.pdf.SignatureView;
import com.sign.util.LogUtils;
import com.sign.util.SPUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.sign.R.id.btn_sign;

public class Aty_Pdf extends BaseActivity implements View.OnClickListener {

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
    @Bind(R.id.readerView)
    PdfReaderView readerView;
    @Bind(R.id.signatureView)
    SignatureView signatureView;
    @Bind(btn_sign)
    ImageView btnSign;
    @Bind(R.id.rl_sign)
    RelativeLayout rlSign;
    @Bind(R.id.btn_back)
    ImageView btnBack;
    @Bind(R.id.rl_back)
    RelativeLayout rlBack;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.rl_clear)
    RelativeLayout rlClear;
    @Bind(R.id.imageView4)
    ImageView imageView4;
    @Bind(R.id.rl_compose)
    RelativeLayout rlCompose;
    @Bind(R.id.rl_top)
    LinearLayout rlTop;
    @Bind(R.id.rl_parent)
    LinearLayout rlParent;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMsgString event) {
        try {
            muPDFCore = new MuPDFCore(nextPdf);//显示合成后的PDF
            readerView.setAdapter(new PageAdapter(Aty_Pdf.this, muPDFCore));
            readerView.setmScale(1.0f);
            readerView.setDisplayedViewIndex(readerView.getDisplayedViewIndex());
            progressDialog.dismiss();
            LogUtils.e("-----------合成后的显示nextPdf --- " + nextPdf);
        } catch (Exception e) {
            LogUtils.e("合成后的显示nextPdf显示错误 ---- " + e);
            e.printStackTrace();
        }
    }

    private String current_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pdf);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        current_pdf = intent.getStringExtra("pdf_address");
        initView();
        initClick();
    }

    private void initClick() {
        rlSign.setOnClickListener(this);
        rlCompose.setOnClickListener(this);
        rlClear.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    private void initView() {
        tvTitle.setText("pdf阅读签名");
        llTitle.setBackgroundColor(getResources().getColor(R.color.color_gray));
        tvTitle.setTextColor(Color.WHITE);
        tvTitleRight.setText("发送");
        tvTitleRight.setTextColor(Color.WHITE);
        tvTitleRight.setVisibility(View.VISIBLE);
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (PermissionControl.check(this, WRITE_EXTERNAL_STORAGE)) {
            FileUtils.putAssetsToSDCard(this, "pdf_file", Environment.getExternalStorageDirectory().getPath());
            initPdfReadView();
        } else {
            PermissionControl.reqstPermission(this, "读取SD卡权限申请",
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
        }
    }

    ProgressDialog progressDialog;
    String originalPdf;
    String nextPdf;
    String curPdf;
    int curComposeNum = 0; //第几次合成
    MuPDFCore muPDFCore;
    SavePdfTask savePdfTask;

    private void initPdfReadView() {
//        originalPdf = Environment.getExternalStorageDirectory().getPath() + "/pdf_file/testpdf.pdf";
        originalPdf = current_pdf;
        LogUtils.e(" --------------- " + originalPdf);
        try {
            muPDFCore = new MuPDFCore(originalPdf);//要显示的PDF的文件路径
            readerView.setAdapter(new PageAdapter(this, muPDFCore));
            readerView.setDisplayedViewIndex(0);//要显示的PDF的页数
        } catch (Exception e) {
            Toast.makeText(Aty_Pdf.this, "此文件路径无效", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                back();
                break;
            case R.id.tv_title_right:
                if(signatureView.isShown()){
                    Toast.makeText(Aty_Pdf.this, "您还没保存呢，亲", Toast.LENGTH_SHORT).show();
                 return;
                }
                String path_tu_ya = SPUtils.getInstance(Aty_Pdf.this).getString("path_tu_ya", "");
//                Toast.makeText(Aty_Pdf.this, "路径：" + path_tu_ya, Toast.LENGTH_SHORT).show();
                ShareFileUtils.shareFile(Aty_Pdf.this, path_tu_ya);
                break;
            case R.id.rl_sign:
                signature();
                break;
            case R.id.rl_clear:
                signatureView.clear();
                break;
            case R.id.rl_compose://合成图片！
                composeSignature();
                break;
        }
    }

    /**
     * 合成签名到指定的PDF文档
     */
    class SavePdfTask extends AsyncTask {
        ComposeSignatureToPdf savePdf;

        public SavePdfTask(ComposeSignatureToPdf savePdf) {
            this.savePdf = savePdf;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            savePdf.addText();
            return null;
        }

        //主线程
        @Override
        protected void onPostExecute(Object o) {
            progressDialog.dismiss();
            progressDialog = ProgressDialog.show(Aty_Pdf.this, null, "合成成功，正在载入，...");
            curComposeNum++; //合成后，涂鸦次数+1
            try {
                muPDFCore = new MuPDFCore(nextPdf);//显示合成后的PDF
                readerView.setAdapter(new PageAdapter(Aty_Pdf.this, muPDFCore));
                readerView.setmScale(1.0f);
                readerView.setDisplayedViewIndex(readerView.getDisplayedViewIndex());
                progressDialog.dismiss();
                LogUtils.e("-----------合成后的显示nextPdf --- " + nextPdf);
            } catch (Exception e) {
                LogUtils.e("合成后的显示nextPdf显示错误 ---- " + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //原文件和最后一份合成的文档不删除
        for (int i = 0; i < curComposeNum; i++) {
            File file = new File(originalPdf.substring(0, originalPdf.length() - 4) + i + ".pdf");
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            keyBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void keyBack() {
        if (null != savePdfTask && !savePdfTask.isCancelled())
            savePdfTask.cancel(true);
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.dismiss();
        Aty_Pdf.this.finish();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("是否退出？");
//        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //删除缓冲的存储
//                Aty_Pdf.this.finish();
//            }
//        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        }).show();
    }

    /**
     * 撤销合成
     * 原理：
     * 1、每次合成后都存储到本地，回退就是显示上一次合成后的文档
     * 2、每回退一次，就删除最后一次合成的文档
     * 2、退出当前APP或者Activity后除了最后一份签名后的文档和原文档，其余中间合成的文档都删除。
     */
    private void back() {
        if (curComposeNum == 0) {
            Toast.makeText(this, "已经是原始文档，不可回退。", Toast.LENGTH_LONG).show();
            return;
        }
        if (curComposeNum == 1) {
            LogUtils.e("回退 -----------  curComposeNum = 1");
            curPdf = originalPdf;
            nextPdf = originalPdf.substring(0, originalPdf.length() - 4) + +curComposeNum + ".pdf";
            LogUtils.e("回退 -----------  curComposeNum = 1 -- " + nextPdf);
        } else {
            curPdf = originalPdf.substring(0, originalPdf.length() - 4) + (curComposeNum - 1) + ".pdf";
            nextPdf = originalPdf.substring(0, originalPdf.length() - 4) + curComposeNum + ".pdf";
            LogUtils.e("回退 -----------  curComposeNum 不等于 1 -- " + nextPdf);
        }
        readerView.setmScale(1.0f);
        try {
            muPDFCore = new MuPDFCore(curPdf);
            readerView.setAdapter(new PageAdapter(Aty_Pdf.this, muPDFCore));
            readerView.setDisplayedViewIndex(readerView.getDisplayedViewIndex());
            curComposeNum--;
        } catch (Exception e) {
            Toast.makeText(Aty_Pdf.this, "文件异常，不存在", Toast.LENGTH_SHORT).show();
        }

    }

    private void signature() {
        if (signatureView.getVisibility() == View.GONE) {
            //可编辑状态
            signatureView.setVisibility(View.VISIBLE);
            rlCompose.setVisibility(View.VISIBLE);
            rlClear.setVisibility(View.VISIBLE);
            rlBack.setVisibility(View.VISIBLE);
            btnSign.setImageResource(R.drawable.icon_pdf_sign);
        } else {
            //不可编辑状态！
            signatureView.clear();
            signatureView.setVisibility(View.GONE);
//            rlCompose.setVisibility(View.GONE);
            rlBack.setVisibility(View.GONE);
            rlClear.setVisibility(View.GONE);
            btnSign.setImageResource(R.drawable.icon_pdf_signature);
        }
    }

    /**
     * 异步合成签名图片到PDF文档
     */
    private void composeSignature() {
        //确认存储位置和名称
        if (curComposeNum != 0) {
            curPdf = originalPdf.substring(0, originalPdf.length() - 4) + curComposeNum + ".pdf";
            nextPdf = originalPdf.substring(0, originalPdf.length() - 4) + (curComposeNum + 1) + ".pdf";
        } else {
            curPdf = originalPdf; //第一次涂鸦前的原始文件
            nextPdf = originalPdf.substring(0, originalPdf.length() - 4) + (curComposeNum + 1) + ".pdf"; //第一次涂鸦后的文件
        }
        ComposeSignatureToPdf savePdf = new ComposeSignatureToPdf(curPdf, nextPdf);
        savePdf.setPageNum(readerView.getDisplayedViewIndex() + 1);
        savePdf.setWidthScale(1.0f * readerView.scrollX / readerView.getDisplayedView().getWidth());//缩放后，相对于左下角 宽偏移的百分比
        savePdf.setHeightScale(1.0f * readerView.scrollY / readerView.getDisplayedView().getHeight());//缩放后，相对于左下角，长偏移的百分比
        if (signatureView.getWidth() == 0 || signatureView.getHeight() == 0) {
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(signatureView.getWidth(), signatureView.getHeight(),
                Bitmap.Config.ARGB_8888); //签名图片
        Canvas canvas = new Canvas(bitmap);
        signatureView.draw(canvas);//一定要执行此操作，否则图片上没有内容
        //HH  设置图片大小占放大后的pdf的比例
        float widthPercent = bitmap.getWidth() / (float) readerView.getDisplayedView().getWidth();
        float heightPercent = bitmap.getHeight() / (float) readerView.getDisplayedView().getHeight();
        savePdf.setImgPercent(widthPercent, heightPercent);
        savePdf.setBitmap(bitmap);
        savePdfTask = new SavePdfTask(savePdf);
        savePdfTask.execute();
        progressDialog = ProgressDialog.show(Aty_Pdf.this, null, "正在合成...");
        unSignatureViewState();
    }

    private void unSignatureViewState() {
        signatureView.setVisibility(View.GONE);
//        rlCompose.setVisibility(View.GONE);
        rlClear.setVisibility(View.GONE);
        rlBack.setVisibility(View.GONE);
        signatureView.clear();
        btnSign.setImageResource(R.drawable.icon_pdf_signature);
    }
}
