package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/11/13.
 */

public class AuthenticationClassificationActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private TextView textView9,textView10;
    private TextView tvShopGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_authentication);
        context = this;
        initView();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        textView9 = (TextView) findViewById(R.id.textView9);
        textView9.setOnClickListener(this);
        textView10 = (TextView) findViewById(R.id.textView10);
        textView10.setOnClickListener(this);
        tvShopGuide = (TextView) findViewById(R.id.start_shop_guide);
        tvShopGuide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.textView9:
                Intent intent1 = new Intent();
                intent1.setClass(context,AuthenticationPersonActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.textView10:
                Intent intent = new Intent();
                intent.setClass(context,AuthenticationShopActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.start_shop_guide:
                Intent intent2 = new Intent();
                intent2.setClass(context,StartShopGuideActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

}
