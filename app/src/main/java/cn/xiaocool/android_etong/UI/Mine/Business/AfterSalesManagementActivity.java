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
 * Created by 潘 on 2016/7/17.
 */
public class AfterSalesManagementActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private RelativeLayout rl_return_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_after_sales_management);
        context = this;
        initview();
    }

    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        rl_return_goods = (RelativeLayout) findViewById(R.id.rl_retun_goods);
        rl_return_goods.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_retun_goods:
                startActivity(new Intent(context,ReturnGoodsActivity.class));
                break;
        }
    }
}
