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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
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
import cn.xiaocool.android_etong.UI.HomePage.SearchActivity;
import cn.xiaocool.android_etong.UI.Local.AllClassifyActivity;
import cn.xiaocool.android_etong.UI.Local.AllStoreOrGoodsActivity;
import cn.xiaocool.android_etong.UI.Local.DailySpecialActivity;
import cn.xiaocool.android_etong.UI.Local.EntertainmentActivity;
import cn.xiaocool.android_etong.UI.Local.FoodActivity;
import cn.xiaocool.android_etong.UI.Local.HotelActivity;
import cn.xiaocool.android_etong.UI.Local.KtvActivity;
import cn.xiaocool.android_etong.UI.Local.MoiveActivity;
import cn.xiaocool.android_etong.UI.Local.NewUsersActivity;
import cn.xiaocool.android_etong.UI.Local.PanicBuyingActivity;
import cn.xiaocool.android_etong.UI.Local.RechargeActivity;
import cn.xiaocool.android_etong.UI.Local.ServiceActivity;
import cn.xiaocool.android_etong.UI.Local.TakeOutFoodAcitvity;
import cn.xiaocool.android_etong.UI.Local.TravelAroundActivity;
import cn.xiaocool.android_etong.adapter.GetBBSListAdapter;
import cn.xiaocool.android_etong.adapter.LocalAdapter;
import cn.xiaocool.android_etong.adapter.RankingAdapter;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.bean.business.LocationService;
import cn.xiaocool.android_etong.bean.json.Ranking;
import cn.xiaocool.android_etong.callback.ListRefreshCallBack;
import cn.xiaocool.android_etong.dao.ApiStores;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.fragment.Local.LocalAddressActivity;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

;import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;
import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class LocalFragment extends Fragment implements View.OnClickListener , BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private Context context;
    private TextView et_search,tv_local,rank_all_tv,goods_all_tv;
    private RelativeLayout ry_line;
    private LinearLayout ll_eqianggou,ll_xinkezhuanxiang,ll_jinritejia;
    private Button btn_quanbu,btn_meishi,btn_dianying,btn_jiudian,btn_waimai,btn_shenghuoyule,
            btn_zhoubianyou,btn_shenghuofuwu,btn_ktv,btn_shoujichongzhi;
    private LocalAdapter localAdapter;
    private ListView list_local,list_ranking;
    private List<Ranking.DataBean> list = new ArrayList<>();
    private List<Local> locals;
    private String city;
    private RankingAdapter rankingAdapter;
    private LocationService locationService;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.IsLike:
                    try {
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        String state=jsonObject.getString("status");
                        if (state.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            locals.clear();
                            Log.e("success", "加载数据");
                            int length = 2;
                            if (jsonArray.length()<3){
                                length = jsonArray.length();
                            }
                            for (int i = 0;i<length;i++){
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
                                locals.add(local);
                                Log.e("succees", "商品数据加载");
                            }if (localAdapter!=null){
                                Log.e("success", "设置适配器");
                                localAdapter.notifyDataSetChanged();
                            }else {
                                Log.e("success","设置适配器");
                                localAdapter = new LocalAdapter(context,locals);
                                list_local.setAdapter(localAdapter);
                                setListViewHeightBasedOnChildren(list_local);
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
        View view = inflater.inflate(R.layout.fragment_local,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        //设置状态栏高度
//        ry_line = (RelativeLayout)getView().findViewById(R.id.lin);
//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
//        linearParams.height = getStatusBarHeight(context);
//        ry_line.setLayoutParams(linearParams);
        initview();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).IsLike();
        }else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
        onrefrsh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("onHiddenChanged",""+hidden);
        if (!hidden){
            if (NetUtil.isConnnected(context)){
                new MainRequest(context,handler).IsLike();
                getCityList();
            }else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initview() {
        list_local = (ListView)getView().findViewById(R.id.list_local);
        list_ranking = (ListView) getView().findViewById(R.id.list_ranking);
        locals = new ArrayList<>();
        btn_quanbu = (Button)getView().findViewById(R.id.btn_quanbu);
        btn_quanbu.setOnClickListener(this);
        btn_meishi = (Button)getView().findViewById(R.id.btn_meishi);
        btn_meishi.setOnClickListener(this);
        btn_dianying = (Button)getView().findViewById(R.id.btn_dianying);
        btn_dianying.setOnClickListener(this);
        btn_jiudian = (Button)getView().findViewById(R.id.btn_jiudian);
        btn_jiudian.setOnClickListener(this);
        btn_waimai = (Button)getView().findViewById(R.id.btn_waimai);
        btn_waimai.setOnClickListener(this);
        btn_shenghuoyule = (Button)getView().findViewById(R.id.btn_shenghuoyule);
        btn_shenghuoyule.setOnClickListener(this);
        btn_zhoubianyou = (Button)getView().findViewById(R.id.btn_zhoubianyou);
        btn_zhoubianyou.setOnClickListener(this);
        btn_shenghuofuwu = (Button)getView().findViewById(R.id.btn_shenghuofuwu);
        btn_shenghuofuwu.setOnClickListener(this);
        btn_ktv = (Button)getView().findViewById(R.id.btn_ktv);
        btn_ktv.setOnClickListener(this);
        btn_shoujichongzhi = (Button)getView().findViewById(R.id.btn_shoujichongzhi);
        btn_shoujichongzhi.setOnClickListener(this);
        ll_eqianggou = (LinearLayout)getView().findViewById(R.id.ll_eqianggou);
        ll_eqianggou.setOnClickListener(this);
        ll_xinkezhuanxiang = (LinearLayout)getView().findViewById(R.id.ll_xinkezhuanxiang);
        ll_xinkezhuanxiang.setOnClickListener(this);
        ll_jinritejia = (LinearLayout)getView().findViewById(R.id.ll_jinritejia);
        ll_jinritejia.setOnClickListener(this);
        et_search = (TextView) getView().findViewById(R.id.et_search);
        et_search.clearFocus();
        et_search.setOnClickListener(this);
        tv_local = (TextView) getView().findViewById(R.id.tv_local);
        tv_local.setOnClickListener(this);
        rank_all_tv = (TextView) getView().findViewById(R.id.rank_all_tv);
        rank_all_tv.setOnClickListener(this);
        goods_all_tv = (TextView) getView().findViewById(R.id.goods_all_tv);
        goods_all_tv.setOnClickListener(this);
//        mDemoSlider = (SliderLayout) getView().findViewById(R.id.slider);
//
//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
//        url_maps.put("Big Bang Theory", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
//        url_maps.put("House of Cards", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
//        url_maps.put("Game of Thrones", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");
//
//        for(String name : url_maps.keySet()){
//            TextSliderView textSliderView = new TextSliderView(context);
//            textSliderView
//                    .description(name)
//                    .image(url_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(this);
//
//            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
//
//            mDemoSlider.addSlider(textSliderView);
//        }
//        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider.setDuration(4000);
//        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
//        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_quanbu:

//                intent.putExtra("city",tv_local.getText().toString());
//                intent.setClass(context, ShopListActivity.class);
                intent.setClass(context, AllClassifyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_meishi:
                Intent intent1 = new Intent();
                intent1.putExtra("city",tv_local.getText().toString());
                intent1.setClass(context, FoodActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_dianying:
                Intent intent2 = new Intent();
                intent2.setClass(context, MoiveActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_jiudian:
                Intent intent3 = new Intent();
                intent3.putExtra("city",tv_local.getText().toString());
                intent3.setClass(context, HotelActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_waimai:
                Intent intent4 = new Intent();
                intent4.putExtra("city", tv_local.getText().toString());
                intent4.setClass(context, TakeOutFoodAcitvity.class);
                startActivity(intent4);
                break;
            case R.id.btn_shenghuoyule:
                Intent intent5 = new Intent();
                intent5.setClass(context, EntertainmentActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_zhoubianyou:
                Intent intent6 = new Intent();
                intent6.putExtra("city", tv_local.getText().toString());
                intent6.setClass(context, TravelAroundActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_shenghuofuwu:
                Intent intent7 = new Intent();
                intent7.putExtra("city", tv_local.getText().toString());
                intent7.setClass(context, ServiceActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_ktv:
                Intent intent8 = new Intent();
                intent8.putExtra("city", tv_local.getText().toString());
                intent8.setClass(context, KtvActivity.class);
                startActivity(intent8);
                break;
            case R.id.btn_shoujichongzhi:
                Intent intent9 = new Intent();
                intent9.setClass(context, RechargeActivity.class);
                startActivity(intent9);
                break;
            case R.id.ll_eqianggou:
                Intent intent10 = new Intent();
                intent10.setClass(context, PanicBuyingActivity.class);
                startActivity(intent10);
                break;
            case R.id.ll_xinkezhuanxiang:
                Intent intent11 = new Intent();
                intent11.setClass(context, NewUsersActivity.class);
                startActivity(intent11);
                break;
            case R.id.ll_jinritejia:
                Intent intent12 = new Intent();
                intent12.setClass(context, DailySpecialActivity.class);
                startActivity(intent12);
                break;
            case R.id.et_search:
                Intent intent13 = new Intent();
                intent13.setClass(context, SearchActivity.class);
                intent13.putExtra("city", tv_local.getText().toString());
                startActivity(intent13);
                break;
            case R.id.tv_local:
                Intent intent14 = new Intent();
                intent14.setClass(context, LocalAddressActivity.class);
                intent14.putExtra("city",tv_local.getText().toString());
                startActivityForResult(intent14,1);
                break;
            case R.id.rank_all_tv:
                intent.setClass(context, AllStoreOrGoodsActivity.class);
                intent.putExtra("type","rank");
                intent.putExtra("city",tv_local.getText().toString());
                startActivity(intent);
                break;
            case R.id.goods_all_tv:
                intent.setClass(context, AllStoreOrGoodsActivity.class);
                intent.putExtra("type","goods");
                intent.putExtra("city",tv_local.getText().toString());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            city = data.getStringExtra("city");
            tv_local.setText(data.getStringExtra("city"));
            getCityList();
            Log.e("city=",data.getStringExtra("city"));
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        Toast.makeText(context, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
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
            // 计算子项View 的宽高(listitem需要时LinearLayout，否则容易报空指针)
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

    public void onrefrsh(){
        // -----------location config ------------
        locationService = ((cn.xiaocool.android_etong.view.etongApplaction)getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getActivity().getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
        // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    /*****
     * @see copy funtion to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.e("sb=", sb.toString());
                city = location.getCity();
                tv_local.setText(location.getCity());
                locationService.stop();
                getCityList();
            }
        }
    };

    public void getCityList() {
        list.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<Ranking> call = apiStores.GetLocalShopList(city,"0","1","0");

        call.enqueue(new Callback<Ranking>() {
            @Override
            public void onResponse(Call<Ranking> call, Response<Ranking> response) {
                list.addAll(response.body().getData());
                setAdapter();  //异步请求结束后，设置适配器
            }

            @Override
            public void onFailure(Call<Ranking> call, Throwable t) {
                Log.e("err", t.toString());
            }

        });
    }



    private boolean setAdapter() {
        if (rankingAdapter != null) {
            rankingAdapter.notifyDataSetChanged();
        } else {
            rankingAdapter = new RankingAdapter(context, list);
            list_ranking.setAdapter(rankingAdapter);
        }
        return  true;
    }

}
