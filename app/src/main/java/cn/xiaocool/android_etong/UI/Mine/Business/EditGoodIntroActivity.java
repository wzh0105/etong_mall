package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/20.
 */
public class EditGoodIntroActivity extends Activity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {
    private Context mContext;
    private String picName, title, description, type, brand, artNo, standard, price, oprice, freight, inventory, goodDetails, shipAddress, content;
    private RelativeLayout btnBack, btnStandard, btnInventory;
    private TextView tvTitle, tvType, tvBrand, tvArtNo, tvStandard, tvPrice, tvOprice, tvFreight, tvInventory,
            etGoodDetails, etShipAddress;
    private RelativeLayout rlPic;
    private int state = 0;
    private String picStr;

    private String picname1, picname2, picname3, picname4, picname5;
    private String pic_path1, pic_path2, pic_path3, pic_path4, pic_path5;

    private SliderLayout mDemoSlider;

    private HashMap<String, String> url_maps = new HashMap<String, String>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case CommunalInterfaces.CHANGE_GOOD_INTRO:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            picName = object.getString("picture");
                            Log.e("aaaabbbb", picName);
                            title = object.getString("goodsname");
                            description = object.getString("description");
//                            type = object.getString("type");
                            brand = object.getString("brand");
                            artNo = object.getString("artno");
                            standard = object.getString("unit");
                            price = object.getString("price");
                            oprice = object.getString("oprice");
                            freight = object.getString("freight");
//                            Log.e("aaaabbbb", picName);
                            goodDetails = object.getString("description");
                            shipAddress = object.getString("address");
                            content = object.getString("content");
                            inventory = object.getString("inventory");
                            Log.e("result", picName + title + type + brand + artNo + standard + price
                                    + oprice + freight + inventory + goodDetails + shipAddress);
                            tvTitle.setText(title);
                            tvType.setText(type);
                            tvBrand.setText(brand);
                            tvArtNo.setText(artNo);
                            tvStandard.setText(standard);
                            tvPrice.setText(price);
                            tvOprice.setText(oprice);
                            tvFreight.setText(freight);
                            tvInventory.setText(inventory);
                            etGoodDetails.setText(goodDetails);
                            etShipAddress.setText(shipAddress);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private String goodId;
    private RelativeLayout rlTitle;
    private LinearLayout llType;
    private RelativeLayout rlBrand;
    private RelativeLayout rlArtNo, rlPrice, rlOPrice, rlFreight, rlDetails, rlAddress, rlIntro;

    private TextView et_xiangqing;
    private RelativeLayout rl_carousel_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_change_goods_intro);
        mContext = this;
        ButterKnife.bind(this);
        initView();
        Intent intent = getIntent();
        goodId = intent.getStringExtra("goodId");
        Log.e("goodidis", goodId);
        if (NetUtil.isConnnected(this)) {
            new MineRequest(this, handler).changeGoodIntro(goodId);
            Log.e("next", "next");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetUtil.isConnnected(this)) {
            new MineRequest(this, handler).changeGoodIntro(goodId);
        }
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.rl_back);
        btnBack.setOnClickListener(this);
        rlPic = (RelativeLayout) findViewById(R.id.rl_carousel_pic);
        rlPic.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.business_tv_good_name);
        tvTitle.setOnClickListener(this);
        tvType = (TextView) findViewById(R.id.business_et_change_type2);
        tvType.setOnClickListener(this);
        tvBrand = (TextView) findViewById(R.id.business_tv_good_brand);
        tvBrand.setOnClickListener(this);
        tvArtNo = (TextView) findViewById(R.id.business_tv_good_art_no);
        tvArtNo.setOnClickListener(this);
        btnStandard = (RelativeLayout) findViewById(R.id.business_tv_good_standard);
        btnStandard.setOnClickListener(this);
        tvPrice = (TextView) findViewById(R.id.editGood_et_price);
        tvPrice.setOnClickListener(this);
        tvOprice = (TextView) findViewById(R.id.editGood_et_oprice);
        tvOprice.setOnClickListener(this);
        tvInventory = (TextView) findViewById(R.id.editGood_et_inventory);
        tvFreight = (TextView) findViewById(R.id.business_tv_good_freight);
        tvFreight.setOnClickListener(this);
        btnInventory = (RelativeLayout) findViewById(R.id.edit_good_inventory);
        btnInventory.setOnClickListener(this);
        etGoodDetails = (TextView) findViewById(R.id.editGood_et_details);
        etGoodDetails.setOnClickListener(this);
        etShipAddress = (TextView) findViewById(R.id.business_tv_good_ship_address);
        etShipAddress.setOnClickListener(this);
        rlTitle = (RelativeLayout) findViewById(R.id.edit_good_title);
        rlTitle.setOnClickListener(this);//标题
        llType = (LinearLayout) findViewById(R.id.edit_good_type);
        llType.setOnClickListener(this);//分类
        rlBrand = (RelativeLayout) findViewById(R.id.edit_good_brand);
        rlBrand.setOnClickListener(this);//品牌
        rlArtNo = (RelativeLayout) findViewById(R.id.edit_good_artNo);
        rlArtNo.setOnClickListener(this);//货号
        rlPrice = (RelativeLayout) findViewById(R.id.edit_good_price);
        rlPrice.setOnClickListener(this);//现价
        rlOPrice = (RelativeLayout) findViewById(R.id.edit_good_oPrice);
        rlOPrice.setOnClickListener(this);//原价
        rlFreight = (RelativeLayout) findViewById(R.id.edit_good_freight);
        rlFreight.setOnClickListener(this);//运费
        rlDetails = (RelativeLayout) findViewById(R.id.edit_good_details);
        rlDetails.setOnClickListener(this);//详情
        rlAddress = (RelativeLayout) findViewById(R.id.edit_good_address);
        rlAddress.setOnClickListener(this);//发货地址
        rlIntro = (RelativeLayout) findViewById(R.id.edit_good_intro);
        rlIntro.setOnClickListener(this);//宝贝简介
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
                picname1 = picname1 + ".jpg";
            }
            if (picname2 == null || picname2.equals("")) {
                picname2 = "";
            } else {
                picname2 = picname2 + ".jpg";
            }
            if (picname3 == null || picname3.equals("")) {
                picname3 = "";
            } else {
                picname3 = picname3 + ".jpg";
            }
            if (picname4 == null || picname4.equals("")) {
                picname4 = "";
            } else {
                picname4 = picname4 + ".jpg";
            }
            if (picname5 == null || picname5.equals("")) {
                picname5 = "";
            } else {
                picname5 = picname5 + ".jpg";
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
                url_maps.put("图 1", WebAddress.GETAVATAR + picname1);
            }
            if (picname2.equals("")) {

            } else {
                url_maps.put("图 2", WebAddress.GETAVATAR + picname2);
            }
            if (picname3.equals("")) {

            } else {
                url_maps.put("图 3", WebAddress.GETAVATAR + picname3);
            }
            if (picname4.equals("")) {

            } else {
                url_maps.put("图 4", WebAddress.GETAVATAR + picname4);
            }
            if (picname5.equals("")) {

            } else {
                url_maps.put("图 5", WebAddress.GETAVATAR + picname5);
            }
            mDemoSlider.setVisibility(View.VISIBLE);
            for (String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(mContext);
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

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
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_carousel_pic:
                Intent intent = new Intent();
                intent.setClass(this, EditGoodLookPicActivity.class);
                intent.putExtra("goodId", goodId);
                if (picName != null) {
                    intent.putExtra("picName", picName);
                } else {
                    intent.putExtra("picName", "");
                }
//                Log.e("pic name is", picName);
                startActivity(intent);
                break;
            case R.id.edit_good_title:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        title, "a=UpdateGoodsName&id=" + goodId + "&goodsname=");
                break;
            case R.id.edit_good_intro:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        description, "a=UpdateGoodsDescription&id=" + goodId + "&description=");
                break;
            case R.id.edit_good_type:
                IntentUtils.changeInforIntent(this, ChangeGoodTypeActivity.class,
                        "", "a=UpdateGoodsType&id=" + goodId + "&type=");//分类
                break;
            case R.id.edit_good_brand:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        brand, "a=UpdateGoodsBand" + goodId + "&band=");
                break;
            case R.id.edit_good_artNo:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        artNo, "&a=UpdateGoodsArtno" + goodId + "&artno=");
                break;
            case R.id.business_tv_good_standard:
                Intent standardIntent = new Intent();
                standardIntent.setClass(this, EditStandardItemActivity.class);//跳转修改商品规格
                standardIntent.putExtra("goodId", goodId);
                startActivity(standardIntent);
                break;
            case R.id.edit_good_oPrice:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        oprice, "a=UpdateGoodsOPrice&id=" + goodId + "&oprice=");
                break;
            case R.id.edit_good_price:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        price, "a=UpdateGoodsPrice&id=" + goodId + "&price=");
                break;
            case R.id.edit_good_freight:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        freight, "a=UpdateGoodsFreight&id=" + goodId + "&freight=");
                break;
            case R.id.edit_good_inventory:
//                Intent inventoryIntent = new Intent();
//                inventoryIntent.setClass(this, UploadInventoryActivity.class);//跳转修改库存
//                inventoryIntent.putExtra("goodId", goodId);
//                startActivity(inventoryIntent);
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        inventory, "a=UpdateStock&goodsid=" + goodId);
                break;
            case R.id.edit_good_details:
//                IntentUtils.changeInforIntent(this, ChangeGoodDetailsActivity.class,
//                        content, "a=UpdateGoodsDescription&id=" + goodId + "&content=");
                Intent intent1 = new Intent();
                intent1.putExtra("changeInfor", content);
                intent1.putExtra("webAddress", "a=UpdateGoodsDescription&id=" + goodId + "&content=");
                if (goodId != null) {

                    intent1.putExtra("goodid", goodId);
                } else {
                    intent1.putExtra("goodid", "");

                }
                intent1.setClass(EditGoodIntroActivity.this, ChangeGoodDetailsActivity.class);
                startActivity(intent1);
                break;
            case R.id.edit_good_address:
                IntentUtils.changeInforIntent(this, ChangeGoodInforActivity.class,
                        shipAddress, "a=UpdateGoodsDescription&id=" + goodId + "&address=");
                break;
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Intent intent2 = new Intent();
        intent2.setClass(mContext, CarouselPicActivity.class);
        startActivityForResult(intent2, 1);
        return;
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
}
