package cn.xiaocool.android_etong.UI.Main;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.FlashSaleAdapter;
import cn.xiaocool.android_etong.adapter.LocalAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleFiveFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleFourFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleOneFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleThreeFragment;
import cn.xiaocool.android_etong.fragment.FlashSale.FlashSaleTwoFragment;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.view.PagerSlidingTabStrip;

/**
 * Created by wzh on 2016/7/24.
 */
public class FlashSaleActivity extends FragmentActivity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    private String[] titles = new String[5];
    private TextView tvTitle,tv_time1,tv_time2,tv_time3;
    private RelativeLayout rlBack;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private Context context;
    private long time;
    private int position = 0;
    private ArrayList<Fragment> fragments;
    private String type;
    private ProgressBar progressBar;
    private PagerSlidingTabStrip activityAddressTab;
    private ViewPager activityAddressViewPager;
    private ProgressDialog progressDialog;
    public int pushtype = 0;
    private FlashSaleAdapter flashSaleAdapter;
    FlashSaleOneFragment flashSaleOneFragment = new FlashSaleOneFragment();
    FlashSaleTwoFragment flashSaleTwoFragment = new FlashSaleTwoFragment();
    FlashSaleThreeFragment flashSaleThreeFragment = new FlashSaleThreeFragment();
    FlashSaleFourFragment flashSaleFourFragment = new FlashSaleFourFragment();
    FlashSaleFiveFragment flashSaleFiveFragment = new FlashSaleFiveFragment();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0X111111:
                    activityAddressViewPager.setCurrentItem(position);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.flash_sale);
        context = this;
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        initView();
        judgeTime();
        setFragment();
        Log.e("当前选中", String.valueOf(activityAddressViewPager.getCurrentItem()));
        Log.e("title=",titles[0]);
    }

    /**
     * 设置fragment
     */
    private void setFragment() {
        fragments = new ArrayList<>();
        fragments.add(flashSaleOneFragment);
        fragments.add(flashSaleTwoFragment);
        fragments.add(flashSaleThreeFragment);
        fragments.add(flashSaleFourFragment);
        fragments.add(flashSaleFiveFragment);
        activityAddressViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        activityAddressTab.setViewPager(activityAddressViewPager);
        activityAddressViewPager.setOffscreenPageLimit(4);
        //Fragment的每次创建 用了 newInstance (为了保证不内存溢出) 因为数据会重新init 在 handler里面再调用ViewPager 的 setCurrentItem方法
        handler.sendEmptyMessage(0X111111);
    }

    /**
     * viewpager适配器
     */
    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    private void initView() {
        newArrivalDataBeanList = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("限时抢购");
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);

        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        activityAddressTab = (PagerSlidingTabStrip) findViewById(R.id.activity_address_tab);
        activityAddressViewPager = (ViewPager) findViewById(R.id.activity_address_viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void judgeTime() {
        Calendar cal = Calendar.getInstance();// 当前日期
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
        int minute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        final long start = 10* 60 ;// 起始时间 00:00的分钟数
        final long end = 12 * 60;// 结束时间 8:00的分钟数
        final long end2 = 14*60;
        final long end3 = 16*60;
        final long end4 = 18*60;
        final long end5 = 20*60;
        if (minuteOfDay >= start && minuteOfDay <= end) {
            type = "1";
            titles[0]=("10:00:正在进行");
            titles[1]=("12:00:即将开始");
            titles[2]=("14:00:即将开始");
            titles[3]=("16:00:即将开始");
            titles[4]=("18:00:即将开始");
            time = end*60 - minuteOfDay*60;
            position = 0;
        }else if (minuteOfDay >= end && minuteOfDay <= end2){
            type = "2";
            titles[0]=("10:00:已结束");
            titles[1]=("12:00:正在进行");
            titles[2]=("14:00:即将开始");
            titles[3]=("16:00:即将开始");
            titles[4]=("18:00:即将开始");
            time = end2*60 - minuteOfDay*60;
            position = 1;
        }else if (minuteOfDay >= end2 && minuteOfDay <= end3){
            type = "3";
            titles[0]=("10:00:已结束");
            titles[1]=("12:00:已结束");
            titles[2]=("14:00:正在进行");
            titles[3]=("16:00:即将开始");
            titles[4]=("18:00:即将开始");
            time = end3*60 - minuteOfDay*60;
            position = 2;
        }else if (minuteOfDay >= end3 && minuteOfDay <= end4){
            type = "4";
            titles[0]=("10:00:已结束");
            titles[1]=("12:00:已结束");
            titles[2]=("14:00:已结束");
            titles[3]=("16:00:正在进行");
            titles[4]=("18:00:即将开始");
            time = end4*60 - minuteOfDay*60;
            position = 3;
        }else if (minuteOfDay >= end4&& minuteOfDay <= end5){
            type = "5";
            titles[0]=("10:00:已结束");
            titles[1]=("12:00:已结束");
            titles[2]=("14:00:已结束");
            titles[3]=("16:00:已结束");
            titles[4]=("18:00:正在进行");
            time = end5*60  - minuteOfDay*60;
            position = 4;
        }else if (minuteOfDay >= end5){
            type = "5";
            titles[0]=("10:00:已结束");
            titles[1]=("12:00:已结束");
            titles[2]=("14:00:已结束");
            titles[3]=("16:00:已结束");
            titles[4]=("18:00:已结束");
            tv_time2.setText("已结束");
            position = 4;
        }
        Log.e("type=",type);

        return;
    }

    public  String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour+"："+minute+"："+second;
        return strtime;

    }



    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
//        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        activityAddressViewPager.setCurrentItem(pushtype);
        pushtype = 0;
    }

    @Override
    public void onPause() {
//        if (mDemoSlider != null) {
//            mDemoSlider.stopAutoCycle();
//        }
        super.onPause();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
