package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
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
import cn.xiaocool.android_etong.adapter.UploadGoodItemAdapter;
import cn.xiaocool.android_etong.bean.UploadGoodSanndard.UploadStandardBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;

/**
 * Created by wzh on 2016/7/29.
 */
public class EditStandardItemActivity extends Activity implements View.OnClickListener {
    private UploadGoodItemAdapter uploadGoodItemAdapter;
    private List<UploadStandardBean.DataBean> dataBeenList;
    //    private List<UploadStandardBean.DataBean.PlistBean> plistBeenList;
    private ListView listView;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GOOD_ATTACHED_PROPERTY:
                    JSONObject jsonObject0 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject0.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray0 = jsonObject0.getJSONArray("data");
                            JSONObject jsonObject1;
                            for (int i = 0; i < jsonArray0.length(); i++) {
                                jsonObject1 = jsonArray0.getJSONObject(i);
                                UploadStandardBean.DataBean dataBean = new UploadStandardBean.DataBean();
                                dataBean.setId(jsonObject1.getString("id"));
                                dataBean.setTermid(jsonObject1.getString("termid"));
                                dataBean.setName(jsonObject1.getString("name"));
                                dataBean.setListorder(jsonObject1.getString("listorder"));
                                Log.e("this list is", dataBeenList.toString());
                                JSONArray jsonArray1 = jsonObject1.getJSONArray("plist");
                                JSONObject jsonObject2;
//                                dataBeenList.add(dataBean);
                                List<UploadStandardBean.DataBean.PlistBean> plistBeenList = new ArrayList<>();
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    jsonObject2 = jsonArray1.getJSONObject(j);
                                    UploadStandardBean.DataBean.PlistBean plistBean =
                                            new UploadStandardBean.DataBean.PlistBean();
                                    plistBean.setId(jsonObject2.getString("id"));
                                    plistBean.setTypeid(jsonObject2.getString("typeid"));
                                    plistBean.setName(jsonObject2.getString("name"));
                                    plistBean.setListorder(jsonObject2.getString("listorder"));
                                    plistBeenList.add(plistBean);
                                }
                                dataBean.setPlist(plistBeenList);
                                dataBeenList.add(dataBean);
                            }
                            uploadGoodItemAdapter = new UploadGoodItemAdapter(EditStandardItemActivity.this, dataBeenList, goodId);
                            listView.setAdapter(uploadGoodItemAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private String goodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_good_standard_list);
        goodId = getIntent().getStringExtra("goodId");
        new ShopRequest(this, handler).obtainAttachedProperty("1");
        dataBeenList = new ArrayList<>();
//        plistBeenList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.add_good_standard_listView);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("添加商品规格");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.setClass(EditStandardItemActivity.this, AddGoodAttributeActivity.class);
//                intent.putExtra("list", (Serializable) dataBeenList);
//                startActivity(intent);
//            }
//        });
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
