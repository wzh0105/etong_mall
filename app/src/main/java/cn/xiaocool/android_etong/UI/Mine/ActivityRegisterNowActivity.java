package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by hzh on 2016/12/31.
 */

public class ActivityRegisterNowActivity extends Activity implements View.OnClickListener {
    private Context context;
    private UserInfo userInfo;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == CommunalInterfaces.ACTIVITY_REGISTER) {
                JSONObject jsonObject = (JSONObject) msg.obj;
                try {
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        ToastUtils.makeShortToast(context, "报名成功！");
                        finish();
                    } else {
                        ToastUtils.makeShortToast(context, "报名失败，请重试！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                {

                }
            }

        }
    };
    private String activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);
        context = this;
        Intent intent = getIntent();
        activityId = intent.getStringExtra("id");
        initView();
        userInfo = new UserInfo();
        userInfo.readData(context);


    }

    private void initView() {
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("立即报名");
        RelativeLayout rlRegisterNow = (RelativeLayout) findViewById(R.id.activity_register_now_btn);
        rlRegisterNow.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        } else if (view.getId() == R.id.activity_register_now_btn) {
            if (NetUtil.isConnnected(context)) {
                new MainRequest(context, handler).activityRegister(activityId, "");
            }
        }
    }
}
