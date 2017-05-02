package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.PendingPaymentAdapter;
import cn.xiaocool.android_etong.bean.Mine.PendingPayment;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/8/2.
 */
public class PendingConfirmActivity extends Activity implements View.OnClickListener {
    private ListView list_goods;
    private RelativeLayout rl_back;
    private Context context;
    private List<PendingPayment.DataBean> dataBeans;
    private PendingPaymentAdapter pendingPaymentAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_SHOPPING_ORDER_LIST:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            dataBeans.clear();
                            JSONObject jsonObject1;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.e("length=", String.valueOf(jsonArray.length()));
                                jsonObject1 = jsonArray.getJSONObject(i);
                                if (jsonObject1.getString("state").equals("3")) {
                                    PendingPayment.DataBean dataBean = new PendingPayment.DataBean();
                                    dataBean.setOrder_num(jsonObject1.getString("order_num"));
                                    Log.e("order_num=", jsonObject1.getString("order_num"));
                                    dataBean.setGid(jsonObject1.getString("gid"));
                                    dataBean.setGoodsname(jsonObject1.getString("goodsname"));
                                    dataBean.setPicture(jsonObject1.getString("picture"));
                                    dataBean.setId(jsonObject1.getString("id"));
//                                    dataBean.setType(jsonObject1.getString("type"));
//                                    dataBean.setPeoplename(jsonObject1.getString("peoplename"));
                                    dataBean.setMobile(jsonObject1.getString("mobile"));
                                    dataBean.setPrice(jsonObject1.getString("price"));
                                    dataBean.setAddress(jsonObject1.getString("address"));
                                    dataBean.setNumber(jsonObject1.getString("number"));
                                    dataBean.setMoney(jsonObject1.getString("money"));
                                    Log.e("money=", jsonObject1.getString("username"));
                                    dataBean.setUsername(jsonObject1.getString("username"));
                                    dataBeans.add(dataBean);
                                }
                            }
                            if (pendingPaymentAdapter != null) {
                                pendingPaymentAdapter.notifyDataSetChanged();
                            } else {
                                pendingPaymentAdapter = new PendingPaymentAdapter(context, dataBeans);
                                list_goods.setAdapter(pendingPaymentAdapter);
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pending_comfirm);
        context = this;
        initView();
    }

    private void initView() {
        dataBeans = new ArrayList<>();
        list_goods = (ListView) findViewById(R.id.list_goods);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getshoppingorderlist("","");
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
