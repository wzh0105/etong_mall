package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.EAgencyShopListActivity;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;

/**
 * Created by 潘 on 2016/10/19.
 */

public class AgentActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private Context context;
    private TextView tv_yue, tv_benyuejiedan, tv_all_oreder, tv_month_income, tv_all_income;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GetMyWallet:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            Log.e("success", "GetMyWallet");
                            JSONObject object = jsonObject1.getJSONObject("data");
                            if (object.getString("availablemoney").equals("null") || object.getString("availablemoney") == null) {
                                tv_yue.setText("0.00");
                            } else {
                                tv_yue.setText(object.getString("availablemoney"));
                            }
                            tv_benyuejiedan.setText(object.getString("monthorders"));
                            tv_all_oreder.setText(object.getString("allorder"));
                            tv_month_income.setText(object.getString("monthincome"));
                            tv_all_income.setText(object.getString("allincome"));

                        } else {
                            Toast.makeText(context, jsonObject1.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private LinearLayout btnShopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_agent);
        context = this;
        initView();
        new MainRequest(context, handler).GetMyWallet();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_yue = (TextView) findViewById(R.id.tv_yue);
        tv_benyuejiedan = (TextView) findViewById(R.id.tv_benyuejiedan);
        tv_all_oreder = (TextView) findViewById(R.id.tv_all_order);
        tv_month_income = (TextView) findViewById(R.id.tv_month_income);
        tv_all_income = (TextView) findViewById(R.id.tv_all_income);
        btnShopList = (LinearLayout) findViewById(R.id.agency_shop_list_btn);
        btnShopList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.agency_shop_list_btn:
                Intent intent = new Intent();
                intent.setClass(context, EAgencyShopListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
