package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

import cn.xiaocool.android_etong.R;

/**
 * Created by hzh on 2016/12/28.
 */

public class TimeSelectActivity extends Activity implements View.OnClickListener{
    private Context context;
    private TextView tv_begintime,tv_endtime;
    private RelativeLayout rl_back;
    private Button mTabs[];
    private Button btn_select;
    private int index, currentIndex;
    private Calendar ca = Calendar.getInstance();
    private  SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd",
            Locale.CHINA);
    private String begintime,endtime,shopid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_time_select);
        context = this;
        Intent intent = getIntent();
        begintime = intent.getStringExtra("begintime");
        endtime = intent.getStringExtra("endtime");
        shopid = intent.getStringExtra("shopid");
        initView();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_begintime = (TextView) findViewById(R.id.tv_begintime);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_select.setOnClickListener(this);
        mTabs = new Button[5];
        mTabs[0] = (Button) findViewById(R.id.btn_0);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_1);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (Button) findViewById(R.id.btn_2);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (Button) findViewById(R.id.btn_3);
        mTabs[3].setOnClickListener(this);
        mTabs[0].setSelected(true);

        Date date = new Date();
        Log.e("当前时间为",date.toString());

        //得到一个Calendar的实例
        ca.setTime(new Date());
//        // 设置时间为当前时间
//        ca.add(Calendar.DAY_OF_MONTH, -7);
//        // 年份减1
//        Date lastMonth = ca.getTime(); //结果

        tv_begintime.setText(sdr.format(date));
        tv_endtime.setText(sdr.format(date));
//
//
//        Log.e("一周前", String.valueOf(lastMonth.getTime()/1000)+ sdr.format(lastMonth));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_0:
                index = 0;
                Date dt = new Date();
                begintime = String.valueOf(dt.getTime()/1000);
                tv_begintime.setText(sdr.format(dt));
                break;
            case R.id.btn_1:
                index = 1;
                // 设置时间为当前时间
                ca.clear();
                ca.add(Calendar.DAY_OF_MONTH, -7);
                // 年份减1
                Date lastMonth = ca.getTime();
                begintime = String.valueOf(lastMonth.getTime()/1000);
                tv_begintime.setText(sdr.format(lastMonth));
                break;
            case R.id.btn_2:
                index = 2;
                ca.clear();
                // 设置时间为当前时间
                ca.add(Calendar.DAY_OF_MONTH, -30);
                // 年份减1
                Date lastMonth1 = ca.getTime();
                begintime = String.valueOf(lastMonth1.getTime()/1000);
                tv_begintime.setText(sdr.format(lastMonth1));
                break;
            case R.id.btn_3:
                index = 3;
                ca.clear();
                ca.add(Calendar.DAY_OF_MONTH, -90);
                // 年份减1
                Date lastMonth2 = ca.getTime();
                begintime = String.valueOf(lastMonth2.getTime()/1000);
                tv_begintime.setText(sdr.format(lastMonth2));
                break;
            case R.id.btn_select:
                Intent intent = new Intent();
                intent.putExtra("begintime",begintime);
                intent.putExtra("endtime",endtime);
                intent.putExtra("shopid",shopid);
                intent.setClass(context,SelectTurnoverActivity.class);
                startActivity(intent);
                break;
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
        Log.e("begintime",begintime);
        Log.e("endtime",endtime);
    }

}
