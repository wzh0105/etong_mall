package cn.xiaocool.android_etong.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;

/**
 * Created by 潘 on 2016/6/22.
 */
public class ForgetPasswordActivity extends Activity implements View.OnClickListener{

    private static int second =30;
    private Context context;
    private EditText et_forget_phone ,et_forget_code,et_forget_password,et_forget_sure;
    private UserInfo user;
    private RelativeLayout re_back;
    private Button btn_sure,btn_send_code;
    private String phoneNumber, code;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_CODE:
                    JSONObject jsonObject=(JSONObject)msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        Log.e("status = ", status);
                        if (status.equals("success")) {
                            Toast.makeText(ForgetPasswordActivity.this, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                            String data = jsonObject.getString("data");
                            JSONObject joCode = new JSONObject(data);
                            code = joCode.getString("code");
                            Log.e("code is ", code);
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "验证码获取失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.BTN_UNTOUCH:
                    //按钮不可用，开始倒计时
                    btn_send_code.setClickable(false);
                    btn_send_code.setText(second + "s");
                    break;
                case CommunalInterfaces.BTN_TOUCH:
                    btn_send_code.setText("发送验证码");
                    btn_send_code.setClickable(true);
                    second =30;
                    break;
                case CommunalInterfaces.FORGETPASSWORD:
                    //判断与服务器的验证码是否一致，如果一致则提示注册成功，跳转到主界面；否则提示验证码错误
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            //实力化缓存类
                            Toast.makeText(ForgetPasswordActivity.this, "找回密码成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetPasswordActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ForgetPasswordActivity.this, data,Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_forgetpassword);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        initView();
    }

    private void initView() {
        et_forget_phone = (EditText)findViewById(R.id.et_forget_phone);
        et_forget_code=(EditText)findViewById(R.id.et_forget_code);
        et_forget_password = (EditText)findViewById(R.id.et_forget_password);
        et_forget_sure=(EditText)findViewById(R.id.et_forget_sure);
        btn_sure = (Button)findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        btn_send_code = (Button)findViewById(R.id.btn_send_code);
        btn_send_code.setOnClickListener(this);
        re_back = (RelativeLayout)findViewById(R.id.re_back);
        re_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_code:
                sendCode();
                break;
            case R.id.btn_sure:
                Next();
                break;
            case R.id.re_back:
                finish();
                break;
        }

    }



    private void sendCode() {
        phoneNumber = et_forget_phone.getText().toString();
        Log.e("phone number is", phoneNumber);
        if (phoneNumber.length() == 11) {
            new Thread(){
                public void run() {
                    new MainRequest(context,handler).sendCode(phoneNumber);
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
            Toast.makeText(ForgetPasswordActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
        }
    }

    private void Next() {
        //获取手机号码的字符串形式，获取服务器返回的手机号和验证码值
        String phoneNum = et_forget_phone.getText().toString();
        String verifyCode = et_forget_code.getText().toString();
        String firstPassword = et_forget_password.getText().toString();
        String secondPassword = et_forget_sure.getText().toString();
        if (firstPassword.equals(secondPassword)&&!firstPassword.equals("")) {
            if ((phoneNum.length() == 11) && (verifyCode.length()) == 6) {
                new  MainRequest(context,handler).forgetpassword(phoneNum,verifyCode,firstPassword);
            } else {
                Toast.makeText(ForgetPasswordActivity.this, "请输入正确的验证码和手机号！！", Toast.LENGTH_SHORT).show();
            }
        }else
            Toast.makeText(ForgetPasswordActivity.this,"请检查输入的密码是否正确！",Toast.LENGTH_SHORT).show();

    }
}
