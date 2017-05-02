package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.GetActivityListAdapter;
import cn.xiaocool.android_etong.adapter.GetBBSListAdapter;
import cn.xiaocool.android_etong.bean.ActivityRegisterBean;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.ApiStores;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;

/**
 * Created by hzh on 2016/12/31.
 */

public class ActivityRegisterActivity extends Activity implements View.OnClickListener {
    private Context context;
    private ListView listView;
    private UserInfo userInfo;
    private GetActivityListAdapter getActivityListAdapter;
    private List<ActivityRegisterBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        initView();
        userInfo = new UserInfo();
        userInfo.readData(context);
        getActivityList(userInfo.getUserId());
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.activity_register_listView);
        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        TextView tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("活动列表");
    }

    public void getActivityList(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<ActivityRegisterBean> call = apiStores.getActivityRegister(userId);

        call.enqueue(new Callback<ActivityRegisterBean>() {
            @Override
            public void onResponse(Call<ActivityRegisterBean> call, Response<ActivityRegisterBean> response) {
                list = response.body().getData();
                setAdapter();//异步请求结束后，设置适配器
            }

            @Override
            public void onFailure(Call<ActivityRegisterBean> call, Throwable t) {
            }


        });
    }

    private void setAdapter() {
        if (getActivityListAdapter != null) {
            getActivityListAdapter.notifyDataSetChanged();
        } else {
            getActivityListAdapter = new GetActivityListAdapter(context, list);
            listView.setAdapter(getActivityListAdapter);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }
}
