package cn.xiaocool.android_etong.UI.Mine;

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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.AfterSalesManagementActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.CommonProblemActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.EditStoreActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsManageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderManageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.StoreHomepageActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.TurnoverActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.UploadGoodsActivity;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.tool.zxingCode.activity.CaptureActivity;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by 潘 on 2016/6/27.
 */
public class BusinessActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back, rl_order;
    private Button btn_uploadgoods, btn_baobeiguanli, btn_dianpuguanli, btn_shouhouguanli, btn_dingdanguanli, btn_changjianwenti,
            btn_huodongbaoming, btn_caiwujiekuan;
    private String shopid, shopType, islocal;
    private TextView tx_store_name, tx_business_price1, tx_business_price2, tx_business_price3, textView6;
    private Context context;
    private ImageView img1, img2, img3, img4, img5;
    private ImageView img_store_head;
    private ProgressDialog progressDialog;
    private UserInfo userInfo;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GETMYSHOP:
                    Log.e("getmyshop", "getmyshop");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject.getString("status");
                            String data = jsonObject.getString("data");
                            if (status.equals("success")) {
                                progressDialog.dismiss();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String shopid = jsonObject1.getString("id");
                                String head = jsonObject1.getString("photo");
                                String shopname = jsonObject1.getString("shopname");
                                if (jsonObject1.getString("level").equals("0")) {
                                    img1.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img2.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img3.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img4.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);

                                } else if (jsonObject1.getString("level").equals("0.5")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_half);
                                    img2.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img3.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img4.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("1")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img3.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img4.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("1.5")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_half);
                                    img3.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img4.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("2")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_all);
                                    img3.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img4.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("2.5")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_all);
                                    img3.setBackgroundResource(R.mipmap.ic_star_half);
                                    img4.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("3")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_all);
                                    img3.setBackgroundResource(R.mipmap.ic_star_all);
                                    img4.setBackgroundResource(R.mipmap.ic_xingixng);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("3.5")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_all);
                                    img3.setBackgroundResource(R.mipmap.ic_star_all);
                                    img4.setBackgroundResource(R.mipmap.ic_star_half);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("4")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_all);
                                    img3.setBackgroundResource(R.mipmap.ic_star_all);
                                    img4.setBackgroundResource(R.mipmap.ic_star_all);
                                    img5.setBackgroundResource(R.mipmap.ic_xingixng);
                                } else if (jsonObject1.getString("level").equals("4.5")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_all);
                                    img3.setBackgroundResource(R.mipmap.ic_star_all);
                                    img4.setBackgroundResource(R.mipmap.ic_star_all);
                                    img5.setBackgroundResource(R.mipmap.ic_star_half);
                                } else if (jsonObject1.getString("level").equals("5")) {
                                    img1.setBackgroundResource(R.mipmap.ic_star_all);
                                    img2.setBackgroundResource(R.mipmap.ic_star_all);
                                    img3.setBackgroundResource(R.mipmap.ic_star_all);
                                    img4.setBackgroundResource(R.mipmap.ic_star_all);
                                    img5.setBackgroundResource(R.mipmap.ic_star_all);
                                }
                                if (TextUtils.isEmpty(jsonObject1.getString("favorite")) || !jsonObject1.getString("favorite").equals("null")) {
                                    textView6.setText("收藏:" + jsonObject1.getString("favorite") + "人");
                                } else {
                                    textView6.setText("收藏:0人");
                                }
                                shopType = jsonObject1.getString("type");
                                userInfo.setUserShopId(shopid);
                                userInfo.setUserShopName(shopname);
                                userInfo.setUserShopType(shopType);
                                userInfo.writeData(BusinessActivity.this);
                                Log.e("head=", head);
                                ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + jsonObject1.getString("photo"), img_store_head);
                                if (shopname.equals("null") || shopname == null || shopname.equals("")) {
                                    tx_store_name.setText("未设置");
                                } else {
                                    tx_store_name.setText(shopname);
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CommunalInterfaces.GetMyWallet:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            Log.e("success", "GetMyWallet");
                            JSONObject object = jsonObject1.getJSONObject("data");
                            tx_business_price1.setText("￥" + object.getString("allorder"));
                            tx_business_price2.setText(object.getString("dayorders"));
                            tx_business_price3.setText(object.getString("dayvisitor"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.VerifyShoppingCode:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject2.getString("status");
                        Intent intent = new Intent(context, ShopVerifyStatusActivity.class);

                        if (status.equals("success")) {
                            intent.putExtra("status", "1");//验证成功
                            startActivity(intent);
                        } else {
                            intent.putExtra("status", "0");//验证失败
                        }
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private Button btnWantHelp;
    private Button btnVerify;
    private EditText etVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_business);
        context = this;
        initview();
        userInfo = new UserInfo();
        userInfo.readData(this);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        islocal = intent.getStringExtra("islocal");
        Log.e("shopid=", intent.getStringExtra("shopid"));
        if (NetUtil.isConnnected(context)) {
            progressDialog.setMessage("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context, handler).getmyshop();
            new MainRequest(context, handler).GetMyWallet();
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
//        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//        Log.e("deviced","''''''''''''''''"+tm.getDeviceId());

    }

    private void initview() {
        btnWantHelp = (Button) findViewById(R.id.btn_want_help);
        btnWantHelp.setOnClickListener(this);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        rl_order = (RelativeLayout) findViewById(R.id.rl_order);
        rl_order.setOnClickListener(this);
        btn_uploadgoods = (Button) findViewById(R.id.btn_uploadgoods);
        btn_uploadgoods.setOnClickListener(this);
        btn_baobeiguanli = (Button) findViewById(R.id.btn_baobeiguanli);
        btn_baobeiguanli.setOnClickListener(this);
        btn_dianpuguanli = (Button) findViewById(R.id.btn_dianpuguanli);
        btn_dianpuguanli.setOnClickListener(this);
        btn_shouhouguanli = (Button) findViewById(R.id.btn_shouhouguanli);
        btn_shouhouguanli.setOnClickListener(this);
        btn_dingdanguanli = (Button) findViewById(R.id.btn_dingdanguanli);
        btn_dingdanguanli.setOnClickListener(this);
        btn_changjianwenti = (Button) findViewById(R.id.btn_changjianwenti);
        btn_changjianwenti.setOnClickListener(this);
        btn_huodongbaoming = (Button) findViewById(R.id.btn_huodongbaoming);
        btn_huodongbaoming.setOnClickListener(this);
        btn_caiwujiekuan = (Button) findViewById(R.id.btn_caiwujiekuan);
        btn_caiwujiekuan.setOnClickListener(this);
        img_store_head = (ImageView) findViewById(R.id.img_store);
        img_store_head.setOnClickListener(this);
        tx_store_name = (TextView) findViewById(R.id.tx_business_touxiang);
        tx_business_price1 = (TextView) findViewById(R.id.tx_business_price1);
        tx_business_price2 = (TextView) findViewById(R.id.tx_business_price2);
        tx_business_price3 = (TextView) findViewById(R.id.tx_business_price3);
        textView6 = (TextView) findViewById(R.id.textView6);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        btnVerify = (Button) findViewById(R.id.btn_store_verify_code);
        btnVerify.setOnClickListener(this);
        etVerify = (EditText) findViewById(R.id.et_store_verify_code);
        Button btnClear = (Button) findViewById(R.id.btn_store_clear);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_uploadgoods:
                Intent intent = new Intent();
                intent.putExtra("shopid", shopid);
                intent.putExtra("type", shopType);
                intent.setClass(BusinessActivity.this, UploadGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baobeiguanli:
                Intent intent2 = new Intent();
                intent2.putExtra("shopid", shopid);
                intent2.setClass(BusinessActivity.this, GoodsManageActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_dianpuguanli:
                Intent intent3 = new Intent();
                intent3.putExtra("shopid", shopid);
                intent3.setClass(BusinessActivity.this, EditStoreActivity.class);
                startActivityForResult(intent3, 1);
                break;
            case R.id.btn_shouhouguanli:
                Intent intent4 = new Intent();
                intent4.putExtra("shopid", shopid);
                intent4.setClass(BusinessActivity.this, AfterSalesManagementActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_dingdanguanli:
                Intent intent5 = new Intent();
                intent5.putExtra("shopid", shopid);
                intent5.putExtra("islocal", islocal);
                intent5.setClass(BusinessActivity.this, OrderManageActivity.class);
                startActivity(intent5);
                break;
            case R.id.rl_order:
                Intent intent15 = new Intent();
                intent15.putExtra("shopid", shopid);
                intent15.putExtra("islocal", islocal);
                intent15.setClass(BusinessActivity.this, OrderManageActivity.class);
                startActivity(intent15);
                break;
            case R.id.btn_changjianwenti:
                Intent intent6 = new Intent();
                intent6.setClass(BusinessActivity.this, CommonProblemActivity.class);
                startActivity(intent6);
                break;
            case R.id.img_store:
                Intent intent7 = new Intent();
                intent7.putExtra("shopid", shopid);
                intent7.setClass(BusinessActivity.this, StoreHomepageActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_huodongbaoming:
                Intent intent8 = new Intent();
                intent8.setClass(BusinessActivity.this, ActivityRegisterActivity.class);
                startActivity(intent8);
                break;
            case R.id.btn_caiwujiekuan:
                Intent intent9 = new Intent();
//                intent8.putExtra("shopid", shopid);
                intent9.putExtra("shopid", shopid);
                intent9.setClass(BusinessActivity.this, TurnoverActivity.class);
                startActivity(intent9);
                break;
            case R.id.btn_want_help:
//                Intent intentCall = new Intent(Intent.ACTION_DIAL);
//                Uri data = Uri.parse("tel:" + "15853503932");
//                intentCall.setData(data);
//                startActivity(intentCall);
                startActivity(new Intent(context, CaptureActivity.class));
                break;
            case R.id.btn_store_verify_code:
                String inputNum = etVerify.getText().toString();
                if (!(inputNum.length() == 0)) {
                    if (NetUtil.isConnnected(context)) {
                        new ShopRequest(context, handler).verifyShoppingCode(inputNum);
                    }
                } else {
                    ToastUtils.makeShortToast(context, "输入不能为空！");
                }
                break;
            //清空
            case R.id.btn_store_clear:
                etVerify.setText("");
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e("编辑店铺回调", "success");
            try {
                new MainRequest(context, handler).getmyshop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
