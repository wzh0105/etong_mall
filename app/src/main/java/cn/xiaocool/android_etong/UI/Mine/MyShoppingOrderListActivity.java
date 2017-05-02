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

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.OrderListAdapter;
import cn.xiaocool.android_etong.bean.business.OrderList;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/21.
 */
public class MyShoppingOrderListActivity extends Activity implements View.OnClickListener {
    private Context context;
    private ListView list_oreder;
    private RelativeLayout rl_back;
    private ArrayList<OrderList.DataBean> orderlist;
    private OrderListAdapter orderListAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GET_SHOPPING_ORDER_LIST:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            orderlist.clear();
                            Log.e("success", "getshopgoodlist");
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            Log.e("jsonArray",jsonObject.getString("data"));
                            int length = jsonarray.length();
                            JSONObject json;
                            for (int i = 0;i<length;i++){
                                json = (JSONObject) jsonarray.get(i);
                                OrderList.DataBean databean = new OrderList.DataBean();
                                String pic = json.getString("picture");
//                                String[] arraypic = pic.split("[,]");
//                                Log.e("第一张图片名称",arraypic[0]);
                                databean.setGoodsname(json.getString("goodsname"));
                                databean.setPicture(pic);
                                databean.setMoney(json.getString("money"));
                                databean.setNumber(json.getString("number"));
                                databean.setId(json.getString("id"));
                                orderlist.add(databean);
                            }
                            if(orderListAdapter!=null){
                                orderListAdapter.notifyDataSetChanged();
                            }
                                else {
                                    Log.e("sell list","is not empty");;
                                    orderListAdapter = new OrderListAdapter(context,orderlist);
                                    list_oreder.setAdapter(orderListAdapter);
                            }
                        }else{
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
        setContentView(R.layout.activity_my_shopping_order_list);
        context = this;
        initview();
        if(NetUtil.isConnnected(context)){
            new MainRequest(context,handler).getshoppingorderlist("","");
        }else {
            Toast.makeText(context, "网络不稳定", Toast.LENGTH_SHORT).show();
        }
    }

    private void initview() {
        orderlist = new ArrayList<OrderList.DataBean>();
        list_oreder = (ListView)findViewById(R.id.list_order);
        rl_back = (RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
