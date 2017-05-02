package cn.xiaocool.android_etong.fragment;


//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.HomePage.SearchActivity;
import cn.xiaocool.android_etong.adapter.EverydayGoodShopAdapter;
import cn.xiaocool.android_etong.adapter.Find_tab_Adapter;
import cn.xiaocool.android_etong.adapter.HomepageGuessLikeAdapter;
import cn.xiaocool.android_etong.bean.HomePage.EveryDayGoodShopBean;
import cn.xiaocool.android_etong.bean.HomePage.GuessLikeBean;
import cn.xiaocool.android_etong.bean.HomePage.MenuTypeList;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.SPUtils;
import cn.xiaocool.android_etong.view.etongApplaction;
//import cn.xiaocool.android_etong.UI.HomePage.TypeListActivity;
//import cn.xiaocool.android_etong.UI.Main.EverydayBargainActivity;
//import cn.xiaocool.android_etong.UI.Main.EverydayChoicenessActivity;
//import cn.xiaocool.android_etong.UI.Main.FlashSaleActivity;
//import cn.xiaocool.android_etong.UI.Main.NewArrivalActivity;
//import cn.xiaocool.android_etong.UI.Main.QualityLifeActivity;
//import cn.xiaocool.android_etong.UI.Main.ZeroActivity;
//import cn.xiaocool.android_etong.util.IntentUtils;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment implements View.OnClickListener{



    private TabLayout tab_FindFragment_title;
    private ViewPager vp_FindFragment_pager;

    private Find_tab_Adapter fAdapter;

    private List<Fragment> list_fragment;
    private List<String> list_title;
    private List<MenuTypeList> menuTypeLists;


    private TextView et_search;
    private Context context;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_MENU:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        if (jsonObject2.getString("status").equals("success")) {
                            menuTypeLists.clear();
                           menuTypeLists.addAll(getBeanFromJson(jsonObject2.toString()));
                            SPUtils.put(etongApplaction.getInstance().getApplicationContext(), WebAddress.GET_TYPE_LIST,jsonObject2.toString());
                            setMenuData();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private List<MenuTypeList> getBeanFromJson(String result) {
        String data = "";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(data, new TypeToken<List<MenuTypeList>>() {
        }.getType());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        new HomeRequest(context, handler).getGoodsTypeList("0");//获取一级菜单列表
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initView(View view) {

        menuTypeLists = new ArrayList<>();

        et_search = (TextView) view.findViewById(R.id.et_search);
        et_search.clearFocus();
        et_search.setOnClickListener(this);

        tab_FindFragment_title = (TabLayout)view.findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (ViewPager)view.findViewById(R.id.vp_FindFragment_pager);

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_title = new ArrayList<>();

        String menu = (String) SPUtils.get(etongApplaction.getInstance().getApplicationContext(),WebAddress.GET_TYPE_LIST,"");
        if (!menu.equals("")){
            menuTypeLists.addAll(getBeanFromJson(menu));
        }

        //初始化菜单上数据
        setMenuData();


    }

    private void setMenuData() {
        list_fragment.clear();
        list_title.clear();
        list_title.add("首页");
        list_fragment.add(new HomepageFragment());

        for (int i = 0; i < menuTypeLists.size(); i++) {
            HomeDividerFragment fragment = new HomeDividerFragment();
            list_title.add(menuTypeLists.get(i).getName());
            fragment.childlistBeanXs = menuTypeLists.get(i).getChildlist();
            fragment.preMenu = menuTypeLists.get(i);
            fragment.type = menuTypeLists.get(i).getTerm_id();
            list_fragment.add(fragment);
        }

        //设置显示菜单
        setFragment(list_title,list_fragment);
    }

    private void setFragment(List<String> list_title, List<Fragment> list_fragment) {
        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        //为TabLayout添加tab名称
        for (int i = 0; i < list_fragment.size(); i++) {
            tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(i)));
        }
        fAdapter = new Find_tab_Adapter(getChildFragmentManager(),list_fragment,list_title);
        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.et_search:
                Intent intent = new Intent();
                intent.setClass(context, SearchActivity.class);
                intent.putExtra("city", "homepage");
                startActivity(intent);
                break;
        }

    }


}
