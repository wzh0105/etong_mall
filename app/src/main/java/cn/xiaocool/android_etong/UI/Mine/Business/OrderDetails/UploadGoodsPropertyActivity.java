package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.UploadGoodsPropertyAdapter;
import cn.xiaocool.android_etong.bean.Shop.Property;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/8/18.
 */
public class UploadGoodsPropertyActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private Button btn_sure;
    private ListView list_property;
    private List<Property.DataBean> dataBeans;
    private UploadGoodsPropertyAdapter uploadGoodsPropertyAdapter;
    private String type;
    private String goodsid;
    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CommunalInterfaces.GetGoodPropertyList:
                    Log.e("getmyshop", "getmyshop");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if(NetUtil.isConnnected(context)){
                        try {
                            String status = jsonObject.getString("status");
                            String data = jsonObject.getString("data");
                            if (status.equals("success")){
                                dataBeans.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                               for (int i = 0; i<jsonArray.length();i++){
                                   JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                   Property.DataBean dataBean = new Property.DataBean();
                                   dataBean.setId(jsonObject1.getString("id"));
                                   dataBean.setTermid(jsonObject1.getString("termid"));
                                   dataBean.setName(jsonObject1.getString("name"));
                                   dataBean.setListorder(jsonObject1.getString("listorder"));
                                   JSONArray jsonArray1 = jsonObject1.getJSONArray("plist");
                                   List<Property.DataBean.PlistBean> plistBeans = new ArrayList<>();
                                   for (int j = 0 ;j<jsonArray1.length();j++){
                                       Property.DataBean.PlistBean plistBean = new Property.DataBean.PlistBean();
                                       JSONObject object = jsonArray1.getJSONObject(j);
                                       plistBean.setId(object.getString("id"));
                                       plistBean.setTypeid(object.getString("typeid"));
                                       plistBean.setName(object.getString("name"));
                                       plistBean.setListorder(object.getString("listorder"));
                                       plistBeans.add(plistBean);
                                   }
                                   dataBean.setPlist(plistBeans);
                                   dataBeans.add(dataBean);
                               }if (uploadGoodsPropertyAdapter!=null){
                                    uploadGoodsPropertyAdapter.notifyDataSetChanged();
                                }else {
                                    uploadGoodsPropertyAdapter = new UploadGoodsPropertyAdapter(context,dataBeans,goodsid,type);
                                    list_property.setAdapter(uploadGoodsPropertyAdapter);
                                    setListViewHeightBasedOnChildren(list_property);
                                }
                            }else {
                                Toast.makeText(context,jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload_goods_property);
        context = this;
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        goodsid = intent.getStringExtra("goodsid");
        initView();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).GetGoodPropertyList(type);
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        list_property = (ListView) findViewById(R.id.list_property);
        dataBeans = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
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
