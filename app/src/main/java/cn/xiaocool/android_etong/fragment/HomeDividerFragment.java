package cn.xiaocool.android_etong.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.HomePage.GoodsTabActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.adapter.DivideListAdapter;
import cn.xiaocool.android_etong.adapter.TopDivideAdapter;
import cn.xiaocool.android_etong.bean.HomePage.MenuTypeList;
import cn.xiaocool.android_etong.bean.HomePage.TypeGoodsList;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;
import cn.xiaocool.android_etong.util.NoScrollGridView;
import cn.xiaocool.android_etong.util.SPUtils;
import cn.xiaocool.android_etong.view.CustomerFooter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDividerFragment extends BaseFragment {


    @BindView(R.id.top_divide)
    NoScrollGridView topDivide;
    @BindView(R.id.table_divide)
    NoScrollGridView tableDivide;
    @BindView(R.id.xscrollview)
    XScrollView xscrollview;
    @BindView(R.id.xrefresh_view)
    XRefreshView xrefreshView;

    public List<MenuTypeList.ChildlistBeanX> childlistBeanXs = new ArrayList<>();
    public String type = "0";
    private List<TypeGoodsList> typeGoodsLists;
    private DivideListAdapter adapter;
    public MenuTypeList preMenu;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_MENU:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    xrefreshView.stopRefresh();
                    try {
                        if (jsonObject2.getString("status").equals("success")) {
                            typeGoodsLists.clear();
                            typeGoodsLists.addAll(getBeanFromJson(jsonObject2.toString()));
                            setAdapter();
                        }else {
                            typeGoodsLists.addAll(getBeanFromJson((String) SPUtils.get(mContext, WebAddress.GET_GOODS_LIST,"")));
                            setAdapter();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private void setAdapter() {

        if (adapter == null) {
            adapter = new DivideListAdapter(mContext,typeGoodsLists,tableDivide);
            tableDivide.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    private List<TypeGoodsList> getBeanFromJson(String result) {
        String data = "";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<List<TypeGoodsList>>() {
        }.getType());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_divider, container, false);
            ButterKnife.bind(this, rootView);
        }

        setRefreshView();
        setMenuData();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("destroyItem","type"+type);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeGoodsLists = new ArrayList<>();
    }

    private void setRefreshView() {
        xscrollview.setOnScrollListener(new XScrollView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(ScrollView view, int scrollState, boolean arriveBottom) {
            }

            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
            }
        });
        xrefreshView.setAutoRefresh(false);
        xrefreshView.setPullLoadEnable(true);
        xrefreshView.setPinnedTime(1000);
        xrefreshView.setAutoLoadMore(false);
//		outView.setSilenceLoadMore();
        xrefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrefreshView.stopLoadMore();
                    }
                }, 2000);
            }

            @Override
            public void onRefresh() {
                handler.post(LOAD_DATA);
//                new HomeRequest(mContext,handler).GetTypeGoodList(type);
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xrefreshView.stopRefresh();
                    }
                }, 2000);
            }
        });
        xrefreshView.setCustomFooterView(new CustomerFooter(mContext));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setMenuData() {
        final List<MenuTypeList.ChildlistBeanX> list = new ArrayList<>();
        if (childlistBeanXs != null){
            for (int i = 0; i < childlistBeanXs.size(); i++) {
                if (childlistBeanXs.get(i).getIshot()!=null){
                    if (childlistBeanXs.get(i).getIshot().equals("1")){
                        list.add(childlistBeanXs.get(i));
                    }
                }
            }
        }

        //添加全部按钮
        if (preMenu!=null){
            List<MenuTypeList.ChildlistBeanX.ChildlistBean> alist = new ArrayList<>();
            MenuTypeList.ChildlistBeanX all = new MenuTypeList.ChildlistBeanX();
            all.setName("查看全部");
            all.setHaschild(preMenu.getHaschild());
            all.setPhoto("全部");
            all.setTerm_id(preMenu.getTerm_id());
            if (childlistBeanXs!=null){
                for (MenuTypeList.ChildlistBeanX bean : childlistBeanXs){
                    MenuTypeList.ChildlistBeanX.ChildlistBean a = new MenuTypeList.ChildlistBeanX.ChildlistBean();
                    a.setTerm_id(bean.getTerm_id());
                    a.setPhoto(bean.getPhoto());
                    a.setName(bean.getName());
                    a.setIshot(bean.getIshot());
                    alist.add(a);
                }
                all.setChildlist(alist);
                //删除多余的显示的数据
                List<MenuTypeList.ChildlistBeanX> temp = new ArrayList<>();
                if (list.size()>7){
                    for (int i = 0; i < 7; i++) {
                      temp.add(list.get(i));
                    }
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        temp.add(list.get(i));
                    }
                }

                //将查看全部加到最后一个
                list.clear();
                list.addAll(temp);
                list.add(all);

            }

        }

        TopDivideAdapter topDivideAdapter = new TopDivideAdapter(mContext, R.layout.item_top_divide,list);

        for (int i = 0; i < (list.size()>8?8:list.size()); i++) {
            topDivideAdapter.add(list.get(i).getName());
        }
        topDivide.setAdapter(topDivideAdapter);

        topDivide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, GoodsTabActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("menu", (Serializable) list.get(i).getChildlist());
                intent.putExtras(bundle);
                intent.putExtra("title",list.get(i).getName());
                startActivity(intent);
            }
        });


        tableDivide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TypeGoodsList bean = typeGoodsLists.get(i);
                Intent intent = new Intent();
                intent.setClass(mContext, GoodsDetailActivity.class);
                intent.putExtra("id", bean.getId());//传出goodId
                intent.putExtra("artno", bean.getArtno());
                intent.putExtra("shopid", bean.getShopid());//传出shopid
                intent.putExtra("brand", bean.getBrand());
                intent.putExtra("goodsname", bean.getGoodsname());
                intent.putExtra("adtitle", bean.getAdtitle());
                intent.putExtra("oprice", bean.getOprice());
                intent.putExtra("price", bean.getPrice());//传出price
                intent.putExtra("unit", bean.getUnit());
                intent.putExtra("description", bean.getDescription());
                intent.putExtra("pic", bean.getPicture());//传出pic
                intent.putExtra("showid", bean.getShowid());
                intent.putExtra("address", bean.getAddress());
                intent.putExtra("freight", bean.getFreight());
                intent.putExtra("pays", bean.getPays());
                intent.putExtra("racking", bean.getRacking());
                intent.putExtra("recommend", bean.getRecommend());
                intent.putExtra("shopname", bean.getShopname());//店铺名字
                intent.putExtra("sales", bean.getSales());
                intent.putExtra("paynum", bean.getPaynum());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (typeGoodsLists.size()<1){
                xrefreshView.startRefresh();
            }


//            handler.postDelayed(LOAD_DATA,500);
        } else {
//            handler.removeCallbacks(LOAD_DATA);
            xrefreshView.stopRefresh();
        }

    }



    private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {
            //在这里数据内容加载到Fragment上
            Log.e("LOAD_DATA","LOAD_DATA");



            new HomeRequest(mContext,handler).GetTypeGoodList(type);
        }
    };
}
