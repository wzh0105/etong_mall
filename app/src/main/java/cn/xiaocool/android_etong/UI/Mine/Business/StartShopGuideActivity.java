package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;

/**
 * Created by hzh on 2016/11/26.
 */

public class StartShopGuideActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rlBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_shop_guide);
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("开店指引");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
