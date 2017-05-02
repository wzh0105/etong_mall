package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/11/20.
 */

public class WithdrawActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private EditText et_withdraw;
    private Button btn_sure;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ApplyWithdraw:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        Log.e("status = ", status);
                        if (status.equals("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "提现成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
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
        setContentView(R.layout.activity_withdraw);
        context = this;
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        et_withdraw = (EditText) findViewById(R.id.et_withdraw);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                withdraw();
                break;
        }
    }

    private void withdraw() {
        if (!TextUtils.isEmpty(et_withdraw.getText().toString())){
            if (NetUtil.isConnnected(context)){
                progressDialog.setMessage("提现中...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                 new MainRequest(context,handler).ApplyWithdraw(et_withdraw.getText().toString(),"2");
            }else {
                Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,"请输入正确金额",Toast.LENGTH_SHORT).show();
        }

    }

}
