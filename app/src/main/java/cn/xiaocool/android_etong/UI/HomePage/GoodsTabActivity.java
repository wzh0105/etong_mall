package cn.xiaocool.android_etong.UI.HomePage;


import android.app.Activity;


import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.Find_tab_Adapter;
import cn.xiaocool.android_etong.bean.HomePage.MenuTypeList;
import cn.xiaocool.android_etong.fragment.HomeDividerFragment;
import cn.xiaocool.android_etong.fragment.HomepageFragment;

public class GoodsTabActivity extends AppCompatActivity {


    private Find_tab_Adapter fAdapter;
    private ViewPager mViewPager;
    private TabLayout tab;
    private List<Fragment> list_fragment;
    private List<String> list_title;
    private List<MenuTypeList.ChildlistBeanX.ChildlistBean> menuTypeLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_tab);

        list_fragment = new ArrayList<>();
        list_title = new ArrayList<>();

        menuTypeLists = (List<MenuTypeList.ChildlistBeanX.ChildlistBean>) getIntent().getSerializableExtra("menu");

        mViewPager = (ViewPager) findViewById(R.id.container);
        tab = (TabLayout)findViewById(R.id.tab);
        findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView title = (TextView) findViewById(R.id.top_title);
        title.setText(getIntent().getStringExtra("title"));
        setMenuData();

    }


    private void setMenuData() {
        list_fragment.clear();
        list_title.clear();
        for (int i = 0; i < menuTypeLists.size(); i++) {
            list_title.add(menuTypeLists.get(i).getName());
            HomeDividerFragment fragment = new HomeDividerFragment();
            fragment.type = menuTypeLists.get(i).getTerm_id();
            list_fragment.add(fragment);
        }

        //设置显示菜单
        setFragment(list_title,list_fragment);
    }

    private void setFragment(List<String> list_title, List<android.support.v4.app.Fragment> list_fragment) {
        //设置TabLayout的模式
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        //为TabLayout添加tab名称
        for (int i = 0; i < list_fragment.size(); i++) {
            tab.addTab(tab.newTab().setText(list_title.get(i)));
        }
        fAdapter = new Find_tab_Adapter(getSupportFragmentManager(),list_fragment,list_title);
        //viewpager加载adapter
        mViewPager.setAdapter(fAdapter);
        //tab.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab.setupWithViewPager(mViewPager);
    }





}
