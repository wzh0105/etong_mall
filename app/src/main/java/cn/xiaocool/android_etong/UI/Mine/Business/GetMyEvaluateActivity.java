package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.ShopGetMyEvaluateAdapter;
import cn.xiaocool.android_etong.bean.business.ShopMyEvaluateBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;


/**
 * Created by wzh on 2016/10/26.
 */

public class GetMyEvaluateActivity extends Activity implements View.OnClickListener {

    private Context context;
    private RelativeLayout btnBack;
    private ListView listView;
    private List<ShopMyEvaluateBean.DataBean> dataBeanList;
    private ShopGetMyEvaluateAdapter shopGetMyEvaluateAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SHOP_GET_EVALUATE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                ShopMyEvaluateBean.DataBean dataBean = new ShopMyEvaluateBean.DataBean();
                                dataBean.setId(dataObject.getString("id"));
                                dataBean.setOrderid(dataObject.getString("orderid"));
                                dataBean.setReceivetype(dataObject.getString("receivetype"));
                                dataBean.setContent(dataObject.getString("content"));
                                dataBean.setPhoto(dataObject.getString("photo"));
                                dataBean.setAttitudescore(dataObject.getString("attitudescore"));
                                dataBean.setFinishscore(dataObject.getString("finishscore"));
                                dataBean.setEffectscore(dataObject.getString("effectscore"));
                                dataBean.setAdd_time(dataObject.getString("add_time"));
                                dataBean.setStatus(dataObject.getString("status"));
                                JSONArray jsonArray1 = dataObject.getJSONArray("goods_info");
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                                dataBean.setGoodsname(jsonObject1.getString("goodsname"));
                                dataBean.setPicture(jsonObject1.getString("picture"));
                                dataBeanList.add(dataBean);
                            }
                            shopGetMyEvaluateAdapter = new ShopGetMyEvaluateAdapter(context, dataBeanList);
                            listView.setAdapter(shopGetMyEvaluateAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myshop_get_evaluate);
        context = this;
        initView();
        if (NetUtil.isConnnected(context)){
            new ShopRequest(context,handler).getMyEvaluate();
        }
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("收到的评价");
        btnBack = (RelativeLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.myShop_my_evaluate_list);
        dataBeanList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
