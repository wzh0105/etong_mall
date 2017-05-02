package cn.xiaocool.android_etong.UI.ShoppingCart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.DeliveryAddressActivity;
import cn.xiaocool.android_etong.adapter.StatementAdapter;
import cn.xiaocool.android_etong.bean.AddressInfo;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.db.sp.AddressDB;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;

/**
 * Created by 潘 on 2016/8/25.
 */
public class StatementActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private double amount;
    private TextView tv_goods_price_total,tx_comfirm_order;
    private LinearLayout ll_address;
    private List<AddressInfo> address = new ArrayList<AddressInfo>();
    private AddressDB addressDB;
    private TextView et_change_infor,et_customer_phone,et_customer_name,tv_judge;
    private ListView list_statement;
    private String judge = "0";
    private String deliveryAddress = "", phone = "", name = "";
    private LinearLayout ll_delivery_address;
    private List<ShoppingCart_StoreName.DataBean> dataBeans;
    private StatementAdapter statementAdapter;
    public static final String action = "jason.broadcast1.action";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.BOOKING_SHOPPING:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        String data = jsonObject.getString("data");
                        if (status.equals("success")) {
                            Log.e("结算成功","");
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.DELETE_SHOPPING_CART:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        String data = jsonObject1.getString("data");
                        if (status.equals("success")) {
                            Log.e("删除成功","");
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_statement);
        context = this;
        dataBeans = (List<ShoppingCart_StoreName.DataBean>) getIntent().getSerializableExtra("databeans");
        amount = getIntent().getDoubleExtra("amount",1);
        Log.e("amount", String.valueOf(amount));
        initView();
        statementAdapter = new StatementAdapter(context,dataBeans);
        list_statement.setAdapter(statementAdapter);
        fixListViewHeight(list_statement);

    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_goods_price_total = (TextView) findViewById(R.id.tv_goods_price_total);
        tv_goods_price_total.setText(String.valueOf(amount));
        tx_comfirm_order = (TextView) findViewById(R.id.tx_comfirm_order);
        tx_comfirm_order.setOnClickListener(this);
        list_statement = (ListView) findViewById(R.id.list_statement);
        ll_delivery_address = (LinearLayout) findViewById(R.id.ll_delivery_address);
        ll_delivery_address.setOnClickListener(this);
        et_change_infor = (TextView) findViewById(R.id.et_change_infor);
        et_customer_phone = (TextView) findViewById(R.id.et_customer_phone);
        et_customer_name = (TextView) findViewById(R.id.et_customer_name);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        tv_judge = (TextView) findViewById(R.id.tv_judge);

        addressDB = AddressDB.getInstance(getBaseContext());
        address = addressDB.queryAddress();

        if (address!=null){
            for (int i = 0 ; i < address.size() ; i++){
                if (address.get(i).isStatus()){
                    ll_address.setVisibility(View.VISIBLE);
                    et_customer_phone.setText(address.get(i).getPhone());
                    et_customer_name.setText(address.get(i).getName());
                    et_change_infor.setText(address.get(i).getProvinces() + " " + address.get(i).getStreet());
                    tv_judge.setText("更改收货地址");
                    phone = address.get(i).getPhone();
                    name = address.get(i).getName();
                    deliveryAddress =address.get(i).getProvinces() + " " + address.get(i).getStreet();
                    Log.e("设置完毕","收货地址");
                }
            }
        }else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tx_comfirm_order:
                statement();
                Intent intent1 = new Intent(action);
                intent1.putExtra("data", "yes i am data");
                sendBroadcast(intent1);
                finish();
                break;
            case R.id.ll_delivery_address:
                Intent intent = new Intent();
                intent.setClass(context, DeliveryAddressActivity.class);
                intent.putExtra("judge", judge);
                intent.putExtra("deliveryaddress", deliveryAddress);
                intent.putExtra("phone", phone);
                intent.putExtra("name", name);
                startActivityForResult(intent, 1);
                break;
        }
    }

    public void statement(){
                if (!TextUtils.isEmpty(deliveryAddress)) {
                    if (phone.length() == 11) {
                        if (!TextUtils.isEmpty(name)) {
                            for (int i = 0 ; i<dataBeans.size() ; i++) {
                                for (int j = 0; j < dataBeans.get(i).getGoodslist().size(); j++) {
                                    String id = dataBeans.get(i).getGoodslist().get(j).getGid();
                                    id = id.trim();
                                    Log.e("goodsid=",id);
                                    new MainRequest(context, handler).bookingshopping(id, name,
                                            deliveryAddress, dataBeans.get(i).getGoodslist().get(j).getNumber(), phone, "",
                                            String.valueOf(Double.valueOf(dataBeans.get(i).getGoodslist().get(j).getPrice())
                                                    * Integer.valueOf(dataBeans.get(i).getGoodslist().get(j).getNumber()))
                                            , dataBeans.get(i).getGoodslist().get(j).getProid());
                                    new MainRequest(context,handler).DeleteShoppingCart(id);
                                }
                            }
                            dataBeans.clear();
                            statementAdapter = new StatementAdapter(context,dataBeans);
                            list_statement.setAdapter(statementAdapter);
                            fixListViewHeight(list_statement);
                            tv_goods_price_total.setText("0");

                        } else {
                            Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "请输入地址", Toast.LENGTH_SHORT).show();
                }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e("success", "delivery");
            deliveryAddress = data.getStringExtra("deliveryaddress1");
            judge = data.getStringExtra("judge");
            Log.e("first",judge);
            phone = data.getStringExtra("phone");
            name = data.getStringExtra("name");
            Log.e("deliveryaddress=", deliveryAddress);
            if (!phone.isEmpty()&&!name.isEmpty()&&!deliveryAddress.isEmpty()){
                ll_address.setVisibility(View.VISIBLE);
                et_customer_phone.setText(phone);
                et_customer_name.setText(name);
                et_change_infor.setText(deliveryAddress);
                tv_judge.setText("更改收货地址");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            judge = "0";
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void fixListViewHeight(ListView listView) {

        // 如果没有设置数据适配器，则ListView没有子项，返回。

        ListAdapter listAdapter = listView.getAdapter();

        int totalHeight = 0;

        if (listAdapter == null) {

            return;

        }

        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {

            View listViewItem = listAdapter.getView(index, null, listView);

            // 计算子项View 的宽高

            listViewItem.measure(0, 0);

            // 计算所有子项的高度和

            totalHeight += listViewItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        // listView.getDividerHeight()获取子项间分隔符的高度

        // params.height设置ListView完全显示需要的高度

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }

}
