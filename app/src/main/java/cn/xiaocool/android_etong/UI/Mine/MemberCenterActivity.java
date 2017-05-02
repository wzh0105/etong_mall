package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.MEMBER_CENTER;

/**
 * Created by hzh on 2016/12/9.
 */

public class MemberCenterActivity extends Activity implements View.OnClickListener {
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_center);
        user = new UserInfo();
        user.readData(this);
        initView();
    }

    private void initView() {
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("会员中心");
        WebView webView = (WebView) findViewById(R.id.member_center_webView);

        webView.loadUrl(MEMBER_CENTER + user.getUserId());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
