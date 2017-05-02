package cn.xiaocool.android_etong.fragment.MyLikeFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.MyLikeShopAdapter;
import cn.xiaocool.android_etong.bean.MyLikeBean.MyLikeShopBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/5/21.
 */
public class MyLikeShopFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ListView listView;
    private List<MyLikeShopBean.MyLikeShopDataBean> myLikeShopDataBeanList;
    private RelativeLayout btnback;
    private TextView tvTitle;
    private MyLikeShopAdapter myLikeShopAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MY_LIKE_SHOP:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                MyLikeShopBean.MyLikeShopDataBean myLikeShopDataBean = new MyLikeShopBean.MyLikeShopDataBean();
                                myLikeShopDataBean.setId(dataObject.getString("id"));
                                myLikeShopDataBean.setTitle(dataObject.getString("title"));
                                myLikeShopDataBean.setDescription(dataObject.getString("description"));
                                myLikeShopDataBean.setPrice(dataObject.getString("price"));
                                myLikeShopDataBean.setId(dataObject.getString("id"));
                                myLikeShopDataBean.setPhoto(dataObject.getString("photo"));
                                myLikeShopDataBean.setStarLevel(dataObject.getString("starlevel"));
                                myLikeShopDataBeanList.add(myLikeShopDataBean);
                            }
                            myLikeShopAdapter = new MyLikeShopAdapter(context, myLikeShopDataBeanList);
                            listView.setAdapter(myLikeShopAdapter);
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
        View view = inflater.inflate(R.layout.my_like_shop, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        if (NetUtil.isConnnected(context)) {
            new MineRequest(context, handler).myLikeShop();
        }
    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_my_like_shop);
        myLikeShopDataBeanList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

    }
}
