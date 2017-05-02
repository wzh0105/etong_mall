package cn.xiaocool.android_etong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xiaocool on 17/2/6.
 */

public class Find_tab_Adapter extends FragmentPagerAdapter {

    private List<Fragment> listfragment;                         //fragment列表
    private List<String> listTitle;                              //tab名的列表



    public Find_tab_Adapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
        super(fm);
        this.listfragment = list_fragment;
        this.listTitle = list_Title;
    }



    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return listTitle.get(position % listTitle.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Log.e("destroyItem",position+"");
    }

}
