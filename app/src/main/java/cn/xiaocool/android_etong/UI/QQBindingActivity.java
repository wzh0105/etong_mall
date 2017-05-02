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
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/11/23.
 */

public class QQBindingActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private String openid;
    private UserInfo user;
    private String phone, password;
    private EditText et_login_phone,et_login_password;
    private Context context;
    private Button btn_sure;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.UpdateUserQQ:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            if (NetUtil.isConnnected(context)){
//                                new MainRequest(context,handler).GetUserInfoByQQ(openid);
                                context.startActivity(new Intent(context, MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(context,jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GetUserInfoByQQ:
                    JSONObject jsonObject1= (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                            user.setUserId(jsonObject2.getString("id"));
                            user.setUserImg(jsonObject2.getString("photo"));
                            user.writeData(context);
                            context.startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(context,jsonObject1.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.LOGIN:
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            Log.e("data", data);
                            JSONObject item = new JSONObject(data);
                            user.setUserId(item.getString("id"));
                            user.setUserImg(item.getString("photo"));
                            user.writeData(context);
                            if (NetUtil.isConnnected(context)){
                                new MainRequest(context,handler).UpdateUserQQ(openid);
                            }else {
                                Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(context, data,
                                    Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_qqbinding);
        context = this;
        user = new UserInfo();
        user.readData(context);
        Intent intent = getIntent();
        openid = intent.getStringExtra("openid");
        initView();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                login();
                break;
        }
    }

    //登录
    private void login() {
        phone = et_login_phone.getText().toString();
        phone = phone.trim();
        password = et_login_password.getText().toString();
        if (phone.length() == 11) {
            if (!password.equals("")) {
                new MainRequest(context, handler).login(phone, password);
            } else {
                Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                et_login_password.requestFocus();
            }
        } else {
            Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            et_login_phone.requestFocus();
        }

    }

}
