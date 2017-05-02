package cn.xiaocool.android_etong.fragment;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.Map;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.HomePage.SearchActivity;
import cn.xiaocool.android_etong.UI.HomePage.TypeListActivity;
import cn.xiaocool.android_etong.UI.Main.EverydayBargainActivity;
import cn.xiaocool.android_etong.UI.Main.EverydayChoicenessActivity;
import cn.xiaocool.android_etong.UI.Main.FlashSaleActivity;
import cn.xiaocool.android_etong.UI.Main.NewArrivalActivity;
import cn.xiaocool.android_etong.UI.Main.QualityLifeActivity;
import cn.xiaocool.android_etong.UI.Main.ZeroActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.adapter.EverydayGoodShopAdapter;
import cn.xiaocool.android_etong.adapter.HomepageGuessLikeAdapter;
import cn.xiaocool.android_etong.bean.HomePage.EveryDayGoodShopBean;
import cn.xiaocool.android_etong.bean.HomePage.GuessLikeBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NoScrollGridView;
import cn.xiaocool.android_etong.util.ReboundScrollView;
import cn.xiaocool.android_etong.view.HorizontalListView;

import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;

/**
 * Created by 潘 on 2016/6/12.
 */
public class HomepageFragment extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private Context context;
    private SliderLayout mDemoSlider;
    private TextView et_search;
    private RelativeLayout rl_meirijingxuan;
    private RelativeLayout rl_bestshop_left, rl_bestshop_right, rlNewArrival, rlEverydayChoiceness, rlzero,llQualityLife;
    private LinearLayout rlEverydayBargain , llFlashSale;
    private ImageView typeBtn;
    private RelativeLayout rl_search;
    private NoScrollGridView gridView0, gridView1;
    private List<EveryDayGoodShopBean.DataBean> dataBeenList;
    private List<GuessLikeBean.GuessLikeDataBean> guessLikeDataBeanList;
    private EverydayGoodShopAdapter everydayGoodShopAdapter;
    private HomepageGuessLikeAdapter homepageGuessLikeAdapter;
    private ReboundScrollView reboundScrollView;
    private LinearLayout llTop;

    private RelativeLayout rlTopBar;
    private ImageView ivLeft;
    private PopupWindow popLeft;
    private View layoutLeft;
    private GridView menulistLeft;
    private List<Map<String, String>> listLeft;

    private HorizontalListView fenlei_horlist;
    private SliderLayout sliderLayout;


    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_HOMEPAGE_EVERY_GOODSHOP:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                EveryDayGoodShopBean.DataBean dataBean = new EveryDayGoodShopBean.DataBean();
                                dataBean.setId(dataObject.getString("id"));
                                dataBean.setShopname(dataObject.getString("shopname"));
                                dataBean.setPhoto(dataObject.getString("photo"));
                                dataBean.setAddress(dataObject.getString("address"));
                                dataBean.setLikeNum("1220");
                                dataBeenList.add(dataBean);
                            }
                            everydayGoodShopAdapter = new EverydayGoodShopAdapter(context, dataBeenList);
                            gridView0.setAdapter(everydayGoodShopAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GET_GUESS_LIKE:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            int length = jsonArray.length();
                            JSONObject dataObject;
                            for (int i = 0; i < length; i++) {
                                dataObject = (JSONObject) jsonArray.get(i);
                                GuessLikeBean.GuessLikeDataBean guessLikeDataBean = new GuessLikeBean.GuessLikeDataBean();
                                guessLikeDataBean.setId(dataObject.getString("id"));
//                                guessLikeDataBean.setArtno(dataObject.getString("artno"));
                                guessLikeDataBean.setShopid(dataObject.getString("shopid"));
//                                guessLikeDataBean.setBrand(dataObject.getString("brand"));
                                guessLikeDataBean.setGoodsname(dataObject.getString("goodsname"));
//                                guessLikeDataBean.setAdtitle(dataObject.getString("adtitle"));
                                guessLikeDataBean.setOprice(dataObject.getString("oprice"));
                                guessLikeDataBean.setPrice(dataObject.getString("price"));
                                guessLikeDataBean.setUnit(dataObject.getString("unit"));
                                guessLikeDataBean.setDescription(dataObject.getString("description"));
                                guessLikeDataBean.setPicture(dataObject.getString("picture"));
                                guessLikeDataBean.setShowid(dataObject.getString("showid"));
                                guessLikeDataBean.setAddress(dataObject.getString("address"));
                                guessLikeDataBean.setFreight(dataObject.getString("freight"));
                                guessLikeDataBean.setPays(dataObject.getString("pays"));
                                guessLikeDataBean.setRacking(dataObject.getString("racking"));
                                guessLikeDataBean.setRecommend(dataObject.getString("recommend"));

                                guessLikeDataBeanList.add(guessLikeDataBean);
                            }
                            homepageGuessLikeAdapter = new HomepageGuessLikeAdapter(context, guessLikeDataBeanList);
                            gridView1.setAdapter(homepageGuessLikeAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GET_MENU:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        Log.e("a11", jsonObject2.getString("status"));
                        if (jsonObject2.getString("status").equals("success")) {
                            JSONArray jsonArray = jsonObject2.getJSONArray("data");
                            length = jsonArray.length();
                            Log.e("herelength", String.valueOf(length));
                            typeName = new String[length];
                            JSONObject jsonObject3;
                            for (int i = 0; i < length; i++) {
                                jsonObject3 = (JSONObject) jsonArray.get(i);
                                typeName[i] = jsonObject3.getString("levelone_name");
                                HashMap<String, String> mapTemp = new HashMap<String, String>();
                                mapTemp.put("item", typeName[i]);
                                listLeft.add(mapTemp);
                                Log.e("typename", typeName[i]);
                            }
                            SimpleAdapter listAdapter = new SimpleAdapter(
                                    context, listLeft, R.layout.pop_menuitem,
                                    new String[]{"item"},
                                    new int[]{R.id.menuitem});
                            fenlei_horlist.setAdapter(listAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private String[] typeName;
    private int length;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initdata();
        initview();
        new HomeRequest(context, handler).getEveryDayShop();//获取每日好店
        new HomeRequest(context, handler).getGuessLike();//获取猜你喜欢
        new HomeRequest(context, handler).getMenu("", "");//获取一级菜单列表
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();

        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void initParam() {
        rlTopBar = (RelativeLayout) this.getView().findViewById(R.id.rl_topbar);

        llTop = (LinearLayout) getView().findViewById(R.id.ll_top);
        ivLeft = (ImageView) this.getView().findViewById(R.id.homepage_type_img);
        ivLeft.setOnClickListener(myListener);
        // 初始化数据项
        listLeft = new ArrayList<Map<String, String>>();
        llTop.getBackground().mutate().setAlpha(0);
    }


    private View.OnClickListener myListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.homepage_type_img:
                    if (popLeft != null && popLeft.isShowing()) {
                        popLeft.dismiss();
                    } else {
                        layoutLeft = getActivity().getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistLeft = (GridView) layoutLeft
                                .findViewById(R.id.menulist);
                        SimpleAdapter listAdapter = new SimpleAdapter(
                                context, listLeft, R.layout.pop_menuitem,
                                new String[]{"item"},
                                new int[]{R.id.menuitem});
                        menulistLeft.setAdapter(listAdapter);

                        // 点击listview中item的处理
                        menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0,
                                                    View arg1, int arg2, long arg3) {
                                // 改变顶部对应TextView值
                                String strItem = listLeft.get(arg2).get(
                                        "item");
//                                        tvLeft.setText(strItem);
                                //跳转二级列表
                                Intent intent = new Intent();
                                intent.putExtra("strItem", strItem);
                                intent.setClass(context, TypeListActivity.class);
                                startActivity(intent);
                                // 隐藏弹出窗口
                                if (popLeft != null && popLeft.isShowing()) {
                                    popLeft.dismiss();
                                }
                            }
                        });

                        // 创建弹出窗口
                        // 窗口内容为layoutLeft，里面包含一个ListView
                        // 窗口宽度跟tvLeft一样
                        popLeft = new PopupWindow(layoutLeft, LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        //设置背景半透明
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 0.3f;
                        getActivity().getWindow().setAttributes(lp);
//                        backgroundAlpha(1f);
                        ColorDrawable cd = new ColorDrawable(0x0000);
                        popLeft.setBackgroundDrawable(cd);
//                        popLeft.setAnimationStyle(R.style.popupAnimation);
                        popLeft.update();
                        popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popLeft.setTouchable(true); // 设置popupwindow可点击
                        popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        popLeft.setFocusable(true); // 获取焦点

                        // 设置popupwindow的位置（相对tvLeft的位置）
                        int topBarHeight = rlTopBar.getBottom();
                        popLeft.showAsDropDown(ivLeft, 0,
                                (topBarHeight - ivLeft.getHeight()) / 4);

                        popLeft.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    popLeft.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });
                        popLeft.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                backgroundAlpha(1f);
                            }
                        });
                    }
                    break;


                default:
                    break;
            }
        }

    };


    private void initview() {
        fenlei_horlist = (HorizontalListView) getView().findViewById(R.id.fenlei_horlist);
        // 点击listview中item的处理
        fenlei_horlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int arg2, long arg3) {
                // 改变顶部对应TextView值
                String strItem = listLeft.get(arg2).get(
                        "item");
//                                        tvLeft.setText(strItem);
                //跳转二级列表
                Intent intent = new Intent();
                intent.putExtra("strItem", strItem);
                intent.setClass(context, TypeListActivity.class);
                startActivity(intent);
            }
        });
        et_search = (TextView) getView().findViewById(R.id.et_search);
        et_search.clearFocus();
        et_search.setOnClickListener(this);
        rlNewArrival = (RelativeLayout) getView().findViewById(R.id.homepage_rl_new_arrival);
        rlNewArrival.setOnClickListener(this);
        typeBtn = (ImageView) getView().findViewById(R.id.homepage_type_img);
        typeBtn.setOnClickListener(this);
        llQualityLife = (RelativeLayout) getView().findViewById(R.id.homepage_ll_quality_life);
        llQualityLife.setOnClickListener(this);
        llFlashSale = (LinearLayout) getView().findViewById(R.id.homepage_ll_flash_sale);
        llFlashSale.setOnClickListener(this);
        rlEverydayBargain = (LinearLayout) getView().findViewById(R.id.homepage_rl_everyday_bargain_price);
        rlEverydayBargain.setOnClickListener(this);
        rlEverydayChoiceness = (RelativeLayout) getView().findViewById(R.id.homepage_rl_everyday_choiceness);
        rlEverydayChoiceness.setOnClickListener(this);
        gridView0 = (NoScrollGridView) getView().findViewById(R.id.homepage_everyday_goodshop_gridview);
        gridView1 = (NoScrollGridView) getView().findViewById(R.id.homepage_guess_like_gridview);
        getView().findViewById(R.id.etong_home1).setOnClickListener(this);
        getView().findViewById(R.id.etong_home2).setOnClickListener(this);
        dataBeenList = new ArrayList<>();
        guessLikeDataBeanList = new ArrayList<>();
        initParam();
        rl_search = (RelativeLayout) getView().findViewById(R.id.rl_search);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rl_search.getLayoutParams();
        linearParams.height = getStatusBarHeight(context);
        rl_search.setLayoutParams(linearParams);
        reboundScrollView = (ReboundScrollView) getView().findViewById(R.id.a);

        reboundScrollView.setOnScrollChangedListener(new ReboundScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                if (mDemoSlider != null && mDemoSlider.getHeight() > 0) {
                    int lHeight = mDemoSlider.getHeight();
                    if (t < lHeight) {
                        int progress = (int) (new Float(t) / new Float(lHeight) * 200);
                        llTop.getBackground().setAlpha(progress);
                    } else {
                        llTop.getBackground().setAlpha(255);
                    }
                }
            }
        });
        rlzero = (RelativeLayout) getView().findViewById(R.id.homepage_rl_zero);
        rlzero.setOnClickListener(this);


        gridView0.setFocusable(false);
        gridView1.setFocusable(false);

    }

    private void initdata() {
        mDemoSlider = (SliderLayout) getView().findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("新品上市", "http://hq.xiaocool.net/uploads/microblog/sp1.jpg");
        url_maps.put("推荐购买", "http://hq.xiaocool.net/uploads/microblog/sp2.jpg");
        url_maps.put("猜你喜欢", "http://hq.xiaocool.net/uploads/microblog/sp3.jpg");
        url_maps.put("每日特价", "http://hq.xiaocool.net/uploads/microblog/sp4.jpg");

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

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPause() {
        if (mDemoSlider != null) {
            mDemoSlider.stopAutoCycle();
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        final GuessLikeBean.GuessLikeDataBean bean;
        switch (v.getId()) {
            case R.id.homepage_rl_new_arrival:
                IntentUtils.getIntent((Activity) context, NewArrivalActivity.class);
                break;
            case R.id.homepage_ll_quality_life:
                IntentUtils.getIntent((Activity) context, QualityLifeActivity.class);
                break;
            case R.id.homepage_ll_flash_sale:
                IntentUtils.getIntent((Activity) context, FlashSaleActivity.class);
                break;
            case R.id.homepage_rl_everyday_bargain_price:
                IntentUtils.getIntent((Activity) context, EverydayBargainActivity.class);
                break;
            case R.id.homepage_rl_everyday_choiceness:
                IntentUtils.getIntent((Activity) context, EverydayChoicenessActivity.class);
                break;
            case R.id.et_search:
                intent.setClass(context, SearchActivity.class);
                intent.putExtra("city", "homepage");
                startActivity(intent);
                break;
            case R.id.homepage_type_img:
                IntentUtils.getIntent((Activity) context, TypeListActivity.class);
                break;
            case R.id.homepage_rl_zero:
                IntentUtils.getIntent((Activity) context, ZeroActivity.class);
                break;
            case R.id.etong_home1:
                if (guessLikeDataBeanList.size()<1){
                    return;
                }
                bean = guessLikeDataBeanList.get(0);
                intent.setClass(context, GoodsDetailActivity.class);
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
                context.startActivity(intent);
                break;
            case R.id.etong_home2:
                if (guessLikeDataBeanList.size()>1){
                    bean = guessLikeDataBeanList.get(1);
                    intent.setClass(context, GoodsDetailActivity.class);
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
                    context.startActivity(intent);
                }
                break;
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

}
