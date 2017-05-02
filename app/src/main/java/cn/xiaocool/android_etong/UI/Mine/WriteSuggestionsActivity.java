package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by hzh on 2016/12/25.
 */

public class WriteSuggestionsActivity extends Activity implements View.OnClickListener {

    private EditText etSuggestion;
    private TextView tvConfirm;
    private Context context;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ADD_SUGGESTIONS:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "提交建议成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "提交失败，请重试！");
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
        setContentView(R.layout.write_my_suggestions);
        context = this;
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("意见反馈");
        etSuggestion = (EditText) findViewById(R.id.mine_write_suggestions_et);
        tvConfirm = (TextView) findViewById(R.id.mine_write_suggestions_confirm);
        tvConfirm.setOnClickListener(this);
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_write_suggestions_confirm:
                String content = etSuggestion.getText().toString();
                if (!content.equals("")) {
                    if (NetUtil.isConnnected(context)) {
                        new MineRequest(context, handler).addSuggestions(content);
                    } else {
                        ToastUtils.makeShortToast(context, "提交失败！请检查网络！");
                    }
                } else {
                    ToastUtils.makeShortToast(context, "建议不可为空！");
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
