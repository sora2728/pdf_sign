package com.sign.activity.aty_pdf;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.R;
import com.sign.util.DeleteFileUtil;
import com.sign.util.PopupWindowFactory;
import com.sign.util.ScreenUtils;
import com.sign.util.SimpleAlertDialog2Utils;
import com.sign.util.StateButton;
import com.sign.util.UIUtils;
import com.sign.view.CustomPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.List;

import static com.sign.com.MyApplication.getActivity;

/**
 * Created by di on 2019/7/17.
 */

public class Adapter_pdf_item extends CommonAdapter<String> {
    private List<String> mList;
    private Context mContext;
    private PopupWindowFactory popupWindowFactory;
    private CustomPopupWindow customPopupWindow;

    public Adapter_pdf_item(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        mList = datas;
        mContext = context;
    }

    //下面两个方法提供给页面刷新和加载时调用
    // 刷新数据的方法
    public void refresh(List<String> listRefresh) {
        if (listRefresh != null && listRefresh.size() >= 0) {
            mList.removeAll(mList);
            mList.addAll(listRefresh);
            notifyDataSetChanged();
        }
    }

    @Override
    protected void convert(ViewHolder holder, final String s, int position) {
        final View convertView = holder.getConvertView();
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvPath = convertView.findViewById(R.id.tv_path);
        File tempFile = new File(s.trim());
        String fileName = tempFile.getName();
        tvName.setText(fileName);
        tvPath.setText(s);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Aty_Pdf.class);
                intent.putExtra("pdf_address", s);
                mContext.startActivity(intent);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                UIUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        do_show_popup(convertView, s);
                    }
                });
                return true;
            }
        });
    }

    /**
     * **************************************************************************************************执行显示popupwindow
     *
     * @param v
     * @param str
     */
    private void do_show_popup(View v, String str) {
        //首先确定Popupwindow要显示的位置：
        int[] location = new int[2];
        v.getLocationOnScreen(location);//获取在屏幕上的位置！
        int x = location[0] + ScreenUtils.getScreenWidth(mContext) - UIUtils.dp2px(100);
        int y = location[1] + 10;
        customPopupWindow = new CustomPopupWindow.Builder()
                .setContext(mContext) //设置 context
                .setContentView(R.layout.pop_item_function) //设置布局文件
                .setwidth(LinearLayout.LayoutParams.WRAP_CONTENT) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了!
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT) //设置高度
                .setFouse(true)  //设置popupwindow 是否可以获取焦点
                .setOutSideCancel(true) //设置点击外部取消
                .setBackGroudAlpha((AtyGetPdf) mContext, 0F)
                .setAnimationStyle(R.style.popup_anim_style_up_to_down) //设置popupwindow动画,
                .builder()
                .showAtLocation(R.layout.aty_get_pdf, Gravity.NO_GRAVITY, x, y);
        init_popup_after(str);
    }

    private void init_popup_after(final String pdf_path) {
        StateButton btnDel = (StateButton) customPopupWindow.getItemView(R.id.btn_del);
        StateButton btnCancel = (StateButton) customPopupWindow.getItemView(R.id.btn_cancel);
        btnDel.setPressedBackgroundColor(getActivity().getResources().getColor(R.color.color_orange));
        btnCancel.setVisibility(View.GONE);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopupWindow.dismiss();
                SimpleAlertDialog2Utils.getInstance().createAlertDialog(getActivity(), "提示", "您要删除这条消息吗？", new SimpleAlertDialog2Utils.OnMyDialogClickListner() {
                    @Override
                    public void setPositive() {
                        Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
                        boolean b = DeleteFileUtil.deleteFile(pdf_path);
                        if (b) {
                            AtyGetPdf atyGetPdf = (AtyGetPdf) Adapter_pdf_item.this.mContext;
                            atyGetPdf.btnGetPdf();
                        } else {
                            Toast.makeText(mContext, "删除失败！", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void setNegative() {
                    }
                });
            }
        });
    }
    /**
     * **********************************************************************************************************************************
     */
}
