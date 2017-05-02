package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.MineFootprintAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.SwipeListLayout;


/**
 * Created by 潘 on 2016/10/10.
 */

public class MineFootprintActivity extends Activity implements View.OnClickListener, MineFootprintAdapter.DeleteItemListener {

    private PullToRefreshListView listView;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanListLoading;
    private Context context;
    private ProgressDialog progressDialog;
    private MineFootprintAdapter everydayChoicenessAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GetMyBrowseHistory:
                    Log.e("succ", "succ");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            progressDialog.dismiss();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                NewArrivalBean.NewArrivalDataBean newArrivalDataBean = new NewArrivalBean.NewArrivalDataBean();
                                newArrivalDataBean.setId(dataObject.getString("object_id"));
                                newArrivalDataBean.setGoodsname(dataObject.getString("goodsname"));
                                newArrivalDataBean.setPrice(dataObject.getString("price"));
                                newArrivalDataBean.setDescription(dataObject.getString("description"));
                                newArrivalDataBean.setPicture(dataObject.getString("photo"));

                                newArrivalDataBeanList.add(newArrivalDataBean);
                            }
                            if (newArrivalDataBeanList.size() >= 7) {
                                for (int i = 0; i < 7; i++) {
                                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                                }
                            } else {
                                for (int i = 0; i < newArrivalDataBeanList.size(); i++) {
                                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                                }
                            }
                            everydayChoicenessAdapter = new MineFootprintAdapter(context, newArrivalDataBeanListLoading,
                                    (MineFootprintAdapter.DeleteItemListener) context);
                            listView.setAdapter(everydayChoicenessAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //清空我的足迹
                case CommunalInterfaces.DELETE_MY_FOOTPRINT:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(context, "清空成功！", Toast.LENGTH_SHORT).show();
                            newArrivalDataBeanListLoading.clear();
                            everydayChoicenessAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "清空失败！请重试", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //删除某一条我的足迹
                case CommunalInterfaces.DELETE_MY_FOOTPRINT_ITEM:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject2.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "删除足迹成功！");
                        } else {
                            Toast.makeText(context, "删除失败！请重试", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private TextView tvClean;
    private static Set<SwipeListLayout> sets = new HashSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_footprint);
        context = this;
        initView();
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        progressDialog.setMessage("正在加载...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        if (NetUtil.isConnnected(this)) {
            new HomeRequest(this, handler).GetMyBrowseHistory();
        }

        //设置可上拉刷新和下拉刷新
        listView.setMode(PullToRefreshBase.Mode.BOTH);

        //设置刷新时显示的文本
        ILoadingLayout startLayout = listView.getLoadingLayoutProxy(true, false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = listView.getLoadingLayoutProxy(false, true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(MineFootprintActivity.this, 1).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataAsyncTask(MineFootprintActivity.this, 2).execute();

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

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

    //adapter接口回调
    public void deleteItem(String goodId) {
        if (NetUtil.isConnnected(context)) {
            new MineRequest(context, handler).deleteMyFootprintItem("1", goodId);//默认值type = 1
        }
    }

    /**
     * 滑动删除条目监听
     */
    public static class MyOnSlipStatusListener implements SwipeListLayout.OnSwipeStatusListener {

        private SwipeListLayout slipListLayout;

        public MyOnSlipStatusListener(SwipeListLayout slipListLayout) {
            this.slipListLayout = slipListLayout;
        }

        @Override
        public void onStatusChanged(SwipeListLayout.Status status) {
            if (status == SwipeListLayout.Status.Open) {
                //若有其他的item的状态为Open，则Close，然后移除
                if (sets.size() > 0) {
                    for (SwipeListLayout s : sets) {
                        s.setStatus(SwipeListLayout.Status.Close, true);
                        sets.remove(s);
                    }
                }
                sets.add(slipListLayout);
            } else {
                if (sets.contains(slipListLayout))
                    sets.remove(slipListLayout);
            }
        }

        @Override
        public void onStartCloseAnimation() {

        }

        @Override
        public void onStartOpenAnimation() {

        }

    }

    /**
     * 异步下载任务
     */
    private static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private MineFootprintActivity mainActivity;
        private int judge;

        public LoadDataAsyncTask(MineFootprintActivity mainActivity, int judge) {
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
                mainActivity.everydayChoicenessAdapter.notifyDataSetChanged();
                mainActivity.listView.onRefreshComplete();//刷新完成
            }
        }
    }

    private void loadData(int judge) {
        int size = newArrivalDataBeanListLoading.size();
        if (judge == 1) {
            return;
        } else {
            if (newArrivalDataBeanList.size() >= (size + 7)) {
                for (int i = size; i < size + 7; i++) {
                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                }
            } else {
                for (int i = size; i < newArrivalDataBeanList.size(); i++) {
                    newArrivalDataBeanListLoading.add(newArrivalDataBeanList.get(i));
                }
            }
            return;
        }
    }

    private void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.listView_my_footprint);
        newArrivalDataBeanList = new ArrayList<>();
        newArrivalDataBeanListLoading = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("我的足迹");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        tvClean = (TextView) findViewById(R.id.top_clean_footprint);
        tvClean.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.top_clean_footprint:
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("清空我的足迹？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (NetUtil.isConnnected(context)) {
                                    new MineRequest(context, handler).deleteMyFootprint("1");

                                } else {
                                    Toast.makeText(context, "请检查网络！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }
}
