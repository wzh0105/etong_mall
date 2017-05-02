package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.MineFootprintActivity;
import cn.xiaocool.android_etong.adapter.StoreHomePageAdapter;
import cn.xiaocool.android_etong.bean.business.StoreHomepage;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.etongApplaction;

import static cn.xiaocool.android_etong.net.constant.WebAddress.SHARE_SHOP_TO_FRIEND;

/**
 * Created by 潘 on 2016/7/19.
 */
public class StoreHomepageActivity extends Activity implements View.OnClickListener {
    private Context context;
    private Button btn_shoucang;
    private String shopid, shopname, shop_uid, shop_photo;
    private RelativeLayout rl_back;
    private TextView tx_store_name;
    private Button btn_chat_store, btn_lianximaijia;
    private ImageView img_store_head;
    private GridView list_store_goods;
    private PullToRefreshScrollView sv;
    private ArrayList<StoreHomepage.DataBean> goods_list;
    private ArrayList<StoreHomepage.DataBean> newArrivalDataBeanListLoading;
    private StoreHomePageAdapter storeHomePageAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GETSHOPGOODLIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            goods_list.clear();
                            JSONArray jsonarray = jsonObject.getJSONArray("data");
                            Log.e("jsonArray", jsonObject.getString("data"));
                            int length = jsonarray.length();
                            JSONObject json;
                            for (int i = 0; i < length; i++) {
                                json = (JSONObject) jsonarray.get(i);
                                StoreHomepage.DataBean databean = new StoreHomepage.DataBean();
                                String pic = json.getString("picture");
//                                String[] arraypic = pic.split("[,]");
//                                Log.e("第一张图片名称",arraypic[0]);
                                databean.setGoodsname(json.getString("goodsname"));
                                databean.setPicture(pic);
                                databean.setPrice(json.getString("price"));
                                databean.setOprice(json.getString("oprice"));
                                databean.setShopid(json.getString("shopid"));
                                databean.setId(json.getString("id"));
                                databean.setShowid(shopname);
                                goods_list.add(databean);
                            }
                            if (goods_list.size() >= 6) {
                                for (int i = 0; i < 6; i++) {
                                    newArrivalDataBeanListLoading.add(goods_list.get(i));
                                }

                                ILoadingLayout endLayout = sv.getLoadingLayoutProxy(false, true);
                                endLayout.setPullLabel("正在上拉刷新...");
                                endLayout.setRefreshingLabel("正在玩命加载中...");
                                endLayout.setReleaseLabel("放开以刷新");

                            } else {
                                for (int i = 0; i < goods_list.size(); i++) {
                                    newArrivalDataBeanListLoading.add(goods_list.get(i));
                                }

                                ILoadingLayout endLayout = sv.getLoadingLayoutProxy(false, true);
                                endLayout.setPullLabel("正在上拉刷新...");
                                endLayout.setRefreshingLabel("已经没有更多宝贝了");
                                endLayout.setReleaseLabel("放开以刷新");
                            }
                            if (storeHomePageAdapter != null) {
                                storeHomePageAdapter.notifyDataSetChanged();
                            } else {
                                storeHomePageAdapter = new StoreHomePageAdapter(context, newArrivalDataBeanListLoading, shopid, shop_uid, shop_photo);
                                list_store_goods.setAdapter(storeHomePageAdapter);
//                                setListViewHeightBasedOnChildren(list_store_goods);
                            }
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GET_SHOP_INFO:
                    Log.e("getmyshop", "getmyshop");
                    JSONObject jsonObject0 = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject0.getString("status");
                            String data = jsonObject0.getString("data");
                            if (status.equals("success")) {
                                JSONObject jsonObject1 = jsonObject0.getJSONObject("data");
                                String shopid = jsonObject1.getString("id");
                                String head = jsonObject1.getString("photo");
                                starLevel = jsonObject1.getString("level");
                                String sellCount = jsonObject1.getString("sellcount");
                                String likeNum = jsonObject1.getString("favorite");
                                //设置销量、收藏人数
                                if ((!sellCount.equals("")) && (!likeNum.equals(""))) {
                                    tvsellCount.setText("累计销售：" + sellCount);
                                    tvLikeNum.setText("收藏人数：" + likeNum + "人");
                                }
                                if (!starLevel.equals("")) {
                                    //设置星星显示个数
                                    setStarBg(starLevel);
                                }
                                shop_uid = jsonObject1.getString("uid");
                                shop_photo = head;
                                shopname = jsonObject1.getString("shopname");
                                Log.e("head=", head);
                                ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + jsonObject1.getString("photo"), img_store_head);
                                if (shopname.equals("null") || shopname == null || shopname.equals("")) {
                                    tx_store_name.setText("未设置");
                                } else {
                                    tx_store_name.setText(shopname);
                                }
                                initdata();
                            } else {
                                Toast.makeText(context, jsonObject0.getString("data"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CommunalInterfaces.LIKE_GOOD:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        if (jsonObject1.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "收藏成功！");
                            btn_shoucang.setText("已收藏");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CANCLE_LIKE_GOOD:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        if (jsonObject2.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "取消收藏成功！");
                            btn_shoucang.setText("收藏");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private LinearLayout starLayout;
    private TextView tvsellCount;
    private TextView tvLikeNum;
    private Button btnShare;
    private TextView tvSort;
    private PopupWindow sortPopupWindow;
    private TextView sortTv3;
    private TextView sortTv2;
    private TextView sortTv1;
    private TextView sortTv0;

    private void setStarBg(String starLevel) {
        LayoutInflater layoutInflater = getLayoutInflater();
        switch (starLevel) {
            case "0.5":
                layoutInflater.inflate(R.layout.red_star_05, starLayout);
            case "1":
                layoutInflater.inflate(R.layout.red_star_1, starLayout);
            case "1.5":
                layoutInflater.inflate(R.layout.red_star_15, starLayout);
            case "2":
                layoutInflater.inflate(R.layout.red_star_2, starLayout);
            case "2.5":
                layoutInflater.inflate(R.layout.red_star_25, starLayout);
            case "3":
                layoutInflater.inflate(R.layout.red_star_3, starLayout);
            case "3.5":
                layoutInflater.inflate(R.layout.red_star_35, starLayout);
            case "4":
                layoutInflater.inflate(R.layout.red_star_4, starLayout);
            case "4.5":
                layoutInflater.inflate(R.layout.red_star_45, starLayout);
            case "5":
                layoutInflater.inflate(R.layout.red_star_5, starLayout);
        }
    }

    private RelativeLayout shopSearch;

    private IWXAPI api;
    private String starLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_store_homepage);
        context = this;
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");


        // 微信注册初始化
        api = WXAPIFactory.createWXAPI(this, "wxb32c00f" +
                "fa8140d93", true);
        api.registerApp("wxb32c00ffa8140d93");
        initview();


        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getshopinfo(shopid);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }

    }

    private void initview() {
        goods_list = new ArrayList<StoreHomepage.DataBean>();
        newArrivalDataBeanListLoading = new ArrayList<>();
        list_store_goods = (GridView) findViewById(R.id.list_store_goods);
        tx_store_name = (TextView) findViewById(R.id.tx_store_name);
        img_store_head = (ImageView) findViewById(R.id.img_store_head);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        btn_shoucang = (Button) findViewById(R.id.btn_shoucang);
        btn_shoucang.setOnClickListener(this);
        btn_chat_store = (Button) findViewById(R.id.btn_chat_store);
        btn_chat_store.setOnClickListener(this);
        shopSearch = (RelativeLayout) findViewById(R.id.shop_right_search_icon);
        shopSearch.setOnClickListener(this);
        starLayout = (LinearLayout) findViewById(R.id.store_red_star);
        tvsellCount = (TextView) findViewById(R.id.tx_store_saleNum);
        tvLikeNum = (TextView) findViewById(R.id.tv_like_num);
        btnShare = (Button) findViewById(R.id.btn_store_share);
        btnShare.setOnClickListener(this);
        tvSort = (TextView) findViewById(R.id.tv_store_home_sort);
        tvSort.setOnClickListener(this);
        sv = (PullToRefreshScrollView) findViewById(R.id.sv);


        //设置可上拉刷新和下拉刷新
        sv.setMode(PullToRefreshBase.Mode.BOTH);

        //设置刷新时显示的文本
        ILoadingLayout startLayout = sv.getLoadingLayoutProxy(true, false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = sv.getLoadingLayoutProxy(false, true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");


      sv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
          @Override
          public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
              new StoreHomepageActivity.LoadDataAsyncTask(StoreHomepageActivity.this, 1).execute();

          }

          @Override
          public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
              new StoreHomepageActivity.LoadDataAsyncTask(StoreHomepageActivity.this, 2).execute();

          }
      });
    }

    /**
     * 异步下载任务
     */
    private static class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private StoreHomepageActivity mainActivity;
        private int judge;

        public LoadDataAsyncTask(StoreHomepageActivity mainActivity, int judge) {
            this.mainActivity = mainActivity;
            this.judge = judge;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                mainActivity.loadData(judge);
                Thread.sleep(2000);
                return "seccess";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        /**
         * 完成时的方法
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("seccess")) {
                mainActivity.storeHomePageAdapter.notifyDataSetChanged();
                mainActivity.sv.onRefreshComplete();//刷新完成
            }
        }
    }

    private void loadData(int judge) {
        int size = newArrivalDataBeanListLoading.size();
        if (judge == 1) {
            return;
        } else {
            if (goods_list.size() >= (size + 6)) {

                for (int i = size; i < size + 6; i++) {
                    newArrivalDataBeanListLoading.add(goods_list.get(i));
                }

                ILoadingLayout endLayout = sv.getLoadingLayoutProxy(false, true);
                endLayout.setPullLabel("正在上拉刷新...");
                endLayout.setRefreshingLabel("正在玩命加载中...");
                endLayout.setReleaseLabel("放开以刷新");
            } else {

                for (int i = size; i < goods_list.size(); i++) {
                    newArrivalDataBeanListLoading.add(goods_list.get(i));
                }

                ILoadingLayout endLayout = sv.getLoadingLayoutProxy(false, true);
                endLayout.setPullLabel("正在上拉刷新...");
                endLayout.setRefreshingLabel("已经没有更多宝贝了");
                endLayout.setReleaseLabel("放开以刷新");
            }
            return;
        }
    }

    private void initdata() {
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).getshopgoodlist(shopid);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_shoucang:
                if (!btn_shoucang.isSelected()) {
                    new ShopRequest(this, handler).likeShop(shopid);
                    btn_shoucang.setSelected(true);
                } else {
                    new ShopRequest(this, handler).cancelLikeShop(shopid);
                    btn_shoucang.setSelected(false);
                }
                break;
            case R.id.btn_chat_store:
                Intent intent1 = new Intent();
                intent1.putExtra("shop_uid", shop_uid);
                intent1.putExtra("shop_photo", shop_photo);
                intent1.putExtra("shopname", shopname);
                intent1.setClass(context, ChatActivity.class);
                startActivity(intent1);
                break;
            case R.id.shop_right_search_icon:
                //跳转搜索
                IntentUtils.getIntents(context,SearchStoreHomeActivity.class);
                break;
            case R.id.btn_store_share:
                showSharePopwindow();
//                showPopupMenu(shopShare);//弹出分享店铺菜单
                break;
//            case R.id.btn_lianximaijia:
//                Intent intent2 = new Intent();
//                intent2.putExtra("shop_uid", shop_uid);
//                intent2.putExtra("shop_photo", shop_photo);
//                intent2.putExtra("shopname", shopname);
//                intent2.setClass(context, ChatActivity.class);
//                startActivity(intent2);
//                break;
            //四个排序选择
            case R.id.tv_store_home_sort:
                showSortPopWindow();
                break;
            case R.id.search_pop_sort0:
                tvSort.setText(sortTv0.getText() + " ∨");
                sortPopupWindow.dismiss();
                break;
            case R.id.search_pop_sort1:
                tvSort.setText(sortTv1.getText()+ " ∨");
                sortPopupWindow.dismiss();
                break;
            case R.id.search_pop_sort2:
                tvSort.setText(sortTv2.getText()+ " ∨");
                sortPopupWindow.dismiss();
                break;
            case R.id.search_pop_sort3:
                tvSort.setText(sortTv3.getText()+ " ∨");
                sortPopupWindow.dismiss();
                break;
        }
    }



    /**排序popupWindow
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void showSortPopWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.search_sort_popuplayout, null);
        sortPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
//                        backgroundAlpha(1f);
        ColorDrawable cd = new ColorDrawable(0x0000);
        sortPopupWindow.setBackgroundDrawable(cd);
        sortPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        sortPopupWindow.showAsDropDown(tvSort, 0, 0);//puw显示位置

        sortTv0 = (TextView) contentView.findViewById(R.id.search_pop_sort0);
        sortTv1 = (TextView) contentView.findViewById(R.id.search_pop_sort1);
        sortTv2 = (TextView) contentView.findViewById(R.id.search_pop_sort2);
        sortTv3 =  (TextView) contentView.findViewById(R.id.search_pop_sort3);
        sortTv0.setOnClickListener(this);
        sortTv1.setOnClickListener(this);
        sortTv2.setOnClickListener(this);
        sortTv3.setOnClickListener(this);
    }





    /**
     * 显示分享到社交app的popupWindow
     */
    private void showSharePopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.show_share_good_popup_window, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setOutsideTouchable(true); // 设置popupwindow外部可点击
        window.setFocusable(true); // 获取焦点

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x0000);
        window.setBackgroundDrawable(dw);

        //设置背景半透明
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.3f;
        this.getWindow().setAttributes(lp);
//                        backgroundAlpha(1f);
        ColorDrawable cd = new ColorDrawable(0x0000);
        window.setBackgroundDrawable(cd);


//        // 设置popWindow的显示和消失动画
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(StoreHomepageActivity.this.findViewById(R.id.btn_chat_store),
                Gravity.BOTTOM, 0, 0);


        // 这里检验popWindow里的button是否可以点击
//        Button first = (Button) view.findViewById(R.id.first);
        Button icWeChat = (Button) view.findViewById(R.id.pop_share_to_weChat_icon);
        Button icFriend = (Button) view.findViewById(R.id.pop_share_to_weChat_friend_icon);
        Button icQQ = (Button) view.findViewById(R.id.pop_share_to_qq_icon);
        Button icMicroBlog = (Button) view.findViewById(R.id.pop_share_to_microBlog_icon);
        icWeChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                share2weixin(0, shopname);//好友
                etongApplaction applaction = (etongApplaction) getApplication();
                applaction.setjudgeCode("1");//设置微信分享为1，店铺分享
            }
        });
        icFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                share2weixin(1, shopname);//朋友圈
                etongApplaction applaction = (etongApplaction) getApplication();
                applaction.setjudgeCode("1");//设置微信分享购为为1，店铺分享
            }
        });
        icQQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtils.makeShortToast(context, "分享到QQ功能正在开发中");
            }
        });
        icMicroBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtils.makeShortToast(context, "分享到微博功能正在开发中");
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                //设置背景变回原色
                WindowManager.LayoutParams lp = StoreHomepageActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                StoreHomepageActivity.this.getWindow().setAttributes(lp);
            }
        });

    }

//
//    /**
//     * 废弃！！
//     * @param view
//     */
//    private void showPopupMenu(View view) {
//        // View当前PopupMenu显示的相对View的位置
//        PopupMenu popupMenu = new PopupMenu(this, view);
//        // menu布局
//        popupMenu.getMenuInflater().inflate(R.menu.share_shop, popupMenu.getMenu());
//        // menu的item点击事件
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
////                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//                switch (item.getItemId()) {
//                    case R.id.share_shop_to_wechat0:
//                        share2weixin(0,shopname);//好友
//                        break;
//                    case R.id.share_shop_to_wechat1:
//                        share2weixin(1,shopname);//朋友圈
//                        break;
//                }
//                return false;
//            }
//        });
//        // PopupMenu关闭事件
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
////                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        popupMenu.show();
//    }


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


    private void share2weixin(int flag, String shopName) {
        // Bitmap bmp = BitmapFactory.decodeResource(getResources(),
        // R.drawable.weixin_share);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = SHARE_SHOP_TO_FRIEND + shopid;//分享shopid
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = shopName;
        msg.description = "分享店铺给你！";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.drawable.share_to_wechat_icon);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
    }


}
