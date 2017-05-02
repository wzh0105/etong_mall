package cn.xiaocool.android_etong.UI.Mine.Business;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.UploadGoodsPropertyActivity;
import cn.xiaocool.android_etong.app.text.City;
import cn.xiaocool.android_etong.app.text.District;
import cn.xiaocool.android_etong.app.text.Provence;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/6.
 */
public class UploadGoodsActivity extends Activity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {
    private Context mContext;
    private SliderLayout mDemoSlider;
    private SliderLayout mDemoSlider1;
    private RelativeLayout rl_back, rl_carousel_pic, rl_goods_details, rl_slider3,rl_slider4;
    private EditText et_biaoti, et_pinpai, et_guige, et_huohao, et_yunfei, et_fahuodi;
    private TextView tx_goods_upload, tv_judge, et_xiangqing;
    private ProgressDialog progressDialog;
    private String show;
    private String shopid, shopType;
    private String picStr;
    private List<Provence> provences;
    private Provence provence;
    private City city;
    private District district;
    private static int visable = 0;
    ArrayAdapter<Provence> adapter01;
    ArrayAdapter<City> adapter02;
    ArrayAdapter<District> adapter03;
    private HashMap<String, String> url_maps = new HashMap<String, String>();
    private HashMap<String, String> url_maps1 = new HashMap<String, String>();
    private List<String> lists;
    private Spinner spinner01, spinner02, spinner03;
    private String result_data;
    private String picname1, picname2, picname3, picname4, picname5;
    private String pic_path1, pic_path2, pic_path3, pic_path4, pic_path5;
    private String biaoti, pinpai, huohao, guige, yunfei, fahuodi, xiangqing;
    private String price, oprice, inventory;
    private int state = 0;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("222", "222");
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
                            adapter01 = new ArrayAdapter<Provence>(mContext, R.layout.spinner_list_item, provences);
//                            adapter01.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner01.setAdapter(adapter01);
                            spinner01.setSelection(0, true);

                            adapter02 = new ArrayAdapter<City>(mContext,
                                    R.layout.spinner_list_item, provences.get(0)
                                    .getCitys());
                            spinner02.setAdapter(adapter02);
                            spinner02.setSelection(0, true);

                            adapter03 = new ArrayAdapter<District>(mContext,
                                    R.layout.spinner_list_item, provences.get(0)
                                    .getCitys().get(0).getDistricts());
                            spinner03.setAdapter(adapter03);
                            spinner03.setSelection(0, true);
                            Log.e("list", String.valueOf(provences.size()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.PUBLISHGOODS:
                    Log.e("success", "publish");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        String data = jsonObject.getString("data");
                        if (status.equals("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                            Log.e("success", "publish");
                            Intent intent = new Intent();
                            intent.putExtra("type", shopType);
                            intent.putExtra("goodsid", data);
                            intent.setClass(mContext, UploadGoodsPropertyActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, data,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private EditText etPrice;
    private EditText etOprice;
    private EditText etInventory;
    private EditText etIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_uploadgoods);
        mContext = this;
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        shopType = intent.getStringExtra("type");
        Log.e("shopid=", shopid);
        progressDialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
        initview();

        initDatas();
        Log.e("解析完毕", "JSON");
        setonclick();
    }

    private void initview() {
        provences = new ArrayList<>();
        spinner01 = (Spinner) findViewById(R.id.spinner01);
        spinner02 = (Spinner) findViewById(R.id.spinner02);
        spinner03 = (Spinner) findViewById(R.id.spinner03);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        rl_carousel_pic = (RelativeLayout) findViewById(R.id.rl_carousel_pic);
        rl_carousel_pic.setOnClickListener(this);
        //输入信息
        et_biaoti = (EditText) findViewById(R.id.et_biaoti);
        et_pinpai = (EditText) findViewById(R.id.et_pinpai);
        et_huohao = (EditText) findViewById(R.id.et_huohao);
        et_guige = (EditText) findViewById(R.id.et_guige);
        et_yunfei = (EditText) findViewById(R.id.et_yunfei);
        et_fahuodi = (EditText) findViewById(R.id.et_fahuodi);
        et_xiangqing = (TextView) findViewById(R.id.et_xiangqing);
        tx_goods_upload = (TextView) findViewById(R.id.tx_goods_upload);
        tx_goods_upload.setOnClickListener(this);
        etPrice = (EditText) findViewById(R.id.uploadGood_et_price);
        etOprice = (EditText) findViewById(R.id.uploadGood_et_oprice);
        etInventory = (EditText) findViewById(R.id.uploadGood_et_inventory);
        rl_goods_details = (RelativeLayout) findViewById(R.id.iv_goods_detail);
        rl_goods_details.setOnClickListener(this);
        rl_slider3 = (RelativeLayout) findViewById(R.id.rl_slider3);
        rl_slider3.setOnClickListener(this);
        rl_slider4 = (RelativeLayout) findViewById(R.id.rl_slider4);
        rl_slider4.setOnClickListener(this);
        etIntro = (EditText) findViewById(R.id.et_intro);
        //轮播图
        mDemoSlider = (SliderLayout) findViewById(R.id.slider3);
        mDemoSlider1 = (SliderLayout) findViewById(R.id.slider4);
        lists = new ArrayList<>();
    }

    private void initDatas() {
        if (NetUtil.isConnnected(this)) {
            new Thread() {
                Message msg = Message.obtain();

                @Override
                public void run() {
                    try {
                        result_data = NetUtil.getResponse(NetBaseConstant.GET_TYPE_LIST, "");
                        Log.e("announcement", result_data);
                        JSONObject jsonObject = new JSONObject(result_data);
                        msg.obj = jsonObject;
                        msg.what = 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        handler.sendMessage(msg);
                    }
                }
            }.start();
        } else {
        }
    }

    private void setonclick() {
        spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                provence = provences.get(position);
                adapter02 = new ArrayAdapter<City>(UploadGoodsActivity.this,
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
                adapter03 = new ArrayAdapter<District>(UploadGoodsActivity.this,
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
                Log.e("show=", show);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_carousel_pic:
                Intent intent = new Intent();
                intent.setClass(mContext, CarouselPicActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tx_goods_upload:
                uploads();
                break;
//            case R.id.rl_silder:
//                Intent intent2 = new Intent();
//                intent2.setClass(mContext, CarouselPicActivity.class);
//                startActivityForResult(intent2, 1);
//                break;
            case R.id.iv_goods_detail:
                Intent intent3 = new Intent();
                intent3.setClass(mContext, UploadGoodDetailsActivity.class);
                intent3.putExtra("tv_content", et_xiangqing.getText());
                startActivityForResult(intent3, 1000);
                break;
            case R.id.slider3:
                Log.e("click slider1","");
                Intent intent2 = new Intent();
                intent2.putExtra("pic_path1", pic_path1);
                intent2.putExtra("pic_path2", pic_path2);
                intent2.putExtra("pic_path3", pic_path3);
                intent2.putExtra("pic_path4", pic_path4);
                intent2.putExtra("pic_path5", pic_path5);

                intent2.putExtra("picname1", picname1);
                intent2.putExtra("picname2", picname2);
                intent2.putExtra("picname3", picname3);
                intent2.putExtra("picname4", picname4);
                intent2.putExtra("picname5", picname5);
                intent2.setClass(mContext, CarouselPicActivity.class);
                startActivityForResult(intent2, 1);
                break;
            case R.id.slider4:
                Log.e("click slider2","");
                Intent intent4 = new Intent();
                intent4.setClass(mContext, UploadGoodDetailsActivity.class);
                intent4.putExtra("tv_content", et_xiangqing.getText());
                startActivityForResult(intent4, 1000);
                break;

        }
    }

    private void uploads() {
        biaoti = et_biaoti.getText().toString();
        pinpai = et_pinpai.getText().toString();
        huohao = et_huohao.getText().toString();
        guige = et_guige.getText().toString();
        price = etPrice.getText().toString();
        oprice = etOprice.getText().toString();
        yunfei = et_yunfei.getText().toString();
        inventory = etInventory.getText().toString();
        xiangqing = et_xiangqing.getText().toString();
        fahuodi = et_fahuodi.getText().toString();
        String intro = etIntro.getText().toString();
        if (!TextUtils.isEmpty(picStr)) {
            if (state == 1) {
                if (!TextUtils.isEmpty(biaoti)) {
                    if (!TextUtils.isEmpty(pinpai)) {
                        if (!TextUtils.isEmpty(huohao)) {
                            if (!TextUtils.isEmpty(guige)) {
                                if (!TextUtils.isEmpty(price)) {
                                    if (!TextUtils.isEmpty(yunfei)) {
                                        if (!TextUtils.isEmpty(xiangqing)) {
                                            if (!TextUtils.isEmpty(fahuodi)) {
                                                if (!TextUtils.isEmpty(show)) {
                                                    if (NetUtil.isConnnected(mContext)) {
                                                        progressDialog.setMessage("正在上传");
                                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                        progressDialog.show();
                                                        Log.e("pic=", picname4 + "  " + picname5);
                                                        new MainRequest(mContext, handler).publishgoods(shopid, picname1+".jpg", picname2+".jpg", picname3+".jpg", picname4+".jpg", picname5+".jpg",
                                                                biaoti, intro, show, pinpai, huohao, guige, price, oprice, yunfei, inventory, xiangqing, fahuodi, picStr);
                                                    } else {
                                                        Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(mContext, "请选择分类", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(mContext, "请输入发货地", Toast.LENGTH_SHORT).show();
                                                et_fahuodi.requestFocus();
                                            }
                                        } else {
                                            Toast.makeText(mContext, "请输入详情", Toast.LENGTH_SHORT).show();
                                            et_xiangqing.requestFocus();
                                        }

                                    } else {
                                        Toast.makeText(mContext, "请输入运费", Toast.LENGTH_SHORT).show();
                                        et_yunfei.requestFocus();
                                    }
                                } else {
                                    Toast.makeText(mContext, "请输入现价", Toast.LENGTH_SHORT).show();
                                    etPrice.requestFocus();
                                }
                            } else {
                                Toast.makeText(mContext, "请输入规格", Toast.LENGTH_SHORT).show();
                                et_guige.requestFocus();
                            }
                        } else {
                            Toast.makeText(mContext, "请输入货号", Toast.LENGTH_SHORT).show();
                            et_huohao.requestFocus();
                        }
                    } else {
                        Toast.makeText(mContext, "请输入品牌", Toast.LENGTH_SHORT).show();
                        et_pinpai.requestFocus();
                    }
                } else {
                    Toast.makeText(mContext, "请输入标题", Toast.LENGTH_SHORT).show();
                    et_biaoti.requestFocus();
                }
            } else {
                Toast.makeText(mContext, "请传入至少一张照片", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "请设置宝贝详情的图片", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e("success", data.getStringExtra("1111"));
            state = data.getIntExtra("state", 1);
            pic_path1 = "";
            pic_path2 = "";
            pic_path3 = "";
            pic_path4 = "";
            pic_path5 = "";
            picname1 = "";
            picname2 = "";
            picname3 = "";
            picname4 = "";
            picname5 = "";

            pic_path1 = data.getStringExtra("pic_path1");
            Log.e("pic_path1=", pic_path1);
            pic_path2 = data.getStringExtra("pic_path2");
            Log.e("pic_path2=", pic_path2);
            pic_path3 = data.getStringExtra("pic_path3");
            Log.e("pic_path3=", pic_path3);
            pic_path4 = data.getStringExtra("pic_path4");
            Log.e("pic_path4=", pic_path4);
            pic_path5 = data.getStringExtra("pic_path5");
            Log.e("pic_path5=", pic_path5);
            picname1 = data.getStringExtra("picname1");
            Log.e("picname1", picname1);
            picname2 = data.getStringExtra("picname2");
            Log.e("picname2", picname2);
            picname3 = data.getStringExtra("picname3");
            Log.e("picname3", picname3);
            picname4 = data.getStringExtra("picname4");
            Log.e("picname4", picname4);
            picname5 = data.getStringExtra("picname5");
            Log.e("picname5", picname5);
            if (pic_path1 == null || pic_path1.equals("")) {
                pic_path1 = "";
                mDemoSlider.setVisibility(View.GONE);
                rl_carousel_pic.setVisibility(View.VISIBLE);
            } else {
                mDemoSlider.setVisibility(View.VISIBLE);
                rl_carousel_pic.setVisibility(View.GONE);
            }
            if (pic_path2 == null || pic_path2.equals("")) {
                pic_path2 = "";
            }
            if (pic_path3 == null || pic_path3.equals("")) {
                pic_path3 = "";
            }
            if (pic_path4 == null || pic_path4.equals("")) {
                pic_path4 = "";
            }
            if (pic_path5 == null || pic_path5.equals("")) {
                pic_path5 = "";
            }
            if (picname1 == null || picname1.equals("")) {
                picname1 = "";
            } else {
                picname1 = picname1 ;
            }
            if (picname2 == null || picname2.equals("")) {
                picname2 = "";
            } else {
                picname2 = picname2;
            }
            if (picname3 == null || picname3.equals("")) {
                picname3 = "";
            } else {
                picname3 = picname3 ;
            }
            if (picname4 == null || picname4.equals("")) {
                picname4 = "";
            } else {
                picname4 = picname4 ;
            }
            if (picname5 == null || picname5.equals("")) {
                picname5 = "";
            } else {
                picname5 = picname5 ;
            }

            initpic();

        } else if (requestCode == 1000) {
            Bundle bundle = data.getExtras();
            picStr = bundle.getString("picStr");
            if (TextUtils.isEmpty(picStr)) {
            } else {
                Log.e("picStr=", picStr);
            }
            String etString = bundle.getString("good_details");
            et_xiangqing.setText(etString);
            lists = bundle.getStringArrayList("result");
            Log.e("lists", lists.toString());
            if (!lists.toString().equals("[]")) {
                url_maps1.clear();
                mDemoSlider1.removeAllSliders();
                for (int i = 0; i < lists.size(); i++) {
                    url_maps1.put("图" + i, WebAddress.GETAVATAR + lists.get(i).toString());
                }
                mDemoSlider1.setVisibility(View.VISIBLE);
                rl_goods_details.setVisibility(View.GONE);
                for (String name : url_maps1.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(mContext);
                    textSliderView
                            .description(name)
                            .image(url_maps1.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Log.e("click slider2","");
                                    Intent intent4 = new Intent();
                                    intent4.setClass(mContext, UploadGoodDetailsActivity.class);
                                    intent4.putExtra("tv_content", et_xiangqing.getText());
                                    startActivityForResult(intent4, 1000);
                                }
                            });
                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    mDemoSlider1.addSlider(textSliderView);
                }
                mDemoSlider1.setPresetTransformer(SliderLayout.Transformer.Default);
                mDemoSlider1.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                mDemoSlider1.setCustomAnimation(new DescriptionAnimation());
                mDemoSlider1.setDuration(4000);
                mDemoSlider1.addOnPageChangeListener(this);
                mDemoSlider1.setOnClickListener(this);
            }
        }
    }

    private void initpic() {
        if (pic_path1 == null || pic_path1.equals("")) {
            return;
        } else {
            url_maps.clear();
            mDemoSlider.removeAllSliders();
//        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
//        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
//        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
//        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");
            if (picname1.equals("")) {

            } else {
                url_maps.put("图 1", WebAddress.GETAVATAR + picname1+".jpg");
            }
            if (picname2.equals("")) {

            } else {
                url_maps.put("图 2", WebAddress.GETAVATAR + picname2+".jpg");
            }
            if (picname3.equals("")) {

            } else {
                url_maps.put("图 3", WebAddress.GETAVATAR + picname3+".jpg");
            }
            if (picname4.equals("")) {

            } else {
                url_maps.put("图 4", WebAddress.GETAVATAR + picname4+".jpg");
            }
            if (picname5.equals("")) {

            } else {
                url_maps.put("图 5", WebAddress.GETAVATAR + picname5+".jpg");
            }
            mDemoSlider.setVisibility(View.VISIBLE);
            for (String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(mContext);
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                Log.e("click slider1","");
                                Intent intent2 = new Intent();
                                intent2.putExtra("pic_path1", pic_path1);
                                intent2.putExtra("pic_path2", pic_path2);
                                intent2.putExtra("pic_path3", pic_path3);
                                intent2.putExtra("pic_path4", pic_path4);
                                intent2.putExtra("pic_path5", pic_path5);

                                intent2.putExtra("picname1", picname1);
                                intent2.putExtra("picname2", picname2);
                                intent2.putExtra("picname3", picname3);
                                intent2.putExtra("picname4", picname4);
                                intent2.putExtra("picname5", picname5);
                                intent2.setClass(mContext, CarouselPicActivity.class);
                                startActivityForResult(intent2, 1);
                            }
                        });

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
            mDemoSlider.setOnClickListener(this);
        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

}


//此处为XML文件加载数据函数  调用函数可直接加载

//	public List<Provence> getProvinces() throws XmlPullParserException,
//			IOException {
//		List<Provence> provinces = null;
//		Provence province = null;
//		List<City> citys = null;
//		City city = null;
//		List<District> districts = null;
//		District district = null;
//		Resources resources = getResources();
//
//		InputStream in = resources.openRawResource(R.raw.citys_weather);
//
//		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//		XmlPullParser parser = factory.newPullParser();
//
//		parser.setInput(in, "utf-8");
//		int event = parser.getEventType();
//		while (event != XmlPullParser.END_DOCUMENT) {
//			switch (event) {
//			case XmlPullParser.START_DOCUMENT:
//				provinces = new ArrayList<Provence>();
//				break;
//			case XmlPullParser.START_TAG:
//				String tagName = parser.getName();
//				if ("p".equals(tagName)) {
//					province = new Provence();
//					citys = new ArrayList<City>();
//					int count = parser.getAttributeCount();
//					for (int i = 0; i < count; i++) {
//						String attrName = parser.getAttributeName(i);
//						String attrValue = parser.getAttributeValue(i);
//						if ("p_id".equals(attrName))
//							province.setId(attrValue);
//					}
//				}
//				if ("pn".equals(tagName)) {
//					province.setName(parser.nextText());
//				}
//				if ("c".equals(tagName)) {
//					city = new City();
//					districts = new ArrayList<District>();
//					int count = parser.getAttributeCount();
//					for (int i = 0; i < count; i++) {
//						String attrName = parser.getAttributeName(i);
//						String attrValue = parser.getAttributeValue(i);
//						if ("c_id".equals(attrName))
//							city.setId(attrValue);
//					}
//				}
//				if ("cn".equals(tagName)) {
//					city.setName(parser.nextText());
//				}
//				if ("d".equals(tagName)) {
//					district = new District();
//					int count = parser.getAttributeCount();
//					for (int i = 0; i < count; i++) {
//						String attrName = parser.getAttributeName(i);
//						String attrValue = parser.getAttributeValue(i);
//						if ("d_id".equals(attrName))
//							district.setId(attrValue);
//					}
//					district.setName(parser.nextText());
//					districts.add(district);
//				}
//				break;
//			case XmlPullParser.END_TAG:
//				if ("c".equals(parser.getName())) {
//					city.setDistricts(districts);
//					citys.add(city);
//				}
//				if ("p".equals(parser.getName())) {
//					province.setCitys(citys);
//					provinces.add(province);
//				}
//
//				break;
//
//			}
//			event = parser.next();
//
//		}
//		return provinces;
//	}
