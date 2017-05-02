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
 * Created by 潘 on 2016/6/21.
 */
public class RegisiterActivity extends Activity implements View.OnClickListener {
    private static int second = 30;
    private Context context;
    private EditText et_register_phone, et_register_password, et_sure_password;
    private UserInfo user;
    private RelativeLayout re_back;
    private Button btn_register, btn_send_code;
    private String phoneNumber, code;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_CODE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        Log.e("status = ", status);
                        if (status.equals("success")) {
                            Toast.makeText(RegisiterActivity.this, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                            String data = jsonObject.getString("data");
                            JSONObject joCode = new JSONObject(data);
                            code = joCode.getString("code");
                            Log.e("code is ", code);
                        } else {
                            Toast.makeText(RegisiterActivity.this, "验证码获取失败，请重试！", Toast.LENGTH_SHORT).show();
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
                    second = 30;
                    break;
                case CommunalInterfaces.REGISTER:
                    //判断与服务器的验证码是否一致，如果一致则提示注册成功，跳转到主界面；否则提示验证码错误
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        JSONObject jsonObject1 = json.getJSONObject("data");
                        String info = jsonObject1.getString("info");
//                        String data = json.getString("data");
                        if (status.equals("success")) {
                            //实力化缓存类;
                            Toast.makeText(RegisiterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisiterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(RegisiterActivity.this, info, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_regisiter);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        initView();
    }

    private void initView() {
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_sure_password = (EditText) findViewById(R.id.et_sure_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_send_code = (Button) findViewById(R.id.btn_send_code);
        btn_send_code.setOnClickListener(this);
        re_back = (RelativeLayout) findViewById(R.id.re_back);
        re_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_code:
                sendCode();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.re_back:
                finish();
                break;
        }

    }

    private void register() {
        String phone = et_register_phone.getText().toString();
        String code = et_register_password.getText().toString();
        String password = et_sure_password.getText().toString();
        if (code.length() == 6) {
            if (!password.equals("")) {
                new MainRequest(this, handler).register(phone, password, code);
            } else {
                Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "请输入正确验证码", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendCode() {
        phoneNumber = et_register_phone.getText().toString();
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
            Toast.makeText(RegisiterActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
        }
    }
}
