package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.app.text.City;
import cn.xiaocool.android_etong.app.text.District;
import cn.xiaocool.android_etong.app.text.Provence;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/10/23.
 */

public class ChangeGoodTypeActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.business_btn_save)
    Button businessBtnSave;
    private Context context;
    private List<Provence> provences;
    private Provence provence;
    ArrayAdapter<Provence> adapter01;
    ArrayAdapter<City> adapter02;
    ArrayAdapter<District> adapter03;
    private City city;
    private String show;
    private Spinner spinner01, spinner02, spinner03;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == CommunalInterfaces.GET_GOODS_TYPE_LIST) {
                try {
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    Log.e("111", "111");
                    JSONArray array_provence = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array_provence.length(); i++) {
                        Provence p = new Provence();
                        JSONObject object_provence = array_provence.getJSONObject(i);
                        p.setName(object_provence.getString("name"));
                        Log.e("省", p.getName());
                        JSONArray array_city = object_provence.getJSONArray("childlist");
                        List<City> l_c = new ArrayList<City>();
                        for (int j = 0; j < array_city.length(); j++) {
                            City c = new City();
                            JSONObject object_city = array_city.getJSONObject(j);
                            c.setName(object_city.getString("name"));
                            Log.e("市", c.getName());
                            JSONArray array_district = object_city.getJSONArray("childlist");
                            List<District> l_d = new ArrayList<District>();
                            for (int l = 0; l < array_district.length(); l++) {
                                District d = new District();
                                JSONObject object_district = array_district.getJSONObject(l);
                                d.setName(object_district.getString("name"));
                                Log.e("区", d.getName());
                                l_d.add(d);
                            }
                            c.setDistricts(l_d);
                            l_c.add(c);
                        }
                        p.setCitys(l_c);
                        provences.add(p);
                        adapter01 = new ArrayAdapter<Provence>(context, R.layout.spinner_list_item, provences);
//                            adapter01.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner01.setAdapter(adapter01);
                        spinner01.setSelection(0, true);

                        adapter02 = new ArrayAdapter<City>(context,
                                R.layout.spinner_list_item, provences.get(0)
                                .getCitys());
                        spinner02.setAdapter(adapter02);
                        spinner02.setSelection(0, true);

                        adapter03 = new ArrayAdapter<District>(context,
                                R.layout.spinner_list_item, provences.get(0)
                                .getCitys().get(0).getDistricts());
                        spinner03.setAdapter(adapter03);
                        spinner03.setSelection(0, true);
                        Log.e("list", String.valueOf(provences.size()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == CommunalInterfaces.CHANGE_GOOD_INTRO_ITEM) {

                JSONObject jsonObject = (JSONObject) msg.obj;
                try {
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(context, "修改成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    private String suffix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_good_type);
        context = this;
        ButterKnife.bind(this);
        spinner01 = (Spinner) findViewById(R.id.spinner01);
        spinner02 = (Spinner) findViewById(R.id.spinner02);
        spinner03 = (Spinner) findViewById(R.id.spinner03);
        Intent intent = getIntent();
//        infor = intent.getStringExtra("changeInfor");
        suffix = intent.getStringExtra("webAddress");
        provences = new ArrayList<>();
        initTypeListDatas();
        setOnClick();
    }


    private void setOnClick() {
        spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                provence = provences.get(position);
                adapter02 = new ArrayAdapter<City>(context,
                        R.layout.spinner_list_item, provences.get(
                        position).getCitys());
                spinner02.setAdapter(adapter02);
                spinner02.setSelection(0, true);
//				visable = 1;
//				//设置自动触发
//				spinner02.performClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spinner02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                city = provence.getCitys().get(position);
                adapter03 = new ArrayAdapter<District>(context,
                        R.layout.spinner_list_item, provence.getCitys().get(position)
                        .getDistricts());
                spinner03.setAdapter(adapter03);
//                spinner03.setSelection(0, true);
//				if(visable==1){
//
//				}else {
//					spinner03.performClick();
//				}
//				visable=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spinner03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show = spinner03.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initTypeListDatas() {
        if (NetUtil.isConnnected(this)) {
            new ShopRequest(context, handler).getGoodstypeList();
        }
    }

    @OnClick({R.id.rl_back, R.id.business_btn_save, })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.business_btn_save:
                if (NetUtil.isConnnected(this)) {
                    new MineRequest(this, handler).changeGoodIntroItem(suffix, show);
                } else {
                    Toast.makeText(this, "无网络！请稍后重试！", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

}