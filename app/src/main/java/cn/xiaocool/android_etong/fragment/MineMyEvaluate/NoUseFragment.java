package cn.xiaocool.android_etong.fragment.MineMyEvaluate;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.NoUseAdapter;
import cn.xiaocool.android_etong.bean.Mine.PendingPayment;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/8/7.
 */
public class NoUseFragment extends Fragment {
    private ListView list_goods;
    private RelativeLayout rl_back;
    private Context context;
    private Bitmap bitmap=null;

    private List<PendingPayment.DataBean> dataBeans;
    private NoUseAdapter noUseAdapter=null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
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
                                dataBean.setDeliverytype(jsonObject1.getString("deliverytype"));
                                dataBean.setTracking(jsonObject1.getString("tracking"));
                                dataBeans.add(dataBean);
                            }
                            if (noUseAdapter != null) {
                                noUseAdapter.notifyDataSetChanged();
                            } else {
                                noUseAdapter = new NoUseAdapter(context, dataBeans);
                                list_goods.setAdapter(noUseAdapter);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
    }


    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pending_used, container, false);
        return view;
    }
    private void initView() {
        dataBeans = new ArrayList<>();
        list_goods = (ListView)getView().findViewById(R.id.list_goods_no_use);
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).getshoppingorderlist("&state=2","&deliverytype=1");
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }
}
