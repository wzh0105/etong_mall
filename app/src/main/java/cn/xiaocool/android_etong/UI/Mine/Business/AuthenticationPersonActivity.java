package cn.xiaocool.android_etong.UI.Mine.Business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.app.text.City;
import cn.xiaocool.android_etong.app.text.District;
import cn.xiaocool.android_etong.app.text.Provence;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.bean.business.LocationService;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.id;
import static cn.xiaocool.android_etong.R.id.tv_local;

/**
 * Created by 潘 on 2016/11/17.
 */

public class AuthenticationPersonActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private LinearLayout ll_shipinliutong;
    private EditText et_name, et_phone, et_id_card, et_address, et_city;
    private ImageView img_ren, img_shenfenzheng, img_zhizhao, img_yingyezhizhao,img_shipinliucheng;
    private String select = "";
    private TextView tx_next;
    private LocationService locationService;
    private int judge = 0, state1 = 0, state2 = 0, state3 = 0;
    private RadioGroup rg_type, rg_select;
    private RadioButton check_btn,rg_btn_yes,rg_btn_no,rg_btn_qiye,rg_btn_geti;
    private ProgressDialog progressDialog;
    ArrayAdapter<String> adapter01;
    private String city;
    private Spinner spinner_shop_type;
    private List<Provence> provences;
    private Provence provence;
    ArrayAdapter<Provence> adapter1;
    ArrayAdapter<City> adapter2;
    private Spinner spinner01, spinner02;
    private String[] type = new String[]{"美食", "电影", "酒店", "外卖", "生活娱乐", "周边游", "生活服务", "KTV", "手机充值"};
    private UserInfo user;
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/myheader";
    private String positive_pic, opposite_pic, licences_pic;
    private String positive_path, opposite_path, licences_path, licences;
    private String show_type = "";
    String name, id_card, phone, address, city1;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY1 = 4;
    private static final int KEY2 = 5;
    private static final int KEY3 = 6;
    private static final int KEY4 = 7;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //上传头像
                case KEY1:
                    Log.e("upavatar", "success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "positive_pic");
                            progressDialog.setMessage("正在上传身份证照片");
                            Toast.makeText(context, "头像上传成功", Toast.LENGTH_SHORT).show();
                            new MainRequest(context, handler).uploadavatar(opposite_path, KEY2);
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY2:
                    Log.e("upavatar", "success");
                    String key2 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key2);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "opposite_pic");
                            progressDialog.setMessage("正在上传营业执照照片");
                            Toast.makeText(context, "身份证上传成功", Toast.LENGTH_SHORT).show();
                            new MainRequest(context, handler).uploadavatar(licences_path, KEY3);
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY3:
                    Log.e("upavatar", "success");
                    String key3 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key3);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "license_pic");
                            progressDialog.setMessage("正在上传资料");
                            Toast.makeText(context, "营业执照上传成功", Toast.LENGTH_SHORT).show();
                            new MainRequest(context, handler).CreateShop(city1, show_type, name, phone, id_card, address, positive_pic + ".jpg", opposite_pic + ".jpg", licences_pic + ".jpg", KEY4, select);
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY4:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            progressDialog.dismiss();
                            Log.e("success", "createshop");
                            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(AuthenticationPersonActivity.this, AuditShopActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_authentication_person);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        initview();
        initdata();
        onrefrsh();
    }

    private void initview() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_id_card = (EditText) findViewById(R.id.et_id_card);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_city = (EditText) findViewById(R.id.et_city);
        ll_shipinliutong = (LinearLayout) findViewById(R.id.ll_shipinliutong);
        img_ren = (ImageView) findViewById(R.id.img_ren);
        img_ren.setOnClickListener(this);
        img_shenfenzheng = (ImageView) findViewById(R.id.img_shenfenzheng);
        img_shenfenzheng.setOnClickListener(this);
        img_zhizhao = (ImageView) findViewById(R.id.img_zhizhao);
        img_zhizhao.setOnClickListener(this);
        img_yingyezhizhao = (ImageView) findViewById(R.id.img_yingyezhizhao);
        img_yingyezhizhao.setOnClickListener(this);
        img_shipinliucheng = (ImageView) findViewById(R.id.img_shipinliucheng);
        img_shipinliucheng.setOnClickListener(this);
        rg_btn_yes = (RadioButton) findViewById(R.id.rg_btn_yes);
        rg_btn_no = (RadioButton) findViewById(R.id.rg_btn_no);
        rg_btn_qiye = (RadioButton) findViewById(R.id.rg_btn_qiye);
        rg_btn_geti = (RadioButton) findViewById(R.id.rg_btn_geti);
        rg_type = (RadioGroup) findViewById(R.id.rg_type);
        rg_type.check(R.id.rg_btn_qiye);
        rg_select = (RadioGroup) findViewById(R.id.rg_select);
        rg_select.check(R.id.rg_btn_no);
        rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==rg_btn_yes.getId()){
                    rg_select.check(R.id.rg_btn_yes);
                    rg_type.check(R.id.rg_btn_geti);
                    rg_btn_geti.setEnabled(false);
                    rg_btn_qiye.setEnabled(false);
                }else if(checkedId==rg_btn_no.getId()){
                    rg_select.check(R.id.rg_btn_no);
                    rg_btn_geti.setEnabled(true);
                    rg_btn_qiye.setEnabled(true);
                }
            }
        });
        tx_next = (TextView) findViewById(R.id.tx_next);
        tx_next.setOnClickListener(this);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        spinner_shop_type = (Spinner) findViewById(R.id.spinner_shop_type);
        adapter01 = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, type);
        spinner_shop_type.setAdapter(adapter01);
        spinner_shop_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show_type = spinner_shop_type.getSelectedItem().toString();
                Log.e("show=", show_type);
                if (show_type.equals("美食")) {
                    show_type = "1";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.VISIBLE);
                } else if (show_type.equals("电影")) {
                    show_type = "2";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.GONE);
                } else if (show_type.equals("酒店")) {
                    show_type = "3";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.GONE);
                } else if (show_type.equals("外卖")) {
                    show_type = "4";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.GONE);
                } else if (show_type.equals("生活娱乐")) {
                    show_type = "5";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.GONE);
                } else if (show_type.equals("周边游")) {
                    show_type = "6";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.GONE);
                } else if (show_type.equals("生活服务")) {
                    show_type = "7";
                    Log.e("show=", show_type);
                } else if (show_type.equals("KTV")) {
                    show_type = "8";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.GONE);
                } else if (show_type.equals("手机充值")) {
                    show_type = "9";
                    Log.e("show=", show_type);
                    ll_shipinliutong.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initdata() {
        provences = new ArrayList<>();
        spinner01 = (Spinner) findViewById(R.id.spinner01);
        spinner02 = (Spinner) findViewById(R.id.spinner02);

        try {
            provences = getProvinces();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter1 = new ArrayAdapter<Provence>(context, android.R.layout.simple_list_item_1, provences);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner01.setAdapter(adapter1);

        spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                provence = provences.get(position);
                adapter2 = new ArrayAdapter<City>(AuthenticationPersonActivity.this,
                        android.R.layout.simple_list_item_1, provences.get(
                        position).getCitys());
                spinner02.setAdapter(adapter2);
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

                city = spinner02.getSelectedItem().toString() + "市";
                Log.e("city", city);
//				if(visable==1){
//
//				}else {
//					spinner03.performClick();
//				}
//				visable=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ren:
                judge = 1;
                ShowPickDialog();
                break;
            case R.id.img_shenfenzheng:
                judge = 2;
                ShowPickDialog();
                break;
            case R.id.img_zhizhao:
                judge = 3;
                ShowPickDialog();
                break;
            case R.id.img_yingyezhizhao:
                judge = 4;
                ShowPickDialog();
                break;
            case R.id.img_shipinliucheng:
                judge = 5 ;
                ShowPickDialog();
                break;
            case R.id.tx_next:
                next();
                break;
            case R.id.rl_back:
                finish();
                break;
        }
    }

    private void next() {
        name = et_name.getText().toString();
        id_card = et_id_card.getText().toString();
        phone = et_phone.getText().toString();
        address = et_address.getText().toString();
        city1 = et_city.getText().toString();
        check_btn = (RadioButton) rg_select.findViewById(rg_select.getCheckedRadioButtonId());
        if (check_btn.getText().toString().equals("是")) {
            Log.e("是", "0");
            select = "0";
        } else {
            select = "1";
        }
        if (!city1.equals("")) {
            if (!name.equals("")) {
                if (phone.length() == 11) {
                    if (id_card.length() == 18) {
                        if (!address.equals("")) {
                                if (state1 == 1 && state2 == 1 && state3 == 1) {
                                    if (!TextUtils.isEmpty(show_type)) {
                                        if (NetUtil.isConnnected(context)) {
                                            progressDialog.setMessage("正在上传资料");
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            progressDialog.show();
                                            new MainRequest(context, handler).uploadavatar(positive_path, KEY1);
                                        } else {
                                            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "请选择一种店铺类型", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "请完善图片信息", Toast.LENGTH_SHORT).show();
                                }
                        } else {
                            Toast.makeText(context, "请输入地址", Toast.LENGTH_SHORT).show();
                            et_address.requestFocus();
                        }
                    } else {
                        Toast.makeText(context, "请输入正确身份证", Toast.LENGTH_SHORT).show();
                        et_id_card.requestFocus();
                    }
                } else {
                    Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    et_phone.requestFocus();
                }
            } else {
                Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
                et_name.requestFocus();
            }
        } else {
            Toast.makeText(context, "请输入城市", Toast.LENGTH_SHORT).show();
            et_city.requestFocus();
        }
    }

    protected void ShowPickDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("相册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFromGallery, PHOTO_REQUEST_ALBUM);

            }
        }).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(path, "newpic.jpg");
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }
                startActivityForResult(intentFromCapture, PHOTO_REQUEST_CAMERA);
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO_REQUEST_CAMERA:// 相册
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, "newpic.jpg");
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_ALBUM:// 图库
                    startPhotoZoom(data.getData());
                    break;

                case PHOTO_REQUEST_CUT: // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 86);
        intent.putExtra("aspectY", 54);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 430);
        intent.putExtra("outputY", 270);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            if (judge == 1) {
                img_ren.setImageDrawable(drawable);
                state1 = 1;
                positive_pic = "positive_pic" + user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, positive_pic, filepath);
            } else if (judge == 2) {
                img_shenfenzheng.setImageDrawable(drawable);
                state2 = 1;
                opposite_pic = "positive_pic" + user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, opposite_pic, filepath);
            } else if (judge == 3) {
                img_zhizhao.setImageDrawable(drawable);
                state3 = 1;
                licences_pic = "positive_pic" + user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, licences_pic, filepath);
            } else if (judge == 4) {
                img_yingyezhizhao.setImageDrawable(drawable);
            }else if (judge == 5) {
                img_shipinliucheng.setImageDrawable(drawable);
            }
        }
    }

    /**
     * storeImageToSDCARD 将bitmap存放到sdcard中
     */
    public void storeImageToSDCARD(Bitmap colorImage, String ImageName, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File imagefile = new File(file, ImageName + ".jpg");
        try {
            imagefile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imagefile);
            colorImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            if (judge == 1) {
                positive_path = imagefile.getPath();
            } else if (judge == 2) {
                opposite_path = imagefile.getPath();
            } else if (judge == 3) {
                licences_path = imagefile.getPath();
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //此处为XML文件加载数据函数  调用函数可直接加载

    public List<Provence> getProvinces() throws XmlPullParserException,
            IOException {
        List<Provence> provinces = null;
        Provence province = null;
        List<City> citys = null;
        City city = null;
        List<District> districts = null;
        District district = null;
        Resources resources = getResources();

        InputStream in = resources.openRawResource(R.raw.citys_weather);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(in, "utf-8");
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    provinces = new ArrayList<Provence>();
                    break;
                case XmlPullParser.START_TAG:
                    String tagName = parser.getName();
                    if ("p".equals(tagName)) {
                        province = new Provence();
                        citys = new ArrayList<City>();
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("p_id".equals(attrName))
                                province.setId(attrValue);
                        }
                    }
                    if ("pn".equals(tagName)) {
                        province.setName(parser.nextText());
                    }
                    if ("c".equals(tagName)) {
                        city = new City();
                        districts = new ArrayList<District>();
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("c_id".equals(attrName))
                                city.setId(attrValue);
                        }
                    }
                    if ("cn".equals(tagName)) {
                        city.setName(parser.nextText());
                    }
                    if ("d".equals(tagName)) {
                        district = new District();
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("d_id".equals(attrName))
                                district.setId(attrValue);
                        }
                        district.setName(parser.nextText());
                        districts.add(district);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("c".equals(parser.getName())) {
                        city.setDistricts(districts);
                        citys.add(city);
                    }
                    if ("p".equals(parser.getName())) {
                        province.setCitys(citys);
                        provinces.add(province);
                    }

                    break;

            }
            event = parser.next();

        }
        return provinces;
    }

    public void onrefrsh() {
        // -----------location config ------------
        locationService = ((cn.xiaocool.android_etong.view.etongApplaction) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
        // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    /*****
     * @see copy funtion to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.e("sb=", sb.toString());
//                tv_local.setText(location.getCity());
                et_address.setText(location.getAddrStr().toString());
                et_city.setText(location.getCity().toString());
                locationService.stop();
            }
        }
    };


}
