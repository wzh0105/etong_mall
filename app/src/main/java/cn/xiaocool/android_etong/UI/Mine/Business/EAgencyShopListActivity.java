package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.EAgencyListAdapter;
import cn.xiaocool.android_etong.bean.business.EAgencyShopBean;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
/**
 * Created by wzh on 2016/11/20.
 */

public class EAgencyShopListActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rlBack;
    private ListView shopList;
    private Context context;
    private EAgencyListAdapter eAgencyListAdapter;
    private List<EAgencyShopBean.DataBean> beanList;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case CommunalInterfaces.GET_MY_AGENCY_SHOP_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject jsonObject1;
                            EAgencyShopBean.DataBean dataBean;
                            for (int i = 0;i<jsonArray.length();i++){
                                jsonObject1 = (JSONObject) jsonArray.get(i);
                                dataBean = new EAgencyShopBean.DataBean();
                                dataBean.setId(jsonObject1.getString("id"));
                                dataBean.setUid(jsonObject1.getString("uid"));
                                dataBean.setShopname(jsonObject1.getString("shopname"));
                                dataBean.setIslocal(jsonObject1.getString("islocal"));
                                dataBean.setLevel(jsonObject1.getString("level"));
                                dataBean.setVip(jsonObject1.getString("vip"));
                                dataBean.setPhoto(jsonObject1.getString("photo"));
                                dataBean.setType(jsonObject1.getString("type"));
                                dataBean.setCity(jsonObject1.getString("city"));
                                dataBean.setAddress(jsonObject1.getString("address"));
                                dataBean.setIdcard(jsonObject1.getString("idcard"));
                                dataBean.setBusinesslicense(jsonObject1.getString("businesslicense"));
                                dataBean.setContactphone(jsonObject1.getString("contactphone"));
                                dataBean.setId_positive_pic(jsonObject1.getString("id_positive_pic"));
                                dataBean.setId_opposite_pic(jsonObject1.getString("id_opposite_pic"));
                                dataBean.setLicense_pic(jsonObject1.getString("license_pic"));
                                dataBean.setState(jsonObject1.getString("state"));
                                dataBean.setCreate_time(jsonObject1.getString("create_time"));
                                beanList.add(dataBean);
                            }
                            eAgencyListAdapter = new EAgencyListAdapter(context,beanList);
                            shopList.setAdapter(eAgencyListAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_agency_shoplist);
        context = this;
        initView();
        if (NetUtil.isConnnected(context)){
            new ShopRequest(context,handler).getMyAgencyShopList("607");
        }
    }

    private void initView() {
        beanList = new ArrayList<>();
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        shopList = (ListView) findViewById(R.id.e_agency_shop_listView);
        TextView tvTitle =  (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("代理店铺列表");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }

    }
}
