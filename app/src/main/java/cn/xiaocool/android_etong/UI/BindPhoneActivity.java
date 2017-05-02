package cn.xiaocool.android_etong.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.etongApplaction;

/**
 * Created by wzh on 2016/11/12.
 */

public class BindPhoneActivity extends Activity implements View.OnClickListener {

    private IWXAPI api;
    private Button btnConfirm;
    private EditText etPhone, etCode;
    private Context context;
    private String passWeChatCode;
    private etongApplaction applaction;
    private static int second = 30;
    private String code;
    private UserInfo user;
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
                            code = joCode.getString("code");
                            Log.e("code is ", code);
                        } else {
                            Toast.makeText(context, "验证码获取失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.BTN_UNTOUCH:
                    //按钮不可用，开始倒计时
                    btnSendCode.setClickable(false);
                    btnSendCode.setText(second + "s");
                    break;
                case CommunalInterfaces.BTN_TOUCH:
                    btnSendCode.setText("发送验证码");
                    btnSendCode.setClickable(true);
                    second = 30;
                    break;
                case CommunalInterfaces.REGISTER:
                    //判断与服务器的验证码是否一致，如果一致则提示注册成功，跳转到主界面；否则提示验证码错误
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        JSONObject jsonObject1 = json.getJSONObject("data");
                        String info = jsonObject1.getString("info");
                        String userId = jsonObject1.getString("userid");
                        if (status.equals("success")) {
                            //实力化缓存类;
                            //绑定微信
//                            Toast.makeText(context, "注册成功！", Toast.LENGTH_SHORT).show();
                            Log.e("user user",userId + openId);
                            new MainRequest(context,handler).bindWeChat(userId,openId);
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            startActivity(intent);
//                            finish();
                        } else if (info.equals("该手机号码已经注册，可直接登录")) {

                            //绑定微信

//                            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                            Log.e("user user1",userId + openId);
                            new MainRequest(context,handler).bindWeChat(userId,openId);

                        } else {
                            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.BIND_WECHAT_AND_USERID:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")){
                            ToastUtils.makeShortToast(context,"绑定微信成功！");
                            if (NetUtil.isConnnected(context)){
                                new MainRequest(context,handler).checkWeChatBind(openId);//检查微信是否已经绑定手机号
                            }else {

                            }
                        }else {
                            ToastUtils.makeShortToast(context,"绑定微信失败！");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CHECK_WECHAT_BIND:
                    JSONObject jsObject = (JSONObject) msg.obj;
                    try {
                        String status = jsObject.getString("status");
                        if (status.equals("success")){
                            ToastUtils.makeShortToast(context,"微信登陆成功！");//跳转登录界面
                            JSONObject jsObject1 = jsObject.getJSONObject("data");
                            user.setUserId(jsObject1.getString("id"));
                            user.setUserImg(jsObject1.getString("photo"));
                            user.writeData(context);
                            applaction.setResp(null);
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }
                        else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };
    private Button btnSendCode;
    private String openId, nickName;
    private String phoNum, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_login_phone);
        context = this;
        applaction = (etongApplaction) getApplication();
        user = new UserInfo();
        openId = getIntent().getStringExtra("openid");
        nickName = getIntent().getStringExtra("nickname");
        initView();
        // 微信注册初始化
        api = WXAPIFactory.createWXAPI(this, "wxb32c00ffa8140d93", true);
        api.registerApp("wxb32c00ffa8140d93");
    }

    private void initView() {
        btnConfirm = (Button) findViewById(R.id.btn_confirm_bind);
        btnConfirm.setOnClickListener(this);
        etPhone = (EditText) findViewById(R.id.bind_et_phone_num);
        etPhone.setOnClickListener(this);
        etCode = (EditText) findViewById(R.id.bind_et_code);
        etCode.setOnClickListener(this);
        btnSendCode = (Button) findViewById(R.id.bind_btn_send_code);
        btnSendCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_bind:
                register();
                break;
            case R.id.bind_btn_send_code:
                sendCode();
                break;
        }
    }


    private void register() {
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        if (code.length() == 6) {
            new MainRequest(this, handler).register(phone, openId, code);

        } else {
            Toast.makeText(context, "请输入正确验证码", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendCode() {
        phoNum = etPhone.getText().toString();
        Log.e("phone number is", phoNum);
        if (phoNum.length() == 11) {
            new Thread() {
                public void run() {
                    new MainRequest(context, handler).sendCode(phoNum);
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




}
