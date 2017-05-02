package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;


/**
 * Created by hzh on 2016/12/30.
 */

public class ShopVerifyStatusActivity extends Activity implements View.OnClickListener {

    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        Log.e("status", status);
        if (status.equals("1")) {
            //验证成功后
            setContentView(R.layout.shop_verify_success);
        } else if (status.equals("0")) {
            //验证失败后
            setContentView(R.layout.shop_verify_faied);
        }
        initView();
    }

    private void initView() {
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        TextView tvTop = (TextView) findViewById(R.id.top_title_text);
        tvTop.setText("验证详情");
        Button btnFinish = (Button) findViewById(R.id.btn_verify_finish);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_verify_finish:
                finish();
                break;
        }
    }
}
