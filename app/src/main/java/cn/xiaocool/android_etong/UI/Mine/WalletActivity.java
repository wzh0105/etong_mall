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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.ModifyBankActivity;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 潘 on 2016/9/30.
 */

public class WalletActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private String name , touxiang;
    private Button btn_withdraw;//btn_youhuiquan,btn_xianjinhongbao,
    private LinearLayout ll_bank,ll_withdraw;
    private CircleImageView img_mine_head;
    private TextView tx_mine_name,tv_yue,tv_bankinfo;
    private Context context;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GetUserBankInfo:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            if (object.getString("bank").equals("null")|| TextUtils.isEmpty(object.getString("bank"))){
                                tv_bankinfo.setText("设置银行卡信息");
                            }else{
                                //生成银行 /n 银行卡后四位
                                String showbank=object.getString("bank");
                                showbank = showbank +'\n';
                                int banknolenth = object.getString("bankno").length();
                                String bankno =object.getString("bankno").substring(banknolenth-4,banknolenth);
                                showbank = showbank+"****" + bankno;
                                tv_bankinfo.setText(showbank);
                            }
//                            if (object.getString("bankno").equals("null")|| TextUtils.isEmpty(object.getString("bankno"))){
//                                tv_withdraw2.setText("未绑定");
//                            }
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GetMyWallet:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")){
                            progressDialog.dismiss();
                            Log.e("success","GetMyWallet");
                            JSONObject object = jsonObject1.getJSONObject("data");
                            if (object.getString("availablemoney").equals("null")||object.getString("availablemoney")==null){
                                tv_yue.setText("余额 0元");
                            }else {
                                tv_yue.setText("余额 "+object.getString("availablemoney")+"元");
                            }
                        }else {
                            Toast.makeText(context,jsonObject1.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private LinearLayout setPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wallet);
        context = this;
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        touxiang = intent.getStringExtra("touxiang");
        initView();
        if (NetUtil.isConnnected(context)){
            progressDialog.setMessage("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context,handler).GetMyWallet();
            new MainRequest(context,handler).GetUserBankInfo();
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        tv_bankinfo = (TextView) findViewById(R.id.ll_set_bank);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        ll_bank = (LinearLayout) findViewById(R.id.ll_bank);
        ll_bank.setOnClickListener(this);
        ll_withdraw = (LinearLayout) findViewById(R.id.ll_withdraw);
        ll_withdraw.setOnClickListener(this);
        btn_withdraw = (Button) findViewById(R.id.btn_withdraw);
        btn_withdraw.setOnClickListener(this);
//        btn_youhuiquan = (Button) findViewById(R.id.btn_youhuiquan);
//        btn_youhuiquan.setOnClickListener(this);
//        btn_xianjinhongbao = (Button) findViewById(R.id.btn_xianjinhongbao);
//        btn_xianjinhongbao.setOnClickListener(this);
        img_mine_head = (CircleImageView) findViewById(R.id.img_mine_head);
        tv_yue = (TextView) findViewById(R.id.tv_yue);
        tx_mine_name = (TextView) findViewById(R.id.tx_mine_name);
        setPass = (LinearLayout) findViewById(R.id.ll_set_pay_password);
        setPass.setOnClickListener(this);
        if (name.equals("null")||name==null||name.equals("")) {
            tx_mine_name.setText("未设置昵称");
        } else {
            tx_mine_name.setText(name);
        }
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + touxiang, img_mine_head);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
//            case R.id.btn_youhuiquan:
//                startActivity(new Intent(context, CouponActivity.class));
//                break;
//            case R.id.btn_xianjinhongbao:
////                Toast.makeText(context,"该功能正在完善",Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(context, .class));
//                break;
            case R.id.ll_bank:
                startActivity(new Intent(context, ModifyBankActivity.class));
                break;
            case R.id.btn_withdraw:
                startActivity(new Intent(context, WithdrawSelectActivity.class));
                break;
            case R.id.ll_withdraw:
                startActivity(new Intent(context, WithdrawRecordActivity.class));
                break;
            case R.id.ll_set_pay_password:
                startActivity(new Intent(context, EditPayPassActivity.class));
//                ToastUtils.makeShortToast(context,"设置支付密码功能正在开发中！");
                break;
        }
    }

    @Override
    protected void onResume() {
        if (NetUtil.isConnnected(context)){
            progressDialog.setMessage("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context,handler).GetMyWallet();
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}
