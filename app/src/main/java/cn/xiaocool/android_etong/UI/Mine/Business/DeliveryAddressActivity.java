package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.AddressInfo;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.bean.business.LocationService;
import cn.xiaocool.android_etong.db.sp.AddressDB;
import cn.xiaocool.android_etong.util.KeyBoardUtils;

/**
 * Created by 潘 on 2016/7/21.
 */
public class DeliveryAddressActivity extends Activity implements View.OnClickListener {

    private Context context;
    private String deliveryAddress,phone,name;
    private EditText et_change_infor,et_customer_phone,et_customer_name;
    private RelativeLayout rl_back,rl_sure;
    private String judge = "0";
    private LocationService locationService;

    private List<AddressInfo> address = new ArrayList<AddressInfo>();
    private AddressDB addressDB;
    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delivery_address);
        context = this;
        userInfo = new UserInfo();
        userInfo.readData(context);
        initview();
        Intent intent = getIntent();
        judge = intent.getStringExtra("judge");
        Log.e("second",judge);
        deliveryAddress = intent.getStringExtra("deliveryaddress");
        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
//        deliveryAddress = userInfo.getUserAddr();
//        phone = userInfo.getUserPhone();
//        name = userInfo.getUserName();
        if (judge.equals("1")){
            et_change_infor.setText(deliveryAddress);
            et_customer_phone.setText(phone);
            et_customer_name.setText(name);
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = et_change_infor.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(et_change_infor, 300);

    }

    private void initview() {
        et_change_infor = (EditText) findViewById(R.id.et_change_infor);
        et_customer_phone = (EditText) findViewById(R.id.et_customer_phone);
        et_customer_name = (EditText) findViewById(R.id.et_customer_name);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        rl_sure = (RelativeLayout) findViewById(R.id.rl_sure);
        rl_sure.setOnClickListener(this);

        addressDB = AddressDB.getInstance(getBaseContext());
        address = addressDB.queryAddress();

        if (address!=null){
            for (int i = 0 ; i < address.size() ; i++){
                if (address.get(i).isStatus()){
                    et_customer_phone.setText(address.get(i).getPhone());
                    et_customer_name.setText(address.get(i).getName());
                    et_change_infor.setText(address.get(i).getProvinces() + " " + address.get(i).getStreet());
                }
            }
        }else {
            activityStart();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                deliveryAddress = et_change_infor.getText().toString();
                phone = et_customer_phone.getText().toString();
                name = et_customer_name.getText().toString();
                judge = "1";
                Intent intent1 = new Intent();
                intent1.putExtra("deliveryaddress1",deliveryAddress);
                intent1.putExtra("judge",judge);
                intent1.putExtra("phone",phone);
                intent1.putExtra("name",name);
                Log.e("deliveryaddress=",deliveryAddress);
                setResult(RESULT_OK,intent1);
                finish();
                break;
            case R.id.rl_sure:
                deliveryAddress = et_change_infor.getText().toString();
                phone = et_customer_phone.getText().toString();
                name = et_customer_name.getText().toString();
                judge = "1";
                Intent intent = new Intent();
                intent.putExtra("deliveryaddress1",deliveryAddress);
                intent.putExtra("judge",judge);
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                Log.e("deliveryaddress=",deliveryAddress);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            deliveryAddress = et_change_infor.getText().toString();
            judge = "1";
            Intent intent1 = new Intent();
            intent1.putExtra("deliveryaddress1",deliveryAddress);
            intent1.putExtra("judge",judge);
            intent1.putExtra("phone",phone);
            intent1.putExtra("name",name);
            setResult(RESULT_OK,intent1);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected  void  activityStart(){
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
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
//        // -----------location config ------------
//        locationService = ((cn.xiaocool.android_etong.view.etongApplaction) getApplication()).locationService;
//        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
//        locationService.registerListener(mListener);
//        //注册监听
//        int type = getIntent().getIntExtra("from", 0);
//        if (type == 0) {
//            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        } else if (type == 1) {
//            locationService.setLocationOption(locationService.getOption());
//        }
//        locationService.start();// 定位SDK
//                    // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
    }



    /*****
     * @see copy funtion to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
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
                if (TextUtils.isEmpty(deliveryAddress)){
                    et_change_infor.setText(location.getAddrStr());
                    // 切换后将EditText光标置于末尾
                    CharSequence charSequence = et_change_infor.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                    KeyBoardUtils.showKeyBoardByTime(et_change_infor, 300);
                }
                locationService.stop();
            }
        }
    };

}
