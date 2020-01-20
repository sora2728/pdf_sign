package com.sign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sign.com.Constants;
import com.sign.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class LockView extends View {
    private Paint mPaint = new Paint();
    private RectF rectF = new RectF();
    private float width_and_height;
    private int mWidth;
    private int mHeight;
    private static final int PATTERN_SIZE_ONE = 130; //图案大小(大圆)
    private static final int PATTERN_SIZE_TWO = 30; //图案大小(小圆)
    private float mCenterSize; // 每一个宫格中心点
    private List<Pattern> patterns = new ArrayList<>();
    private float mLineEndX = -1;
    private float mLineEndY = -1;
    private int index = -1;
    private List<Integer> numbers = new ArrayList<>();
    String password = "";

    private int drawCount = 0;
    private boolean isDrawTwo = false; // 是否开始绘制第二次

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setColor(0xff999999);
        mPaint.setAntiAlias(true);

    }

    private void initPattern() {
        // 添加图案对象之前先清理集合，防止重复数据
        patterns.clear();
        ////////// 开始位置 = 宫格中心位置 - （图案大小 / 2） X和Y是一样的
        /////////  结束位置 = 开始位置 + 图案大小
        /////////  第二个图案的位置 (X的开始位置 += 宫格大小) 以此类推
        /////////  如果到了第三个应该换行 那么 (Y的开始位置 += 宫格大小) X的位置重置
        float startX = mCenterSize - PATTERN_SIZE_ONE / 2;
        float startY = mCenterSize - PATTERN_SIZE_ONE / 2;
        float startXTwo = mCenterSize - PATTERN_SIZE_TWO / 2;
        float startYTwo = mCenterSize - PATTERN_SIZE_TWO / 2;
        for (int i = 1; i <= 9; i++) {
            float enX = startX + PATTERN_SIZE_ONE;
            float enY = startY + PATTERN_SIZE_ONE;
            float enXTwo = startXTwo + PATTERN_SIZE_TWO;
            float enYTwo = startYTwo + PATTERN_SIZE_TWO;
            Pattern pattern = new Pattern();
            pattern.startX = startX;
            pattern.startY = startY;
            pattern.endX = enX;
            pattern.endY = enY;
            pattern.startXTwo = startXTwo;
            pattern.startYTwo = startYTwo;
            pattern.endXTwo = enXTwo;
            pattern.endYTwo = enYTwo;
            patterns.add(pattern);
            startX += width_and_height;
            startXTwo += width_and_height;
            if (i % 3 == 0) {
                startX = mCenterSize - PATTERN_SIZE_ONE / 2;
                startXTwo = mCenterSize - PATTERN_SIZE_TWO / 2;
                startY += width_and_height;
                startYTwo += width_and_height;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0 || mHeight == 0) {
            return;
        }
        drawPattern(canvas);
        drawLine(canvas);
        drawPatternTwo(canvas);

    }


    private void drawLine(Canvas canvas) {
        // index是用户触摸到的当前图案索引，如果等于-1说明用户还没触摸到图案，那就直接return
        if (index == -1) {
            return;
        }
        mPaint.setColor(0xffAAFFC7);
        mPaint.setStrokeWidth(20);

        // 这些循环是绘制用户已经触目到固定的位置
        // 比如用户触摸的位置是 图案1 和 2 那么1-2之间的线就固定了 直接绘制出来
        for (int i = 0; i < numbers.size() - 1; i++) {
            float startX1 = (patterns.get(numbers.get(i)).startX + patterns.get(numbers.get(i)).endX) / 2f;
            float startY1 = (patterns.get(numbers.get(i)).startY + patterns.get(numbers.get(i)).endY) / 2f;
            float endX = (patterns.get(numbers.get(i + 1)).startX + patterns.get((numbers.get(i + 1))).endX) / 2f;
            float endY = (patterns.get(numbers.get(i + 1)).startY + patterns.get((numbers.get(i + 1))).endY) / 2f;
            canvas.drawLine(startX1, startY1, endX, endY, mPaint);
        }
        // 这里绘制用户当前正在触摸的轨迹， 线的轨迹 = （最后一个固定的位置的中心 ---> 当前触摸的位置）
        // 当前触摸的位置会在onTouchEvent里面里面获取
        float startX = (patterns.get(index).startX + patterns.get(index).endX) / 2f;
        float startY = (patterns.get(index).startY + patterns.get(index).endY) / 2f;
        canvas.drawLine(startX, startY, mLineEndX, mLineEndY, mPaint);
    }

    private void drawPattern(Canvas canvas) {
        for (int i = 0; i < patterns.size(); i++) {
            rectF.left = patterns.get(i).startX;
            rectF.top = patterns.get(i).startY;
            rectF.right = patterns.get(i).endX;
            rectF.bottom = patterns.get(i).endY;
            if (patterns.get(i).isClick) {
                mPaint.setColor(0xff4abdcc);
            } else {
                mPaint.setColor(0x88000000);
            }
            canvas.drawRoundRect(rectF, 100, 100, mPaint);
        }
    }

    private void drawPatternTwo(Canvas canvas) {
        for (int i = 0; i < patterns.size(); i++) {
            rectF.left = patterns.get(i).startXTwo;
            rectF.top = patterns.get(i).startYTwo;
            rectF.right = patterns.get(i).endXTwo;
            rectF.bottom = patterns.get(i).endYTwo;
            mPaint.setColor(0xffffffff);
            canvas.drawRoundRect(rectF, 100, 100, mPaint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isDrawTwo) {
                    cancel();
                }
                upDatePattern(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                upDatePattern(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (index != -1) { //如果抬起时不是-1说明落到了有效位置 才执行下面操作
                    isDrawTwo = false;
                    mLineEndX = (patterns.get(index).startX + patterns.get(index).endX) / 2f;
                    mLineEndY = (patterns.get(index).startY + patterns.get(index).endY) / 2f;
                    if (onDrawFinish != null) {
                        if (drawCount == 0) {
                            onDrawFinish.oneDraw(numbers);
                            password = numbers.toString();
                            Log.e("TAG", "------00000000000000-------------" + password);
                            //限制至少输入4个点！
                            if (numbers.size() < 4) {
                                cancel();
                            } else {
                                drawCount++;
                            }
                        } else {
                            password += "完成" + numbers.toString();
                            Log.e("TAG", "-------------------" + password);
                            onDrawFinish.twoDraw(isOk());
                        }
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 用来判断两次绘制是否一致
     *
     * @return true 一致 false 不一致
     */
    private boolean isOk() {
        String[] strings = password.split("完成");
        if (strings[0].equals(strings[1])) {
            SPUtils.getInstance(getContext()).save(Constants.sp_key_shou_shi, strings[0]);
        }
        return strings[0].equals(strings[1]);
    }

    /**
     * 根据用户滑动轨迹用来判断是否落到九宫格点上 如果落上记录该值
     */
    private void upDatePattern(float x, float y) {
        // 这两个值是线的结束位置
        mLineEndX = x;
        mLineEndY = y;
        for (int i = 0; i < patterns.size(); i++) {
            // 如果用户触摸的位置范围是在图案的范围，并且这个图案没有被选中 那么记录当前位置
            // 并且给当前位置的Pattern对象的boolean 复制为true
            if (x >= patterns.get(i).startX && x <= patterns.get(i).endX &&
                    y >= patterns.get(i).startY && y <= patterns.get(i).endY
                    && !patterns.get(i).isClick) {
                patterns.get(i).isClick = true;
                //////////////// 如果用户跳过本该绘制的宫格 那我们帮他绘制上 /////////////
                //////////////// 比如用户的轨迹是1-3 那么如果2是没有绘制的那就给它绘制上 /////////////
                if (index != -1) {
                    if (Math.abs(i - index) == 8) {
                        int tempIndex = 4;
                        patterns.get(tempIndex).isClick = true;
                        index = tempIndex;
                        numbers.add(index);
                    } else if (Math.abs(i - index) == 6) {
                        int tempIndex = i - index > 0 ? i - 3 : i + 3;
                        patterns.get(tempIndex).isClick = true;
                        index = tempIndex;
                        numbers.add(index);
                    } else if ((i == 2 && index == 6) || (i == 6 && index == 2)) {
                        int tempIndex = 4;
                        patterns.get(tempIndex).isClick = true;
                        index = tempIndex;
                        numbers.add(index);
                    } else if (Math.abs(i - index) == 2 &&
                            patterns.get(i).startY == patterns.get(index).startY) {
                        int tempIndex = i - index > 0 ? i - 1 : i + 1;
                        patterns.get(tempIndex).isClick = true;
                        index = tempIndex;
                        numbers.add(index);
                    }
                }
                index = i;
                // 用一个数字集合记录索引位置
                numbers.add(index);
                return;
            }
        }

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取到View的宽度和高度
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        // 进行比较 选取最小值进行分配空间，保证是一个正方形
        int size = Math.min(mWidth, mHeight);

        // 计算每个宫格的宽和高，因为是正方形所以值都是一样的，一个变量就可以
        // 这个值用来绘制的时候使用
        width_and_height = size / 3f;

        // 计算每个宫格的中心点位置，主要为了绘制原点时保持居中
        mCenterSize = width_and_height / 2f;

        // 初始化每个宫格内的图案的位置
        initPattern();
    }

    /**
     * 重新开始绘制调用此方法
     */
    public void cancel() {
        numbers.clear();
        drawCount = 0;
        for (int i = 0; i < patterns.size(); i++) {
            patterns.get(i).isClick = false;
        }
        index = -1;
        invalidate();
    }

    /**
     * 再次确认绘制调用此方法
     */
    public void drawTwo() {
        isDrawTwo = true;
        numbers.clear();
        for (int i = 0; i < patterns.size(); i++) {
            patterns.get(i).isClick = false;
        }
        index = -1;
        invalidate();
    }

    private OnDrawFinish onDrawFinish;

    public interface OnDrawFinish {
        /**
         * 绘制第一次
         *
         * @param passwords 密码集合
         */
        void oneDraw(List<Integer> passwords);

        void twoDraw(boolean isOk);
    }

    public void setOnDrawFinish(OnDrawFinish onDrawFinish) {
        this.onDrawFinish = onDrawFinish;
    }

    private class Pattern {
        // 是否选中，根据次变量来知道用户滑中了几个位置
        public boolean isClick;
        //大图案位置（外面的大圆）
        public float startX;
        public float startY;
        public float endX;
        public float endY;
        // 小图案位置 （里面的小圆）
        public float startXTwo;
        public float startYTwo;
        public float endXTwo;
        public float endYTwo;
    }
}
