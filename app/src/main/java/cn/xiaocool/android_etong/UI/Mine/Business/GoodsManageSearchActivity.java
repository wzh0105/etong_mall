package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;

/**
 * Created by hzh on 2016/12/2.
 */

public class GoodsManageSearchActivity extends Activity implements View.OnClickListener {

    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_manage_search);
        initView();
    }

    private void initView() {
        tvSearch = (TextView) findViewById(R.id.good_manage_search_tv);
        tvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.good_manage_search_tv:

                //搜索网络请求
                break;
        }
    }
}
