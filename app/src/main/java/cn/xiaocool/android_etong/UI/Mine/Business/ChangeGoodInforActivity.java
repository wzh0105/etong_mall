package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/20.
 */
public class ChangeGoodInforActivity extends Activity implements View.OnClickListener {
    private EditText etGoodInforItem;
    private Button btnSave;
    private RelativeLayout btnBack;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CHANGE_GOOD_INTRO_ITEM:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(ChangeGoodInforActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangeGoodInforActivity.this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };
    private String suffix;
    private String infor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_change_good_infor_item);
        initView();
        Intent intent = getIntent();
        infor = intent.getStringExtra("changeInfor");
        suffix = intent.getStringExtra("webAddress");
        etGoodInforItem.setText(infor);
        etGoodInforItem.setSelection(etGoodInforItem.getText().length());//光标置于最后
    }

    private void initView() {
        etGoodInforItem = (EditText) findViewById(R.id.business_et_change_infor);
        btnSave = (Button) findViewById(R.id.business_btn_save);
        btnSave.setOnClickListener(this);
        btnBack = (RelativeLayout) findViewById(R.id.rl_back);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:

                finish();
                break;
            case R.id.business_btn_save:
                if (NetUtil.isConnnected(this)) {
                    String infor = etGoodInforItem.getText().toString();
                    new MineRequest(this, handler).changeGoodIntroItem(suffix, infor);
                } else {
                    Toast.makeText(this, "无网络！请稍后重试！", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
