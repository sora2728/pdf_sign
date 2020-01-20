package com.sign._ding_dan;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sign.R;

import java.util.List;

/**
 * 订单的
 */

public class FrmAdapterDingDan extends FragmentPagerAdapter {
    private List<Fragment> fragmentsList;//显示的页面集合
    private Context mContext;
    public String[] mTilte = new String[]{"待发货", "已发货", "已签收", "已付款"};

    public FrmAdapterDingDan(FragmentManager fm, List<Fragment> fragments, Context context) {
        super(fm);
        mContext = context;
        this.fragmentsList = fragments;
    }

    /**
     * 返回要显示多少页
     *
     * @return
     */
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position % 7);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item_ding_dan, null);
        TextView tv = view.findViewById(R.id.tab_text0);
        tv.setText(mTilte[position] );
//        TextView tv1 = view.findViewById(R.id.tab_text1);
//        tv1.setText(stringArray[position]);
        return view;
    }

}
