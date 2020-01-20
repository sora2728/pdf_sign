package com.sign.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sign.R;

/**
 * 图片加载的工具类Glide的封装操作！
 */
public class GlideUtils {
    public static void display_circle(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new CircleCrop())
                .error(R.mipmap.icon_defaut_circle)
                .fallback(R.mipmap.icon_defaut_circle);
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions();
        Glide.with(context)
                .load(url)
                .thumbnail(0.2f)
                .apply(requestOptions).transition(transitionOptions.crossFade())
                .into(imageView);
    }

    /**
     * @param context
     * @param imageView
     * @param url
     */
    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.img_error)
                .error(R.mipmap.img_error)
//               .transform(new GlideCircleTransform(this))
                .fallback(R.mipmap.icon_chat_photo);
        Glide.with(context)
                .load(url)
                .thumbnail(0.2f)
                .apply(requestOptions)
                .into(imageView);
    }
}
