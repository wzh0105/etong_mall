package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/11/17.
 */

public class WithdrawSelectActivity extends Activity implements View.OnClickListener {

    private Context context;
    private RelativeLayout rl_back,rl_payWay0,rl_payWay2;
    private ImageView iv_payWay0_item,iv_payWay2_item;
    private TextView tv_withdraw1,tv_withdraw2;
    private Button btn_sure;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GetUserBankInfo:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            if (object.getString("bank").equals("null")|| TextUtils.isEmpty(object.getString("bank"))){
                                tv_withdraw1.setText("未绑定");
                            }
//                            if (object.getString("bankno").equals("null")|| TextUtils.isEmpty(object.getString("bankno"))){
//                                tv_withdraw2.setText("未绑定");
//                            }
                        } else {
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
        setContentView(R.layout.activity_withdraw_select);
        context = this;
        initView();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).GetUserBankInfo();
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {

        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        iv_payWay0_item = (ImageView) findViewById(R.id.iv_payWay0_item);
        iv_payWay2_item = (ImageView) findViewById(R.id.iv_payWay2_item);
        tv_withdraw1 = (TextView) findViewById(R.id.tv_withdraw1);
        tv_withdraw2 = (TextView) findViewById(R.id.tv_withdraw2);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
        rl_payWay0 = (RelativeLayout) findViewById(R.id.rl_payWay0);
        rl_payWay0.setOnClickListener(this);
        rl_payWay2 = (RelativeLayout) findViewById(R.id.rl_payWay2);
        rl_payWay2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_payWay0:
                iv_payWay0_item.setSelected(true);
                iv_payWay2_item.setSelected(false);
                break;
            case R.id.rl_payWay2:
                iv_payWay0_item.setSelected(false);
                iv_payWay2_item.setSelected(true);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                if (iv_payWay0_item.isSelected()){
                   if (tv_withdraw1.getText().toString().equals("未绑定")){
                       startActivity(new Intent(context,WithdrawBankActivity.class));
                       finish();
                   }else {
                       if(NetUtil.isConnnected(context)){
                           startActivity(new Intent(context,WithdrawActivity.class));
                           finish();
                       }else {
                           Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                       }
                   }
                }else if (iv_payWay2_item.isSelected()){
                    if (tv_withdraw2.getText().toString().equals("未绑定")){
                        startActivity(new Intent(context,WithdrawApplyActivity.class));
                        finish();
                    }else {
                        if(NetUtil.isConnnected(context)){

                        }else {
                            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(context,"您还未选项",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
