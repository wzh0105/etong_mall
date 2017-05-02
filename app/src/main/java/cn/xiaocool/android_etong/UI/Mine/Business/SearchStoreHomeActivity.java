package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/12/10.
 */
public class SearchStoreHomeActivity extends Activity implements View.OnClickListener {
    private TextView tvSearch;
    private EditText etSearch;
    private Context context;
    private List<PendingPayment.DataBean> dataBeans;
    private PendingPaymentAdapter pendingPaymentAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEARCH_ORDER:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            dataBeans.clear();
                            JSONObject jsonObject1;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.e("length=", String.valueOf(jsonArray.length()));
                                jsonObject1 = jsonArray.getJSONObject(i);
                                PendingPayment.DataBean dataBean = new PendingPayment.DataBean();
                                dataBean.setOrder_num(jsonObject1.getString("order_num"));
                                Log.e("order_num=", jsonObject1.getString("order_num"));
                                dataBean.setGid(jsonObject1.getString("gid"));
                                dataBean.setGoodsname(jsonObject1.getString("goodsname"));
                                dataBean.setPicture(jsonObject1.getString("picture"));
                                dataBean.setId(jsonObject1.getString("id"));
                                dataBean.setState(jsonObject1.getString("state"));
//                                    dataBean.setType(jsonObject1.getString("type"));
//                                    dataBean.setPeoplename(jsonObject1.getString("peoplename"));
                                dataBean.setMobile(jsonObject1.getString("mobile"));
                                dataBean.setPrice(jsonObject1.getString("price"));
                                dataBean.setAddress(jsonObject1.getString("address"));
                                dataBean.setNumber(jsonObject1.getString("number"));
                                dataBean.setMoney(jsonObject1.getString("money"));
                                Log.e("username=", jsonObject1.getString("username"));
                                dataBean.setUsername(jsonObject1.getString("username"));
                                dataBean.setTime(jsonObject1.getString("time"));
                                dataBeans.add(dataBean);
                            }
                            if (pendingPaymentAdapter != null) {
                                pendingPaymentAdapter.notifyDataSetChanged();
                            } else {
                                pendingPaymentAdapter = new PendingPaymentAdapter(context, dataBeans);
                                listView.setAdapter(pendingPaymentAdapter);
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
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_home_search);
        context = this;
        initView();
    }

    private void initView() {
        tvSearch = (TextView) findViewById(R.id.my_order_search_tv);
        tvSearch.setOnClickListener(this);
        etSearch = (EditText) findViewById(R.id.my_order_et_search);
        listView = (ListView) findViewById(R.id.search_my_order_list);
        dataBeans = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_order_search_tv:
                String searchContent = etSearch.getText().toString();
                if (NetUtil.isConnnected(context)) {
//                    new ShopRequest(this, handler).searchOrder(searchContent);
                }
        }
    }

}
