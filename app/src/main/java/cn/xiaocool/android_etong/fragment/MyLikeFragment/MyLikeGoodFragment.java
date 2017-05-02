package cn.xiaocool.android_etong.fragment.MyLikeFragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.MyLikeGoodAdapter;
import cn.xiaocool.android_etong.bean.MyLikeBean.MyLikeGoodBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/5/21.
 */
public class MyLikeGoodFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ListView listView;
    private List<MyLikeGoodBean.MyLikeGoodDataBean> myLikeGoodDataBeanList;
    private RelativeLayout btnback;
    private TextView tvTitle;
    private MyLikeGoodAdapter myLikeGoodAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MY_LIKE_GOOD:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                MyLikeGoodBean.MyLikeGoodDataBean myLikeGoodDataBean = new MyLikeGoodBean.MyLikeGoodDataBean();
                                myLikeGoodDataBean.setId(dataObject.getString("object_id"));//此处为商品ID
                                myLikeGoodDataBean.setTitle(dataObject.getString("title"));
                                myLikeGoodDataBean.setGoodsName(dataObject.getString("goodsname"));//此处为商品名字
                                myLikeGoodDataBean.setPrice(dataObject.getString("price"));
                                myLikeGoodDataBean.setPhoto(dataObject.getString("photo"));
                                myLikeGoodDataBean.setStarLevel(dataObject.getString("starlevel"));
                                myLikeGoodDataBeanList.add(myLikeGoodDataBean);
                            }
                            myLikeGoodAdapter = new MyLikeGoodAdapter(context, myLikeGoodDataBeanList);
                            listView.setAdapter(myLikeGoodAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_like_good, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        if (NetUtil.isConnnected(context)) {
            new MineRequest(context, handler).myLikeGood();
        }
    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_my_like_good);
        myLikeGoodDataBeanList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        
    }
}
