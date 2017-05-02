package cn.xiaocool.android_etong.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Local.PublishInformationActivity;
import cn.xiaocool.android_etong.adapter.Find_tab_Adapter;

/**
 * Created by 潘 on 2016/6/12.
 */
public class PrefectureFragment extends Fragment {
    @BindView(R.id.tab_FindFragment_title)
    TabLayout tabFindFragmentTitle;
    @BindView(R.id.vp_FindFragment_pager)
    ViewPager vpFindFragmentPager;
    private Context context;
    private Find_tab_Adapter fAdapter;

    private List<Fragment> list_fragment;
    private List<String> list_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prefecture, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(new PrefectureMyFragment());
        list_fragment.add(new PrefectureProductFragment());

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("我的专区");
        list_title.add("农副产品专区");

        //设置TabLayout的模式
        tabFindFragmentTitle.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(list_title.get(0)));
        tabFindFragmentTitle.addTab(tabFindFragmentTitle.newTab().setText(list_title.get(1)));

        fAdapter = new Find_tab_Adapter(getChildFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        vpFindFragmentPager.setAdapter(fAdapter);
        //tabFindFragmentTitle.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabFindFragmentTitle.setupWithViewPager(vpFindFragmentPager);
    }

    @OnClick(R.id.img_edit)
    public void onClick() {
        startActivity(new Intent(context, PublishInformationActivity.class));
    }




}
