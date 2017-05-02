package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/7/7.
 */
public class ApplyShopActivity extends Activity implements View.OnClickListener{
    private RelativeLayout rl_back,rl_next;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_applyshop);
        context = this;
        initview();
    }

    private void initview() {
        rl_back = (RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        rl_next = (RelativeLayout)findViewById(R.id.rl_next);
        rl_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_next:
                Intent intent = new Intent();
                intent.setClass(context,AuthenticationClassificationActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
