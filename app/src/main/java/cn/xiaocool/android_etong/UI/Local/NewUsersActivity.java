package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.NewUsersAdapter;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.fragment.Local.NewUsers_All_Fragment;
import cn.xiaocool.android_etong.fragment.Local.NewUsers_Distence_Fragment;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/8/10.
 */
public class NewUsersActivity extends Activity implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private RelativeLayout rl_back;
    private ScrollView scroll;
    private SliderLayout mDemoSlider;
    private NewUsers_All_Fragment newUser_all_fragment;
    private NewUsers_Distence_Fragment newUser_distence_Fragment;
    private Button[] mTabs;
    private Fragment[] fragments;
    private int index, currentIndex;
    private ListView list_New_Users;
    private Context context;
    private NewUsersAdapter newUsersAdapter;
    private List<Local> locals;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.IsNew:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            locals.clear();
                            Log.e("success", "加载数据");
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Local local = new Local();
                                local.setShopid(jsonObject1.getString("shopid"));
                                local.setId(jsonObject1.getString("id"));
                                local.setGoodsname(jsonObject1.getString("goodsname"));
                                local.setPicture(jsonObject1.getString("picture"));
                                local.setDescription(jsonObject1.getString("description"));
                                local.setPrice(jsonObject1.getString("price"));
                                local.setOprice(jsonObject1.getString("oprice"));
                                local.setFreight(jsonObject1.getString("freight"));
                                JSONArray jsonArray1 = jsonObject1.getJSONArray("shop_list");
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                                local.setShopname(jsonObject2.getString("shopname"));
                                local.setLevel(jsonObject2.getString("level"));
                                locals.add(local);
                                Log.e("succees", "商品数据加载");
                            }if (newUsersAdapter!=null){
                                Log.e("success", "设置适配器");
                                newUsersAdapter.notifyDataSetChanged();
                            }else {
                                Log.e("success","设置适配器");
                                newUsersAdapter = new NewUsersAdapter(context,locals);
                                list_New_Users.setAdapter(newUsersAdapter);
                                  setListViewHeightBasedOnChildren(list_New_Users);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_users);
        context = this;
        initView();
        initData();
//        initFragment();
        initBtn();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).IsNew();
        }else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        locals = new ArrayList<>();
        list_New_Users = (ListView) findViewById(R.id.list_New_Users);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        scroll = (ScrollView) findViewById(R.id.scroll);
    }

    private void initData() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

//    private void initFragment() {
//        newUser_all_fragment = new NewUsers_All_Fragment();
//        newUser_distence_Fragment = new NewUsers_Distence_Fragment();
//        fragments = new Fragment[]{newUser_all_fragment,newUser_distence_Fragment};
//        getFragmentManager().beginTransaction().add(R.id.fragment_container,newUser_all_fragment).show(newUser_all_fragment).commit();
//        initBtn();
//    }

    private void initBtn() {
        mTabs = new Button[2];
        mTabs[0] = (Button) findViewById(R.id.btn_all);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_nearly);
        mTabs[1].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_all:
                index = 0;
                break;
            case R.id.btn_nearly:
                index = 1;
                break;
        }
//        if (currentIndex != index) {
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.hide(fragments[currentIndex]);
//            if (!fragments[index].isAdded()) {
//                fragmentTransaction.add(R.id.fragment_container, fragments[index]);
//            }
//            fragmentTransaction.show(fragments[index]);
//            fragmentTransaction.commit();
//        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }



    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
