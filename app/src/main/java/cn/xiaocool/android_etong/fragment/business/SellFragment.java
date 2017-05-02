package cn.xiaocool.android_etong.fragment.business;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.SellListAdapter;
import cn.xiaocool.android_etong.bean.business.sell;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/15.
 */
public class SellFragment extends Fragment {
    //fragment交互接口
    public interface MyListener{
        public void showMessage(int index);
    }
    private MyListener myListener;
    private String shopid;
    private ArrayList<sell.DataBean> selllist;
    private Context context;
    private ListView list_sell;
    private SellListAdapter sellListAdapter;
    private TextView tx_no_content;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GETSHOPGOODLIST:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            progressDialog.dismiss();
                            selllist.clear();
                            Log.e("success", "getshopgoodlist");
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            Log.e("jsonArray",jsonObject.getString("data"));
                            int length = jsonarray.length();
                            JSONObject json;
                            for (int i = 0;i<length;i++){
                                json = (JSONObject) jsonarray.get(i);
                                sell.DataBean databean = new sell.DataBean();
                                String pic = json.getString("picture");
//                                String[] arraypic = pic.split("[,]");
//                                Log.e("第一张图片名称",arraypic[0]);
                                databean.setGoodsname(json.getString("goodsname"));
                                databean.setPicture(pic);
                                databean.setPrice(json.getString("price"));
                                databean.setShopid(json.getString("shopid"));
                                databean.setOprice(json.getString("oprice"));
                                databean.setId(json.getString("id"));
                                selllist.add(databean);
                            }
                            if(sellListAdapter!=null){
                                sellListAdapter.notifyDataSetChanged();
                            }else {
                                if (selllist.isEmpty()){
                                    Log.e("sell list","is empty");
                                    tx_no_content.setVisibility(View.VISIBLE);
                                }else {
                                    Log.e("sell list","is not empty");
                                    tx_no_content.setVisibility(View.GONE);
                                    sellListAdapter = new SellListAdapter(context,selllist,handler,myListener);
                                    list_sell.setAdapter(sellListAdapter);
                                }
                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GOODSXIAJIA:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        String data = jsonObject.getString("data");
                        if (state.equals("success")) {
                            Toast.makeText(context, "下架成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.DELETEGOODS:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
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
        View view = inflater.inflate(R.layout.fragment_sell,container,false);
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       Bundle bundle = getArguments();
        if(bundle!=null){
            shopid = bundle.getString("shopid");
            Log.e("s hopid=",shopid);
        }
        initview();
        initdata();
    }

    private void initview() {
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        selllist = new ArrayList<sell.DataBean>();
        list_sell = (ListView) getView().findViewById(R.id.list_sell);
        tx_no_content = (TextView) getView().findViewById(R.id.tx_no_content);
    }

    private void initdata() {
        if(NetUtil.isConnnected(context)){
            new MainRequest(context,handler).getshopgoodlist(shopid);
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            myListener = (MyListener) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName()
                    +" must implements interface MyListener");
        }
    }
    public void updataUI() {
        progressDialog.setMessage("正在加载");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        if(NetUtil.isConnnected(context)){
            new MainRequest(context,handler).getshopgoodlist(shopid);
        }else {
            Toast.makeText(context,"网络不稳定",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

}
