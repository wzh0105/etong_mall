package cn.xiaocool.android_etong.UI.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.NewArrivalAdapter;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;

/**
 * Created by wzh on 2016/7/24.
 */
public class NewArrivalActivity extends Activity implements View.OnClickListener {
    private GridView gridView;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private Context context;
    private NewArrivalAdapter newArrivalAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_NEW_ARRIVAL:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    Log.e("00","0x00");
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                NewArrivalBean.NewArrivalDataBean newArrivalDataBean = new NewArrivalBean.NewArrivalDataBean();
                                newArrivalDataBean.setId(dataObject.getString("id"));
                                newArrivalDataBean.setArtno(dataObject.getString("artno"));
                                newArrivalDataBean.setShopid(dataObject.getString("shopid"));
                                newArrivalDataBean.setBrand(dataObject.getString("brand"));
                                newArrivalDataBean.setGoodsname(dataObject.getString("goodsname"));
                                newArrivalDataBean.setAdtitle(dataObject.getString("adtitle"));
                                newArrivalDataBean.setOprice(dataObject.getString("oprice"));
                                newArrivalDataBean.setPrice(dataObject.getString("price"));
                                newArrivalDataBean.setUnit(dataObject.getString("unit"));
                                newArrivalDataBean.setDescription(dataObject.getString("description"));
                                newArrivalDataBean.setPicture(dataObject.getString("picture"));
                                newArrivalDataBean.setShowid(dataObject.getString("showid"));
                                newArrivalDataBean.setAddress(dataObject.getString("address"));
                                newArrivalDataBean.setFreight(dataObject.getString("freight"));
                                newArrivalDataBean.setPays(dataObject.getString("pays"));
                                newArrivalDataBean.setRacking(dataObject.getString("racking"));
                                newArrivalDataBean.setRecommend(dataObject.getString("recommend"));


                                JSONObject jsonObject1 = dataObject.getJSONObject("shop_name");
                                newArrivalDataBean.setShopname(jsonObject1.getString("shopname"));

                                newArrivalDataBean.setSales(dataObject.getString("sales"));
                                newArrivalDataBean.setPayNum(dataObject.getString("paynum"));


                                newArrivalDataBeanList.add(newArrivalDataBean);
                            }
                            newArrivalAdapter = new NewArrivalAdapter(context, newArrivalDataBeanList);
                            gridView.setAdapter(newArrivalAdapter);
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
        setContentView(R.layout.new_arrival);
        context = this;
        initView();
        new HomeRequest(this, handler).getNewArrival("&recommend=1");
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView_new_arrival);
        newArrivalDataBeanList = new ArrayList<>();
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("新品上市");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
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
