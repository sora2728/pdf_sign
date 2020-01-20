package com.sign._ding_dan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sign.R;
import com.sign.util.ScreenUtils;
import com.sign.util.UIUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import static com.sign.com.MyApplication.context;

public class Adapter_frm0_item extends CommonAdapter<String> {
    private List<String> mList;

    public Adapter_frm0_item(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        mList = datas;
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

    public void add(List<String> addMessageList) {
        //增加数据
        int position = mList.size();
        mList.addAll(position, addMessageList);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        View convertView = holder.getConvertView();
        final ImageView imageView = convertView.findViewById(R.id.iv_img);
        String url1 = "http://www.itxtbook.com/attachment/Day_081022/23_163307_e9b4513a7afee66.jpg";
        String url0 = "http://pic11.nipic.com/20101210/2531170_111449179301_2.jpg";
        String url = "";
        if (position % 3 == 0) {
            url = url0;
        } else if (position % 3 == 1) {
            url = url1;
        } else {
            url = "";
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        int widthShow = ScreenUtils.getScreenWidth(context);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(lp.width = widthShow / 2 - UIUtils.dp2px(10), lp.height = widthShow / 2);
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                int widthShow = ScreenUtils.getScreenWidth(context);
                lp.width = widthShow / 2 - UIUtils.dp2px(5);
//                lp.height = height * 1080 / width;//固定高度！
                lp.height = widthShow / 2;
                Log.e("TAG", "显示高度 -----  " + lp.height);
                imageView.setLayoutParams(lp);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
