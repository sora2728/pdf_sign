package com.sign.activity;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sign.R;
import com.sign.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.sign.R.id.iv_title_right;

public class AtyCompany extends BaseActivity {

    @Bind(R.id.linear_bar)
    LinearLayout linearBar;
    @Bind(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(iv_title_right)
    ImageView ivTitleRight;
    @Bind(R.id.wv_company)
    WebView wvCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_company);
        ButterKnife.bind(this);
        initView();
        initClick();
    }

    private void initView() {
        tvTitle.setText("关 于 我 们");
        ivTitleRight.setVisibility(View.INVISIBLE);
        ivTitleLeft.setVisibility(View.VISIBLE);
        wvCompany.loadUrl("https://www.junyangwangluo.com:8666/");
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvCompany.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        wvCompany.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                wvCompany.loadUrl("https://www.junyangwangluo.com:8666/");
            }
        });
    }

    private void initClick() {
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
