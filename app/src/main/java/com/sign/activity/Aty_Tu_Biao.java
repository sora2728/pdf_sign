package com.sign.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sign.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Aty_Tu_Biao extends Activity {

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
    @Bind(R.id.activity_zhe_xian)
    LinearLayout activityZheXian;
    @Bind(R.id.chart)
    BarChart chart;
    private Typeface mTf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_tu_biao);
        ButterKnife.bind(this);
        initView();
        initDate();//
    }
    private void initView() {
        tvTitle.setText("柱状图示例");
    }

    /**
     * 显示数据的操作
     */
    private void initDate() {
        //初始化字体显示
        mTf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
        //设置描述的信息
        chart.setDescription("");
        //是否绘制网格背景
        chart.setDrawGridBackground(false);
        //是否绘制柱状图阴影
        chart.setDrawBarShadow(false);
        //获取X轴
        XAxis xAxis = chart.getXAxis();
        //设置X轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置字体集
        xAxis.setTypeface(mTf);
        //是否绘制x轴的网格线
        xAxis.setDrawGridLines(false);
        //是否绘制x轴线
        xAxis.setDrawAxisLine(true);
        //获取左边的y轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        //最高的柱状图距离顶端的距离
        leftAxis.setSpaceTop(20f);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        BarData barData = generateDataBar(0);
        barData.setValueTypeface(mTf);
        // 柱状图设置显示的数据
        chart.setData(barData);
        //添加动画效果，就不需要调用invalidate()方法了！
        // do not forget to refresh the chart
//        holder.chart.invalidate();
        chart.animateY(700);
    }

    /**
     * generates a random ChartData object with just one DataSet
     * 直接生成柱状图所需要的数据，参数是代表有几个柱状图显示混在一起显示，一般为1个，参数可以去掉
     *
     * @return
     */
    private BarData generateDataBar(int cnt) {
        //主要的一个list集合，这个就是显示的数据来源！
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        //这里是用随机数模拟的显示的柱状图数据，
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }
        BarDataSet d = new BarDataSet(entries, "水电消费柱状图");
        //设置柱状图之间的间隙
        d.setBarSpacePercent(30f);
        //设置柱状图的颜色
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //设置高亮的透明度，就是点击的时候，显示的颜色！
        d.setHighLightAlpha(255);//255代表没有透明度！
        BarData cd = new BarData(getMonths(), d);
        return cd;
    }

    private ArrayList<String> getMonths() {
        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
}
