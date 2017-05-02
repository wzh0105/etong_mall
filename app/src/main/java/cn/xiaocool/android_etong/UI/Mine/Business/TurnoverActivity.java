package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.MineFootprintActivity;
import cn.xiaocool.android_etong.adapter.LocalAdapter;
import cn.xiaocool.android_etong.adapter.TurnoverAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.bean.business.Turnover;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.view.SwipeListLayout;

/**
 * Created by hzh on 2016/12/28.
 */

public class TurnoverActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private String shopid,begintime,endtime;
    private TextView tv_money,tv_select_time;
    private PullToRefreshListView list_turnover;
    private TurnoverAdapter turnoverAdapter;
    private List<Turnover.DataBean> dataBeans;
    private List<Turnover.DataBean> dataBeansLoding;
    private static Set<SwipeListLayout> sets = new HashSet();
    private Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.ShopGetTotalorder:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            tv_money.setText(jsonObject.getString("data"));
                        }else{
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.ShopGetTotalOrderList:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            dataBeans.clear();
                            Log.e("success", "加载数据");
                            int length = jsonArray.length();
                            for (int i = 0;i<length;i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Turnover.DataBean dataBean = new Turnover.DataBean();
                                dataBean.setGoodsname(jsonObject1.getString("goodsname"));
                                dataBean.setPicture(jsonObject1.getString("picture"));
                                dataBean.setTime(jsonObject1.getString("time"));
                                dataBean.setMoney(jsonObject1.getString("money"));
                                dataBeans.add(dataBean);
                            }
                            if (dataBeans.size() >= 6) {
                                for (int i = 0; i < 6; i++) {
                                    dataBeansLoding.add(dataBeans.get(i));
                                }
                            } else {
                                for (int i = 0; i < dataBeans.size(); i++) {
                                    dataBeansLoding.add(dataBeans.get(i));
                                }
                                ILoadingLayout endLayout = list_turnover.getLoadingLayoutProxy(false, true);
                                endLayout.setPullLabel("正在上拉刷新...");
                                endLayout.setRefreshingLabel("数据已全部加载");
                                endLayout.setReleaseLabel("放开以刷新");
                            }
                            if (turnoverAdapter!=null){
                                Log.e("success", "设置适配器");
                                turnoverAdapter.notifyDataSetChanged();
                            }else {
                                Log.e("success","设置适配器");
                                turnoverAdapter = new TurnoverAdapter(context,dataBeans);
                                list_turnover.setAdapter(turnoverAdapter);
                            }
                        }else{
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_turnover);
        context = this;
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        initView();
        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        int minute = time.minute;
        int hour = time.hour;
        int sec = time.second;
        Log.e("当前时间为",year + "年 " + month + "月 " + day + "日 " + hour + "时 " + minute + "分 " + sec + "秒");
        Log.e("月初时间为",year + "年 " + month + "月 " + 1 + "日 " + 0 + "时 " + 0 + "分 " + 0 + "秒");
        begintime = year + "年 " + month + "月 " + 1 + "日 " + 0 + "时 " + 0 + "分 " + 0 + "秒";
        endtime = year + "年 " + month + "月 " + day + "日 " + hour + "时 " + minute + "分 " + sec + "秒";
        Log.e("月初时间戳为",data(begintime));
        Log.e("当前时间戳为",data(endtime));

        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).ShopGetTotalorder(shopid,begintime,endtime);
            new MainRequest(context,handler).ShopGetTotalOrderList(shopid,begintime,endtime);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {

        dataBeans = new ArrayList<>();
        dataBeansLoding = new ArrayList<>();
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_select_time = (TextView) findViewById(R.id.tv_select_time);
        tv_select_time.setOnClickListener(this);
        list_turnover = (PullToRefreshListView) findViewById(R.id.list_turnover);
        //设置可上拉刷新和下拉刷新
        list_turnover.setMode(PullToRefreshBase.Mode.BOTH);

        //设置刷新时显示的文本
        ILoadingLayout startLayout = list_turnover.getLoadingLayoutProxy(true, false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = list_turnover.getLoadingLayoutProxy(false, true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");

        list_turnover.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new TurnoverActivity.LoadDataAsyncTask(TurnoverActivity.this, 1).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new TurnoverActivity.LoadDataAsyncTask(TurnoverActivity.this, 2).execute();

            }
        });

        list_turnover.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //当listview开始滑动时，若有item的状态为Open，则Close，然后移除
                    case SCROLL_STATE_TOUCH_SCROLL:
                        if (sets.size() > 0) {
                            for (SwipeListLayout s : sets) {
                                s.setStatus(SwipeListLayout.Status.Close, true);
                                sets.remove(s);
                            }
                        }
                        break;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_select_time:
                Intent intent = new Intent();
                intent.setClass(context,TimeSelectActivity.class);
                intent.putExtra("begintime",data(begintime));
                intent.putExtra("endtime",data(endtime));
                intent.putExtra("shopid",shopid);
                startActivity(intent);
                break;
        }
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳
     *
     * @param time
     * @return
     */
    public String data(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    /**
     * 异步下载任务
     */
    private static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private TurnoverActivity mainActivity;
        private int judge;

        public LoadDataAsyncTask(TurnoverActivity mainActivity, int judge) {
            this.mainActivity = mainActivity;
            this.judge = judge;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                mainActivity.loadData(judge);
                Thread.sleep(2000);
                return "seccess";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 完成时的方法
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("seccess")) {
                mainActivity.turnoverAdapter.notifyDataSetChanged();
                mainActivity.list_turnover.onRefreshComplete();//刷新完成
            }
        }
    }

    private void loadData(int judge) {
        int size = dataBeansLoding.size();
        if (judge == 1) {
            return;
        } else {
            if (dataBeans.size() >= (size + 6)) {
                for (int i = size; i < size + 6; i++) {
                    dataBeansLoding.add(dataBeans.get(i));
                }
            } else {
                for (int i = size; i < dataBeans.size(); i++) {
                    dataBeansLoding.add(dataBeans.get(i));
                }
                ILoadingLayout endLayout = list_turnover.getLoadingLayoutProxy(false, true);
                endLayout.setPullLabel("正在上拉刷新...");
                endLayout.setRefreshingLabel("数据已全部加载");
                endLayout.setReleaseLabel("放开以刷新");
            }
            return;
        }
    }


}
