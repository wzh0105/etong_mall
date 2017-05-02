package cn.xiaocool.android_etong.UI.Mine;

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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.KeyBoardUtils;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/27.
 */
public class EditPhoneActivity extends Activity implements View.OnClickListener {
    private TextView top_upload;
    private EditText ed_phone;
    private Context context;
    private RelativeLayout ry_line;
    private UserInfo user;
    private String phone;
    private RelativeLayout rl_back;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.UPDATAUSERPHONE:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            progressDialog.dismiss();
                            Log.e("success", "更新手机号成功");
                            Toast.makeText(context, "更新手机号成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            setResult(RESULT_OK, intent);
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
        setContentView(R.layout.activity_edit_phone);
        context = this;
        user = new UserInfo();
        user.readData(context);
        String data = user.getUserPhone();
        Log.e("name=", data);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initview();
        ed_phone.setText(data);
        CharSequence charSequence = ed_phone.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(ed_phone, 300);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
    }

    private void initview() {
        ry_line = (RelativeLayout) findViewById(R.id.lin_phone);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
        linearParams.height = getStatusBarHeight(context);
        ry_line.setLayoutParams(linearParams);

        ed_phone = (EditText) findViewById(R.id.ed_phone);
        top_upload = (TextView) findViewById(R.id.top_upload);
        top_upload.setOnClickListener(this);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.top_upload:
                if (ed_phone.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();
                    ed_phone.requestFocus();
                } else if (ed_phone.getText().toString().length() != 11) {
                    Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("正在提交");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    phone = ed_phone.getText().toString();
                    new MainRequest(context, handler).updatauserphone(ed_phone.getText().toString());
                }
                break;
            case R.id.rl_back:
                finish();
                break;
        }

    }
}
