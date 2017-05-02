package cn.xiaocool.android_etong.UI.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.EverydayChoicenessAdapter;
import cn.xiaocool.android_etong.adapter.ZeroAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.bean.HomePage.ZeroBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;

/**
 * Created by SJL on 2016/8/22.
 */
public class ZeroActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private List<ZeroBean.ZeroDataBean> zeroDataBeanList;
    private Context context;
    private ZeroAdapter zeroAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CommunalInterfaces.GET_NEW_ARRIVAL:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                ZeroBean.ZeroDataBean zeroDataBean = new ZeroBean.ZeroDataBean();
                                zeroDataBean.setId(dataObject.getString("id"));
                                zeroDataBean.setArtno(dataObject.getString("artno"));
                                zeroDataBean.setShopid(dataObject.getString("shopid"));
                                zeroDataBean.setBrand(dataObject.getString("brand"));
                                zeroDataBean.setGoodsname(dataObject.getString("goodsname"));
                                zeroDataBean.setAdtitle(dataObject.getString("adtitle"));
                                zeroDataBean.setOprice(dataObject.getString("oprice"));
                                zeroDataBean.setPrice(dataObject.getString("price"));
                                zeroDataBean.setUnit(dataObject.getString("unit"));
                                zeroDataBean.setDescription(dataObject.getString("description"));
                                zeroDataBean.setPicture(dataObject.getString("picture"));
                                zeroDataBean.setShowid(dataObject.getString("showid"));
                                zeroDataBean.setAddress(dataObject.getString("address"));
                                zeroDataBean.setFreight(dataObject.getString("freight"));
                                zeroDataBean.setPays(dataObject.getString("pays"));
                                zeroDataBean.setRacking(dataObject.getString("racking"));
                                zeroDataBean.setRecommend(dataObject.getString("recommend"));

                                JSONObject jsonObject1 = dataObject.getJSONObject("shop_name");
                                zeroDataBean.setShopname(jsonObject1.getString("shopname"));

                                zeroDataBean.setSales(dataObject.getString("sales"));
                                zeroDataBean.setPaynum(dataObject.getString("paynum"));


                                zeroDataBeanList.add(zeroDataBean);
                            }
                            zeroAdapter = new ZeroAdapter(context, zeroDataBeanList);
                            listView.setAdapter(zeroAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zero);
        context = this;
        initView();
        new HomeRequest(this, handler).getNewArrival("&recommend=6");

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView_everyday_choiceness);
        zeroDataBeanList = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("0元购");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
