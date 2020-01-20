package com.sign.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.sign.R;


public class SimpleAlertDialogUtils {
    private Context mContext;
    private String title;
    private String text;
    private View view;//显示信息的主要的View
    private static SimpleAlertDialogUtils simpleAlertDialogUtils;

    private SimpleAlertDialogUtils() {
    }

    public void setOnMyDialogClickListner(OnMyDialogClickListner onMyDialogClickListner) {
        this.onMyDialogClickListner = onMyDialogClickListner;
    }

    private OnMyDialogClickListner onMyDialogClickListner;

    public static SimpleAlertDialogUtils getInstance() {
        if (simpleAlertDialogUtils == null) {
            simpleAlertDialogUtils = new SimpleAlertDialogUtils();
        }
        return simpleAlertDialogUtils;
    }

    public void createAlertDialog(Context context, String title, String text, final OnMyDialogClickListner onMyDialogClickListner) {
        mContext = context;
        this.title = title;
        this.text = text;
        view = UIUtils.getXmlView(context, R.layout.item_alert_dialog);
        TextView tvAlertDialog = (TextView) view.findViewById(R.id.tv_alert_dialog);
        tvAlertDialog.setText(text);
        //----------------------------
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setIcon(R.mipmap.logo)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onMyDialogClickListner != null) {
                            onMyDialogClickListner.setPositive();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    public interface OnMyDialogClickListner {
        public abstract void setPositive();
    }
}
