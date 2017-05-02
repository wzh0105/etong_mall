package cn.xiaocool.android_etong.UI.Mine;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;


/**
 * Created by 潘 on 2016/11/17.
 */

public class WithdrawBankActivity extends Activity implements View.OnClickListener {
    private static int second = 30;
    private Context context;
    private String phoneNumber;
    private Button btn_finish;
    private RelativeLayout rl_back;
    private EditText et_name,et_id_card,et_bank,et_phone,et_code;
    private TextView tv_send_code,tv_select;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_CODE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        Log.e("status = ", status);
                        if (status.equals("success")) {
                            Toast.makeText(context, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                            String data = jsonObject.getString("data");
                            JSONObject joCode = new JSONObject(data);
                        } else {
                            Toast.makeText(context, "验证码获取失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.BTN_UNTOUCH:
                    //按钮不可用，开始倒计时
                    tv_send_code.setClickable(false);
                    tv_send_code.setText(second + "s");
                    break;
                case CommunalInterfaces.BTN_TOUCH:
                    tv_send_code.setText("发送验证码");
                    tv_send_code.setClickable(true);
                    second = 30;
                    break;
                case CommunalInterfaces.UpdateUserBank:
                    //判断与服务器的验证码是否一致，如果一致则提示注册成功，跳转到主界面；否则提示验证码错误
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        if (status.equals("success")) {
                            //实力化缓存类;
                            Toast.makeText(context, "绑定成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context,"绑定失败" , Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_withdraw_bank);
        context = this;
        initView();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_id_card = (EditText) findViewById(R.id.et_id_card);
        et_bank = (EditText) findViewById(R.id.et_bank);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        tv_send_code = (TextView) findViewById(R.id.tv_send_code);
        tv_send_code.setOnClickListener(this);
        tv_select = (TextView) findViewById(R.id.tv_select);
        tv_select.setOnClickListener(this);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_select:
                startActivityForResult(new Intent(context,SelectBankActivity.class),11);
                break;
            case R.id.btn_finish:
                updateUserBank();
                break;
        }
    }

    private void updateUserBank() {
        if (!TextUtils.isEmpty(et_name.getText().toString())){
            if (et_id_card.getText().toString().length()==18){
                if (!TextUtils.isEmpty(et_bank.getText().toString())){
                    if (!tv_select.getText().toString().equals("请选择开户银行")){
                        if (et_phone.getText().toString().length()==11){
                            if (et_code.getText().toString().length()==6){
                                new MainRequest(context,handler).UpdateUserBank(et_name.getText().toString(),
                                        et_id_card.getText().toString(),tv_select.getText().toString(),
                                        et_bank.getText().toString(),et_phone.getText().toString(),et_code.getText().toString());
                            }else {
                                Toast.makeText(context,"请输入正确验证码",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(context,"请输入正确手机号",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context,"请选择开户银行",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context,"请输入正确银行卡号",Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(context,"请输入正确身份证",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(context,"请输入姓名",Toast.LENGTH_SHORT).show();
        }
    }

    //发送验证码
    private void sendCode() {
        phoneNumber = et_phone.getText().toString();
        Log.e("phone number is", phoneNumber);
        if (phoneNumber.length() == 11) {
            new Thread() {
                public void run() {
                    new MainRequest(context, handler).sendCode(phoneNumber);
                    for (int i = 0; i < 30; i++) {
                        try {
                            handler.sendEmptyMessage(CommunalInterfaces.BTN_UNTOUCH);
                            Thread.sleep(1000);
                            second--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(CommunalInterfaces.BTN_TOUCH);
                }
            }.start();
        } else {
            Toast.makeText(context, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==11){
            Log.e("data",data.getStringExtra("bankname"));
            tv_select.setText(data.getStringExtra("bankname"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
