package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
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
import cn.xiaocool.android_etong.UI.Mine.BusinessActivity;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.KeyBoardUtils;


/**
 * Created by 潘 on 2016/10/9.
 */

public class ConfirmSecurityActivity extends Activity implements View.OnClickListener{
    private UserInfo user;
    private String phone, password,shopid,islocal;
    private EditText et_login_phone, et_login_password;
    private Context context;
    private RelativeLayout rl_back;
    private Button btn_login;
    private ProgressDialog progressDialog;

    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.LOGIN:
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(ConfirmSecurityActivity.this, "进入我的店铺",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent();
                            intent1.putExtra("shopid", shopid);
                            intent1.putExtra("islocal",islocal);
                            intent1.setClass(context, BusinessActivity.class);
                            startActivity(intent1);
                            finish();
                        } else {
                            Toast.makeText(ConfirmSecurityActivity.this, data,
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
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
        setContentView(R.layout.activity_confirm_security);
        context = this;
        user = new UserInfo();
        user.readData(this);
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        islocal = intent.getStringExtra("islocal");
        initView();
    }

    private void initView() {

        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);

        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        if (!user.getUserPhone().equals("")) {
            user.readData(this);
            et_login_phone.setText(user.getUserPhone());
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = et_login_phone.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(et_login_phone, 300);
    }

    //登录
    private void login() {
        progressDialog.setMessage("正在确认");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        phone = et_login_phone.getText().toString();
        phone = phone.trim();
        password = et_login_password.getText().toString();
        user.setUserPhone(phone);
        user.setUserPassword(password);
        user.writeData(context);
        Log.e("et_login_phone is", et_login_phone.getText().toString());
        Log.e("et_login_password is", et_login_password.getText().toString());
        if (phone.length() == 11) {
            if (!password.equals("")) {
                new MainRequest(context, handle).login(phone, password);
            } else {
                Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                et_login_password.requestFocus();
                progressDialog.dismiss();
            }
        } else {
            Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            et_login_phone.requestFocus();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
