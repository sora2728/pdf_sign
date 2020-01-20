package com.sign.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sign.R;

import java.util.Random;

/**
 * 爱心布局！自定义View的实现
 */

public class LoveLayout extends RelativeLayout {
    private Context mContext;
    private Drawable[] drawables = new Drawable[3];
    private Random random = new Random();
    private int dHeight;
    private int dWidth;
    private int mWidth;
    private int mHeight;
    private RelativeLayout.LayoutParams params;

    public LoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawables[0] = getResources().getDrawable(R.drawable.red);
        drawables[1] = getResources().getDrawable(R.drawable.yellow);
        drawables[2] = getResources().getDrawable(R.drawable.green);
        //得到图片的原始的宽高！
        dWidth = drawables[0].getIntrinsicWidth();
        dHeight = drawables[0].getIntrinsicWidth();
        params = new LayoutParams(dWidth, dHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    /**
     * 显示动画的操作：放在自定义控件中！
     * 一点击： 增加一个View！
     */
    public void addLove() {
        ImageView iv = new ImageView(mContext);
        iv.setImageDrawable(drawables[random.nextInt(3)]);
        //位置在底部，水平居中！
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        this.addView(iv, params);

        //添加动画：平移，渐变，缩放！ 属性动画的集合！
        addAnimal(iv);
    }

    private void addAnimal(final ImageView iv) {
        AnimatorSet set = getAnimater(iv);
        //开启动画！
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(iv);
            }
        });
    }

    private AnimatorSet getAnimater(ImageView iv) {
        //缩放
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "sacleX", 0.4f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.4f, 1f);
        //alpha
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 0.4f, 1f);
        //三个动画一起执行
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY, alpha);
        set.setDuration(300);
        //准备贝塞尔动画！----evaluator估值器-----------------
        ValueAnimator bezierAnimator = getBezierAnimator(iv);
        //综合所有动画：
        AnimatorSet s = new AnimatorSet();
        s.playSequentially(set, bezierAnimator);
        s.setTarget(iv);
        return s;
    }

    /**
     * 添加贝塞尔曲线的ValueAnimator动画！
     *
     * @return
     */
    private ValueAnimator getBezierAnimator(final ImageView iv) {
        //贝塞尔曲线怎么控制坐标？准备四个关键坐标点（起始点p0,拐点p1,拐点p2,拐点p3）
        PointF pointF0 = new PointF((mWidth - dWidth) / 2, mHeight - dHeight);
        PointF pointF1 = getPoint(1);
        PointF pointF2 = getPoint(2);
        PointF pointF3 = new PointF(random.nextInt(mWidth), 0);
        BezierEvaluator evaluator = new BezierEvaluator(pointF1, pointF2);
        //属性动画不仅仅可以改变View的属性，还可以改变自定义的一些属性！
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(evaluator, pointF0, pointF3);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                //控制属性的变化：以下在运动中变化！
                iv.setX(pointF.x);
                iv.setY(pointF.y);
                iv.setScaleX(1 - animation.getAnimatedFraction());
                iv.setScaleY(1 - animation.getAnimatedFraction());
                iv.setAlpha(1 - animation.getAnimatedFraction());
            }
        });
        return valueAnimator;
    }

    private PointF getPoint(int i) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth);
        if (i == 1) {//下面的p1
            pointF.y = random.nextInt(mHeight / 2) + mHeight / 2;
        } else {
            pointF.y = random.nextInt(mHeight / 2);
        }
        return pointF;
    }
}
