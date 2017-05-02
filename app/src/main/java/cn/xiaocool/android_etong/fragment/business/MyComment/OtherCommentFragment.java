package cn.xiaocool.android_etong.fragment.business.MyComment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.MyCommentAdapter;
import cn.xiaocool.android_etong.bean.Mine.MyCommentBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/8/13.
 */
public class OtherCommentFragment  extends Fragment{

    private Context context;
    private ListView listView;
    private List<MyCommentBean.DataBean> dataBeanList;
    private MyCommentAdapter myCommentAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.MY_COMMENT:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                MyCommentBean.DataBean dataBean = new MyCommentBean.DataBean();
                                dataBean.setId(dataObject.getString("id"));
                                dataBean.setOrderid(dataObject.getString("orderid"));
                                dataBean.setReceivetype(dataObject.getString("receivetype"));
                                dataBean.setContent(dataObject.getString("content"));
                                dataBean.setPhoto(dataObject.getString("photo"));
                                dataBean.setAttitudescore(dataObject.getString("attitudescore"));
                                dataBean.setFinishscore(dataObject.getString("finishscore"));
                                dataBean.setEffectscore(dataObject.getString("effectscore"));
                                dataBean.setAdd_time(dataObject.getString("add_time"));
                                dataBean.setStatus(dataObject.getString("status"));
                                JSONArray jsonArray1 = dataObject.getJSONArray("goods_info");
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                                dataBean.setGoodsname(jsonObject1.getString("goodsname"));
                                dataBean.setPicture(jsonObject1.getString("picture"));
                                dataBeanList.add(dataBean);
                            }
                            myCommentAdapter = new MyCommentAdapter(context, dataBeanList);
                            listView.setAdapter(myCommentAdapter);
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
        View view = inflater.inflate(R.layout.mine_my_comment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        if (NetUtil.isConnnected(context)) {
            new MineRequest(context, handler).myComment("2");
        }
    }

    private void initView() {
        listView = (ListView) getView().findViewById(R.id.my_comment_list);
        dataBeanList = new ArrayList<>();
    }
}
