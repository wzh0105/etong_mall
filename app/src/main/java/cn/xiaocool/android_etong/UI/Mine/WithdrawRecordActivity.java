package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.WithdrawAdapter;
import cn.xiaocool.android_etong.bean.Mine.WithdrawRecord;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/11/20.
 */

public class WithdrawRecordActivity extends Activity implements View.OnClickListener {

    private Context context;
    private RelativeLayout rl_back;
    private ListView lv_withdraw;
    private List<WithdrawRecord.DataBean> databeans;
    private WithdrawAdapter withdrawaAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GetMyApplyWithdraw:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")){
                            JSONArray jsonarray = jsonObject1.getJSONArray("data");
                            for (int i = 0; i<jsonarray.length();i++){
                                WithdrawRecord.DataBean databean = new WithdrawRecord.DataBean();
                                JSONObject object = jsonarray.getJSONObject(i);
                                databean.setMoney(object.getString("money"));
                                databean.setTime(getStrTime(object.getString("time")));
                                databeans.add(databean);
                            }
                            withdrawaAdapter = new WithdrawAdapter(context,databeans);
                            lv_withdraw.setAdapter(withdrawaAdapter);
                        }else {
                            Toast.makeText(context, jsonObject1.getString("data"), Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_withdraw_record);
        context = this;
        initView();
        initData();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        lv_withdraw = (ListView) findViewById(R.id.lv_withdraw);
        databeans = new ArrayList<>();
    }

    private void initData() {
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).GetMyApplyWithdraw();
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public static String getStrTime(String cc_time){
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        //例如 :cc_time = 1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time*1000L));
        return re_StrTime;
    }
    
}
