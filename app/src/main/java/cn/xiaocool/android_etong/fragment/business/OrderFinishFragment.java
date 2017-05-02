package cn.xiaocool.android_etong.fragment.business;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.SellerOrderAdapter;
import cn.xiaocool.android_etong.bean.business.SellerOrderBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/17.
 */
public class OrderFinishFragment extends Fragment implements View.OnClickListener {

    private ListView list_goods;
    private Context context;
    private String islocal="";
    private List<SellerOrderBean.DataBean> dataBeans;
    private SellerOrderAdapter sellerOrderAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_SELLER_ORDER_LIST:
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
                                SellerOrderBean.DataBean dataBean = new SellerOrderBean.DataBean();
                                dataBean.setOrder_num(jsonObject1.getString("order_num"));
                                Log.e("order_num=", jsonObject1.getString("order_num"));
                                dataBean.setGid(jsonObject1.getString("gid"));
                                dataBean.setGoodsname(jsonObject1.getString("goodsname"));
                                dataBean.setPicture(jsonObject1.getString("picture"));
                                dataBean.setId(jsonObject1.getString("id"));
                                dataBean.setState(jsonObject1.getString("state"));
                                dataBean.setTime(jsonObject1.getString("time"));
//                                    dataBean.setType(jsonObject1.getString("type"));
//                                    dataBean.setPeoplename(jsonObject1.getString("peoplename"));
                                dataBean.setMobile(jsonObject1.getString("mobile"));
                                dataBean.setPrice(jsonObject1.getString("price"));
                                dataBean.setAddress(jsonObject1.getString("address"));
                                dataBean.setNumber(jsonObject1.getString("number"));
                                dataBean.setMoney(jsonObject1.getString("money"));
                                dataBean.setUsername(jsonObject1.getString("username"));
                                dataBeans.add(dataBean);
                            }
                            if (sellerOrderAdapter != null) {
                                sellerOrderAdapter.notifyDataSetChanged();
                            } else {
                                sellerOrderAdapter = new SellerOrderAdapter(context, dataBeans,islocal);
                                list_goods.setAdapter(sellerOrderAdapter);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private String shopId;


    private void initView() {
        dataBeans = new ArrayList<>();
        list_goods = (ListView) getView().findViewById(R.id.seller_order_list_finish);
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getSellerOrderList(shopId,"&state=4");
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_order_finish, container, false);
        Bundle bundle = this.getArguments();
        shopId = bundle.getString("shopId");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getSellerOrderList(shopId,"&state=4");
        }
    }

    @Override
    public void onClick(View v) {

    }
}
