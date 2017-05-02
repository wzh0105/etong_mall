package cn.xiaocool.android_etong.fragment.Local;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.ServiceAdapter;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/8/9.
 */
public class Service_All_Fragment extends Fragment {
    private Context context;
    private ListView list_service;
    private String city;
    private ServiceAdapter serviceAdapter;
    private List<Local> locals;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GET_SHOP_LIST:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            locals.clear();
                            Log.e("success", "加载数据");
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String shopid = object.getString("id");
                                String shopname = object.getString("shopname");
                                String photo = object.getString("photo");
                                String levle = object.getString("level");
                                String address = object.getString("address");
                                String state1 = object.getString("state");
                                Log.e("succees","店铺数据加载");
                                Local local = new Local();
                                local.setShopid(shopid);
                                local.setShopname(shopname);
                                local.setPhoto(photo);
                                local.setLevel(levle);
                                local.setAddress(address);
                                local.setState(state1);
                                locals.add(local);
                            }if (serviceAdapter!=null){
                                Log.e("success","设置适配器");
                                serviceAdapter.notifyDataSetChanged();
                            }else {
                                Log.e("success","设置适配器");
                                serviceAdapter = new ServiceAdapter(context,locals);
                                list_service.setAdapter(serviceAdapter);
                                setListViewHeightBasedOnChildren(list_service);
                            }
                        }else{
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_service_all,container,false);
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        city = getArguments().getString("city");
        initView();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).GetShopList(city,"7");
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        list_service = (ListView)getView().findViewById(R.id.list_service);
        locals = new ArrayList<>();
    }

    /*
      解决scrollview下listview显示不全
    */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
