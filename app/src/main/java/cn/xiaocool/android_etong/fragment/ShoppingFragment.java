package cn.xiaocool.android_etong.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.UI.ShoppingCart.StatementActivity;
import cn.xiaocool.android_etong.adapter.StoreAdapter;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class ShoppingFragment extends BaseFragment implements View.OnClickListener {
    private Context context;
    private Button btn_statement;
    private TextView tx_shopping_price;
    private double amount = 0;
    private int size = 0;
    private String pro_id = "", pro_name = "";
    private RelativeLayout ry_line;
    private CheckBox cb_all_select;
    private ListView list_Shopping_Cart;
    private StoreAdapter storeAdapter;
    private List<ShoppingCart_StoreName.DataBean> dataBeans;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_SHOPPING_CART:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            dataBeans.clear();
                            Log.e("success", "getshoppingcart");
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            int length = jsonarray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject json = (JSONObject) jsonarray.get(i);
                                ShoppingCart_StoreName.DataBean store = new ShoppingCart_StoreName.DataBean();
                                store.setShopname(json.getString("shopname"));
                                store.setShopid(json.getString("shopid"));
                                JSONArray jsonArray = json.getJSONArray("goodslist");
                                List<ShoppingCart_StoreName.DataBean.GoodslistBean> goodses = new ArrayList<>();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    pro_id = "";
                                    pro_name = "";
                                    JSONObject jsonObject2 = (JSONObject) jsonArray.get(j);
                                    ShoppingCart_StoreName.DataBean.GoodslistBean good = new ShoppingCart_StoreName.DataBean.GoodslistBean();
                                    good.setId(jsonObject2.getString("id"));
                                    good.setGid(jsonObject2.getString("gid"));
                                    good.setGoodsname(jsonObject2.getString("goodsname"));
                                    good.setPicture(jsonObject2.getString("picture"));
                                    good.setDescription(jsonObject2.getString("description"));
                                    good.setOprice(jsonObject2.getString("oprice"));
                                    good.setPrice(jsonObject2.getString("price"));
                                    good.setNumber(jsonObject2.getString("number"));
                                    good.setTime(jsonObject2.getString("time"));
                                    JSONArray jsonArray1 = jsonObject2.getJSONArray("property");
                                    for (int a = 0; a < jsonArray1.length(); a++) {
                                        JSONObject jsonObject1 = jsonArray1.getJSONObject(a);
                                        pro_id = pro_id + jsonObject1.getString("proid") + ",";
                                        if (jsonObject1.getString("proid").equals("0")) {

                                        } else {
                                            JSONArray jsonArray2 = jsonObject1.getJSONArray("propert_list");
                                            JSONObject jsonObject3 = jsonArray2.getJSONObject(0);
                                            pro_name = pro_name + jsonObject3.getString("typename") + ":" + jsonObject3.getString("propertyname") + ";";
                                        }

                                    }
                                    if (!TextUtils.isEmpty(pro_id)) {
                                        pro_id = pro_id.substring(0, pro_id.length() - 1);
                                        Log.e("pro_id=", pro_id);
                                        good.setProid(pro_id);
                                    }
                                    Log.e("pro_name=", pro_name);
                                    good.setProname(pro_name);
                                    goodses.add(good);
                                }
                                store.setGoodslist(goodses);
                                dataBeans.add(store);
                            }
                            storeAdapter = new StoreAdapter(ShoppingFragment.this, dataBeans);
                            list_Shopping_Cart.setAdapter(storeAdapter);
                            fixListViewHeight(list_Shopping_Cart);
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.EDIT_SHOPPING_CART:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "edit_shoppingcart");
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.DELETE_SHOPPING_CART:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "delete_shoppingcart");
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置状态栏高度
//        ry_line = (RelativeLayout) getView().findViewById(R.id.lin);
//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
//        linearParams.height = getStatusBarHeight(context);
//        ry_line.setLayoutParams(linearParams);
//        MyApp myApp = (MyApp)getActivity().getApplication();
//        myApp.setHandler(handler);
        initView();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).GetShoppingCart();
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        dataBeans = new ArrayList<>();
        list_Shopping_Cart = (ListView) getView().findViewById(R.id.list_shopping_cart);
        tx_shopping_price = (TextView) getView().findViewById(R.id.tx_shopping_price);
        cb_all_select = (CheckBox) getView().findViewById(R.id.cb_all_select);
        cb_all_select.setOnClickListener(this);
        btn_statement = (Button) getView().findViewById(R.id.btn_statement);
        btn_statement.setOnClickListener(this);
        initShoppingCart();
        initShoppingCart1();
    }

    private void initShoppingCart() {
        IntentFilter filter = new IntentFilter(GoodsDetailActivity.action);
        context.registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.e("data=", intent.getExtras().getString("data"));
            if (NetUtil.isConnnected(context)) {
                new MainRequest(context, handler).GetShoppingCart();
            } else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initShoppingCart1() {
        IntentFilter filter = new IntentFilter(StatementActivity.action);
        context.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_all_select:
                boolean flag = cb_all_select.isChecked();
                for (int i = 0; i < storeAdapter.getSelect().size(); i++) {
                    storeAdapter.getSelect().set(i, flag);
                    for (int j = 0; j < storeAdapter.getPAdapter(i).getSelect().size(); j++) {
                        storeAdapter.getPAdapter(i).getSelect().set(j, flag);
                    }
                }
                updateAmount();
                storeAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_statement:
                getStatementObject();
                if (size != 0) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("databeans", (Serializable) getStatementObject());
                    intent.putExtras(bundle);
                    intent.putExtra("amount",amount);
                    intent.setClass(context, StatementActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "请选择结算商品", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public List<ShoppingCart_StoreName.DataBean> getStatementObject() {
        size = 0;
        List<ShoppingCart_StoreName.DataBean> statementObject = new ArrayList<>();
        for (int i = 0; i < storeAdapter.getSelect().size(); i++) {
            ShoppingCart_StoreName.DataBean shoppingCart_storeName = new ShoppingCart_StoreName.DataBean();
            List<ShoppingCart_StoreName.DataBean.GoodslistBean> goodses = new ArrayList<>();
            for (int j = 0; j < storeAdapter.getPAdapter(i).getSelect().size(); j++) {
                if (storeAdapter.getPAdapterList().get(i).getSelect().get(j)) {
                    goodses.add(dataBeans.get(i).getGoodslist().get(j));
                    size++;
                }
            }
            if (goodses.size() != 0) {
                shoppingCart_storeName.setGoodslist(goodses);
                shoppingCart_storeName.setShopname(dataBeans.get(i).getShopname());
                statementObject.add(shoppingCart_storeName);
            }
        }
        return statementObject;
    }

    public void checkAll(boolean checked) {
        cb_all_select.setChecked(checked);
    }

    public void updateAmount() {
        amount = 0;
        tx_shopping_price.setText("￥");
        for (int i = 0; i < dataBeans.size(); i++) {
            for (int j = 0; j < dataBeans.get(i).getGoodslist().size(); j++) {
                if (storeAdapter.getPAdapter(i).getSelect().get(j)) {
                    amount +=Double.valueOf(dataBeans.get(i).getGoodslist().get(j).getPrice())
                            * Integer.valueOf(dataBeans.get(i).getGoodslist().get(j).getNumber());
                }
            }
        }
        double amountt;


        if (amount != 0) {
            amountt = amount;
            DecimalFormat df =new DecimalFormat("#.##");
            String result = df.format(amountt);
            amount = Double.valueOf(result);
            tx_shopping_price.setText("￥" +amount);
        }
    }

    public void setGoodsNumber(int storePosition, int goodsPosition, String number) {
        dataBeans.get(storePosition).getGoodslist().get(goodsPosition).setNumber(number);
        Log.e("success", number);
    }

    public void removePosition(int storePosition) {
        dataBeans.remove(storePosition);
        storeAdapter = new StoreAdapter(ShoppingFragment.this, dataBeans);
        list_Shopping_Cart.setAdapter(storeAdapter);
        fixListViewHeight(list_Shopping_Cart);
    }

    public void setUpdate() {
        for (int i = 0; i < dataBeans.size(); i++) {
            for (int j = 0; j < dataBeans.get(i).getGoodslist().size(); j++) {
                new MainRequest(context, handler).EditShoppingCart(dataBeans.get(i).getGoodslist().get(j).getGid(), dataBeans.get(i).getGoodslist().get(j).getNumber());
            }
        }
    }

    public void DeleteDate(String goodsid) {
        if (NetUtil.isConnnected(context)) {
            Log.e("ready", "delete_shopping_cart");
            new MainRequest(context, handler).DeleteShoppingCart(goodsid);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
