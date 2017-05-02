package cn.xiaocool.android_etong.UI.Mine.Business;

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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UploadGoodSanndard.ObtainAttributeBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;

/**
 * Created by wzh on 2016/8/2.
 */
public class UploadInventoryActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rlBack;
    private TextView tvTitle;
    private String goodID;
    private Context context;
    private List<ObtainAttributeBean.DataBean> dataBeanList;
    private List<ObtainAttributeBean.DataBean.PropertylistBean> propertylistBeanList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.OBTAIN_GOOD_ATTRIBUTE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                            JSONObject jsonObject1;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject1 = jsonArray.getJSONObject(i);
                                ObtainAttributeBean.DataBean dataBean = new ObtainAttributeBean.DataBean();
                                dataBean.setId(jsonObject1.getString("id"));
                                dataBean.setTypeid(jsonObject1.getString("typeid"));
                                dataBean.setName(jsonObject1.getString("name"));
                                JSONArray jsonArray1 = jsonObject1.getJSONArray("propertylist");
                                JSONObject jsonObject2;
                                propertylistBeanList = new ArrayList<>();
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    jsonObject2 = jsonArray1.getJSONObject(j);
                                    ObtainAttributeBean.DataBean.PropertylistBean propertylistBean =
                                            new ObtainAttributeBean.DataBean.PropertylistBean();
                                    propertylistBean.setId(jsonObject2.getString("id"));
                                    propertylistBean.setName(jsonObject2.getString("name"));
                                    Log.e("name is1122",jsonObject2.getString("name"));
                                    propertylistBeanList.add(propertylistBean);
                                }
                                dataBean.setPropertylist(propertylistBeanList);
                                dataBeanList.add(dataBean);
                            }
//                            obtainAttributeAdapter = new ObtainAttributeAdapter(context, dataBeanList);
//                            listView.setAdapter(obtainAttributeAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.obtain_attribute);
        initView();
        context = this;
        goodID = getIntent().getStringExtra("goodId");
        new ShopRequest(this, handler).obtainGoodProperty("32");
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("上传商品库存");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.upload_good_inventory_list);
        dataBeanList = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
