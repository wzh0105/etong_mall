package cn.xiaocool.android_etong.UI.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import cn.xiaocool.android_etong.adapter.ShopListAdapter;
import cn.xiaocool.android_etong.bean.Shop.ShopList;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;


/**
 * Created by 潘 on 2016/7/23.
 */
public class ShopListActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private String city;
    private ListView list_shop;
    private ArrayList<ShopList.DataBean> arrayList_Shop;
    private ShopListAdapter shopListAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GET_SHOP_LIST:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            arrayList_Shop.clear();
                            Log.e("success", "shoplist");
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            Log.e("jsonArray",jsonObject.getString("data"));
                            int length = jsonarray.length();
                            JSONObject json;
                            for (int i = 0;i<length;i++){
                                json = (JSONObject) jsonarray.get(i);
                                ShopList.DataBean databean = new ShopList.DataBean();
                                String pic = json.getString("photo");
//                                String[] arraypic = pic.split("[,]");
//                                Log.e("第一张图片名称",arraypic[0]);
                                databean.setShopname(json.getString("shopname"));
                                databean.setPhoto(pic);
                                databean.setAddress(json.getString("address"));
                                databean.setId(json.getString("id"));
                                arrayList_Shop.add(databean);
                            }
                            if(shopListAdapter!=null){
                                shopListAdapter.notifyDataSetChanged();
                            }else {
                                    shopListAdapter = new ShopListAdapter(context,arrayList_Shop);
                                    list_shop.setAdapter(shopListAdapter);
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
        setContentView(R.layout.activity_shoplist);
        context = this;
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        initview();
        initdate();
    }

    private void initview() {
        arrayList_Shop = new ArrayList<ShopList.DataBean>();
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        list_shop = (ListView) findViewById(R.id.list_shop);
    }

    private void initdate(){
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).GetShopList(city,"0");
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
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
