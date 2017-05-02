package cn.xiaocool.android_etong.UI.Local;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.LocalAdapter;
import cn.xiaocool.android_etong.adapter.RankingAdapter;
import cn.xiaocool.android_etong.bean.json.Ranking;
import cn.xiaocool.android_etong.dao.ApiStores;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.view.list.PullToRefreshBase;
import cn.xiaocool.android_etong.view.list.PullToRefreshListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;

public class AllStoreOrGoodsActivity extends AppCompatActivity {

    private PullToRefreshListView list_all;
    private ListView listView;
    private RankingAdapter rankingAdapter;
    private LocalAdapter localAdapter;
    private Context context;
    private List<Ranking.DataBean> rankList;
    private List<Local> locals;
    private String beginid = "0";
    private String type;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.IsLike:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            locals.clear();
                            Log.e("success", "加载数据");
                            int length = 2;
                            if (jsonArray.length() < 3) {
                                length = jsonArray.length();
                            }
                            for (int i = 0; i < length; i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Local local = new Local();
                                local.setShopid(jsonObject1.getString("shopid"));
                                local.setId(jsonObject1.getString("id"));
                                local.setGoodsname(jsonObject1.getString("goodsname"));
                                local.setPicture(jsonObject1.getString("picture"));
                                local.setDescription(jsonObject1.getString("description"));
                                local.setPrice(jsonObject1.getString("price"));
                                local.setOprice(jsonObject1.getString("oprice"));
                                local.setFreight(jsonObject1.getString("freight"));
                                locals.add(local);
                                Log.e("succees", "商品数据加载");
                            }
                            if (localAdapter != null) {
                                Log.e("success", "设置适配器");
                                localAdapter.notifyDataSetChanged();
                            } else {
                                Log.e("success", "设置适配器");
                                localAdapter = new LocalAdapter(context, locals);
                                listView.setAdapter(localAdapter);
                            }
                        } else {
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
        setContentView(R.layout.activity_all_store_or_goods);
        context = this;
        rankList = new ArrayList<>();
        locals = new ArrayList<>();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(beginid);
    }

    private void initData(String id) {

        type = getIntent().getStringExtra("type");
        if (type.equals("rank")){
            getRanks(id);
        }else {
            getGoods(id);
        }


    }

    private void getGoods(String id) {
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).IsLike();
        }else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRanks(final String id) {
        String city  = getIntent().getStringExtra("city");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<Ranking> call = apiStores.GetLocalShopList(city,"0","1",id);

        call.enqueue(new Callback<Ranking>() {
            @Override
            public void onResponse(Call<Ranking> call, Response<Ranking> response) {
//                list_all.onRefreshComplete();
                if (id.equals("0")){
                    rankList.clear();
                }

                rankList.addAll(response.body().getData());
                setAdapter();  //异步请求结束后，设置适配器
            }

            @Override
            public void onFailure(Call<Ranking> call, Throwable t) {
                Log.e("err", t.toString());
            }

        });
    }

    private void initView() {
        list_all = (PullToRefreshListView) findViewById(R.id.list_all);
        listView = list_all.getRefreshableView();
        list_all.setPullLoadEnabled(true);
        list_all.setScrollLoadEnabled(true);
        //设置下拉上拉监听
         list_all.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
             @Override
             public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                 beginid = "0";
                 initData(beginid);
                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         list_all.onPullDownRefreshComplete();
                     }
                 }, 1000);
             }

             @Override
             public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                 if (type.equals("rank")){
                     beginid  = rankList.size()>=7?rankList.get(rankList.size()-1).getId():"0";
                 }else {
                     beginid = locals.size()>=7?locals.get(locals.size()-1).getId():"0";
                 }
                 initData(beginid);
                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         list_all.onPullUpRefreshComplete();
                     }
                 }, 1000);
             }
         });
        findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean setAdapter() {
        if (rankingAdapter != null) {
            rankingAdapter.notifyDataSetChanged();
        } else {
            rankingAdapter = new RankingAdapter(context, rankList);
            listView.setAdapter(rankingAdapter);
        }
        return  true;
    }
}
