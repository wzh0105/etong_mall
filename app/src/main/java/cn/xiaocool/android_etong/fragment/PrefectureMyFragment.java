package cn.xiaocool.android_etong.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.android_etong.R;

import cn.xiaocool.android_etong.UI.BindPhoneActivity;
import cn.xiaocool.android_etong.UI.LoginActivity;
import cn.xiaocool.android_etong.UI.MainActivity;
import cn.xiaocool.android_etong.UI.Mine.MineFootprintActivity;
import cn.xiaocool.android_etong.adapter.GetBBSListAdapter;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.bean.CityBBSBean.DataBean;
import cn.xiaocool.android_etong.callback.ListRefreshCallBack;
import cn.xiaocool.android_etong.dao.ApiStores;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.SwipeListLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;

/**
 * Created by 潘 on 2016/6/14.
 */

public class PrefectureMyFragment extends BaseFragment implements View.OnClickListener {
    private Context context;
    private List<DataBean> list;
    //    private List<CityBBSBean> list;
    private GetBBSListAdapter getBBSListAdapter;
    private PullToRefreshScrollView scroll;
    private int beginid = 0;
    private ListView listView;
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x2333:
                    setAdapter();
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_prefecturemy, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getCityList();
        setrefrseh();
    }


    private void initView() {
        listView = (ListView) getView().findViewById(R.id.get_city_bbs_listView);
        scroll = (PullToRefreshScrollView) getView().findViewById(R.id.scroll);
    }



    public void getCityList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<CityBBSBean> call = apiStores.getBBSList("1",String.valueOf(beginid));

        call.enqueue(new Callback<CityBBSBean>() {
            @Override
            public void onResponse(Call<CityBBSBean> call, Response<CityBBSBean> response) {
                list.addAll(response.body().getData());
                beginid = Integer.parseInt(list.get(list.size()-1).getMid());
                setAdapter();  //异步请求结束后，设置适配器
            }

            @Override
            public void onFailure(Call<CityBBSBean> call, Throwable t) {
                Log.e("err", t.toString());
            }

        });
    }


    private boolean setAdapter() {
        if (getBBSListAdapter != null) {
            getBBSListAdapter.notifyDataSetChanged();
        } else {
            getBBSListAdapter = new GetBBSListAdapter(mContext, list, listView, new ListRefreshCallBack() {
                @Override
                public void success() {

//                    ToastUtils.makeShortToast(context,"成功");
                }

                @Override
                public void error() {

                }
            });
            listView.setAdapter(getBBSListAdapter);
        }
        return  true;
    }

    private void setrefrseh() {
        //设置可上拉刷新和下拉刷新
        scroll.setMode(PullToRefreshBase.Mode.BOTH);

        //设置刷新时显示的文本
        ILoadingLayout startLayout = scroll.getLoadingLayoutProxy(true, false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = scroll.getLoadingLayoutProxy(false, true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");


        scroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new LoadDataAsyncTask(context, 1).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Log.e("refesh","refesh");
                new LoadDataAsyncTask(context, 2).execute();
            }
        });

    }

    /**
     * 异步下载任务
     */
    private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private Context mainActivity;
        private int judge;

        public LoadDataAsyncTask(Context mainActivity, int judge) {
            this.mainActivity = mainActivity;
            this.judge = judge;
        }

        @Override
        protected String doInBackground(Void... params){
            try {
                if (judge==1) {
                    handle.sendEmptyMessage(0x2333);
                }else {
                    getCityList();
                }
                Log.e("refesh","refesh2");
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
                Log.e("refesh","refesh3");
                scroll.onRefreshComplete();//刷新完成
                return;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

}
