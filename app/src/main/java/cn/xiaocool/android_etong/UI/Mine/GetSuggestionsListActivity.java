package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.GetMySuggestionAdapter;
import cn.xiaocool.android_etong.bean.Mine.MySuggestionsBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by hzh on 2016/12/25.
 */

public class GetSuggestionsListActivity extends Activity implements View.OnClickListener {

    private EditText etSuggestion;
    private TextView tvAdd;
    private Context context;
    private List<MySuggestionsBean.DataBean> dataBeanList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_SUGGESTIONS:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject jsonObject1;
                            if (jsonArray.length() == 0) {
                                IntentUtils.getIntent(GetSuggestionsListActivity.this, WriteSuggestionsActivity.class);
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject1 = (JSONObject) jsonArray.get(i);
                                    MySuggestionsBean.DataBean dataBean = new MySuggestionsBean.DataBean();
                                    dataBean.setContent(jsonObject1.getString("content"));
                                    dataBean.setCreate_time(jsonObject1.getString("create_time"));
                                    dataBeanList.add(dataBean);
                                }
                                suggestionList.setAdapter(new GetMySuggestionAdapter(context, dataBeanList));

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private ListView suggestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_my_suggestions);
        context = this;
        initView();
//        if (NetUtil.isConnnected(context)) {
//            new MineRequest(context, handler).getSuggestions();
//        }
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("意见反馈");
        etSuggestion = (EditText) findViewById(R.id.mine_write_suggestions_et);
        tvAdd = (TextView) findViewById(R.id.mine_add_suggestions);
        tvAdd.setOnClickListener(this);
        suggestionList = (ListView) findViewById(R.id.get_my_suggestion_listView);
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        dataBeanList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
            if (NetUtil.isConnnected(context)) {
                dataBeanList.clear();
                new MineRequest(context, handler).getSuggestions();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_add_suggestions:
                IntentUtils.getIntent(this, WriteSuggestionsActivity.class);
                //跳转写建议
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
