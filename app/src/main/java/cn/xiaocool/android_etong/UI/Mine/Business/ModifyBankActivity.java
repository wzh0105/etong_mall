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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.WithdrawActivity;
import cn.xiaocool.android_etong.UI.Mine.WithdrawApplyActivity;
import cn.xiaocool.android_etong.UI.Mine.WithdrawBankActivity;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by hzh on 2016/12/29.
 */

public class ModifyBankActivity extends Activity implements View.OnClickListener {

    private Context context;
    private RelativeLayout rl_back,rl_payWay0,rl_payWay2;
    private ImageView iv_payWay0_item,iv_payWay2_item;
    private TextView tv_withdraw1,tv_withdraw2,tv_delete;
    private Button btn_sure;
    private ProgressDialog progressDialog;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GetUserBankInfo:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            progressDialog.dismiss();
                            JSONObject object = jsonObject.getJSONObject("data");
                            if (object.getString("bank").equals("null")|| TextUtils.isEmpty(object.getString("bank"))){
                                tv_withdraw1.setText("未绑定");
                                btn_sure.setText("绑定");
                            }else{
                                //生成银行 /n 银行卡后四位
                                String showbank=object.getString("bank");
                                showbank = showbank +'\n';
                                int banknolenth = object.getString("bankno").length();
                                String bankno =object.getString("bankno").substring(banknolenth-4,banknolenth);
                                showbank = showbank+"****" + bankno;
                                tv_withdraw1.setText(showbank);
                            }
//                            if (object.getString("bankno").equals("null")|| TextUtils.isEmpty(object.getString("bankno"))){
//                                tv_withdraw2.setText("未绑定");
//                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.UpdateUserBank:
                    //判断与服务器的验证码是否一致，如果一致则提示注册成功，跳转到主界面；否则提示验证码错误
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        if (status.equals("success")) {
                            progressDialog.dismiss();
                            //实力化缓存类;
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context,"删除失败" , Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_modify_bank);
        context = this;
        initView();
        if (NetUtil.isConnnected(context)){
            progressDialog.setMessage("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context,handler).GetUserBankInfo();
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {

        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(this);
        iv_payWay0_item = (ImageView) findViewById(R.id.iv_payWay0_item);
        iv_payWay2_item = (ImageView) findViewById(R.id.iv_payWay2_item);
        tv_withdraw1 = (TextView) findViewById(R.id.tv_withdraw1);
        tv_withdraw2 = (TextView) findViewById(R.id.tv_withdraw2);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        rl_payWay0 = (RelativeLayout) findViewById(R.id.rl_payWay0);
        rl_payWay0.setOnClickListener(this);
        rl_payWay2 = (RelativeLayout) findViewById(R.id.rl_payWay2);
        rl_payWay2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_payWay0:
                iv_payWay0_item.setSelected(true);
                iv_payWay2_item.setSelected(false);
                break;
            case R.id.rl_payWay2:
                iv_payWay0_item.setSelected(false);
                iv_payWay2_item.setSelected(true);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                if (iv_payWay0_item.isSelected()){
                    if (tv_withdraw1.getText().toString().equals("未绑定")){
                        startActivity(new Intent(context,WithdrawBankActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(context,WithdrawBankActivity.class));
                        finish();
                    }

                }else {
                    Toast.makeText(context,"您还未选项",Toast.LENGTH_SHORT).show();
                }
                break;
            //本来想调用绑定个人银行 参数不传 来删除 ，但是不行 参数丢失  需要删除已绑定银行卡接口
            case R.id.tv_delete:
                if (iv_payWay0_item.isSelected()){
                    if (NetUtil.isConnnected(context)){
                        progressDialog.setMessage("正在加载");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        new MainRequest(context,handler).UpdateUserBank("","","","","","");
                    }else {
                        Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context,"您还未选项",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
