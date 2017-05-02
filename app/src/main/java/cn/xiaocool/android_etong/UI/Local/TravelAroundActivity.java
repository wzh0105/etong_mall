package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.TravelAroundAdapter;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/8/7.
 */
public class TravelAroundActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private ListView list_travel_around;
    private Context context;
    private String city;
    private TravelAroundAdapter travelAroundAdapter;
    private List<Local> locals;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GET_SHOP_LIST:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            locals.clear();
                            Log.e("success", "加载数据");
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String shopid = object.getString("id");
                                String shopname = object.getString("shopname");
                                String photo = object.getString("photo");
                                String levle = object.getString("level");
                                String address = object.getString("address");
                                String state1 = object.getString("state");
                                JSONArray jsonArray1 = object.getJSONArray("goodslist");
                                Log.e("succees","店铺数据加载");
                                for (int j = 0 ; j<jsonArray1.length();j++){
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                    Local local = new Local();
                                    local.setShopid(shopid);
                                    local.setShopname(shopname);
                                    local.setPhoto(photo);
                                    local.setLevel(levle);
                                    local.setAddress(address);
                                    local.setState(state1);
                                    local.setId(jsonObject1.getString("id"));
                                    local.setGoodsname(jsonObject1.getString("goodsname"));
                                    local.setPicture(jsonObject1.getString("picture"));
                                    local.setDescription(jsonObject1.getString("description"));
                                    local.setPrice(jsonObject1.getString("price"));
                                    local.setOprice(jsonObject1.getString("oprice"));
                                    local.setFreight(jsonObject1.getString("freight"));
                                    locals.add(local);
                                    Log.e("succees", "商品数据加载");
                                }
                            }if (travelAroundAdapter!=null){
                                Log.e("success","设置适配器");
                                travelAroundAdapter.notifyDataSetChanged();
                            }else {
                                Log.e("success","设置适配器");
                                travelAroundAdapter = new TravelAroundAdapter(context,locals);
                                list_travel_around.setAdapter(travelAroundAdapter);
                                setListViewHeightBasedOnChildren(list_travel_around);
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
        setContentView(R.layout.activity_travel_around);
        context = this;
        Intent intent = new Intent();
        city = intent.getStringExtra("city");
        initView();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).GetShopList(city,"6");
        }else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        list_travel_around = (ListView)findViewById(R.id.list_travel_around);
        locals = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }
    /*
  解决scrollview下listview显示不全
*/
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
