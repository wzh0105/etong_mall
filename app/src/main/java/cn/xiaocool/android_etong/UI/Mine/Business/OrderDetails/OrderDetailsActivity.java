package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Shop.OrderDetailsBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.TimeToolUtils;

/**
 * Created by wzh on 2016/8/8.
 */
public class OrderDetailsActivity extends Activity {


    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.order_reserve_name)
    TextView orderReserveName;
    @BindView(R.id.order_reserve_address_name)
    TextView orderReserveAddressName;
    @BindView(R.id.order_reserve_phone)
    TextView orderReservePhone;
    @BindView(R.id.shop_pic_icon)
    ImageView shopPicIcon;
    @BindView(R.id.order_details_shop_name_tv)
    TextView orderDetailsShopNameTv;
    @BindView(R.id.order_details_good_status_tv)
    TextView orderDetailsGoodStatusTv;
    @BindView(R.id.img_shopping_chanpin)
    ImageView imgShoppingChanpin;
    @BindView(R.id.order_details_good_name_tv)
    TextView orderDetailsGoodNameTv;
    @BindView(R.id.tx_shopping_cloth_color)
    TextView txShoppingClothColor;
    @BindView(R.id.order_details_good_color_tv)
    TextView orderDetailsGoodColorTv;
    @BindView(R.id.tx_shopping_cloth_size)
    TextView txShoppingClothSize;
    @BindView(R.id.order_details_good_size_tv)
    TextView orderDetailsGoodSizeTv;
    @BindView(R.id.tx_shopping_cloth_price)
    TextView txShoppingClothPrice;
    @BindView(R.id.tx_goods_count)
    TextView txGoodsCount;
    @BindView(R.id.orderDetails_btn_apply_serve)
    TextView applyServe;
    @BindView(R.id.orderDetails_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.orderDetails_pay_way)
    TextView tvPayWay;
    @BindView(R.id.orderDetails_createTime)
    TextView tvCreateTime;
    @BindView(R.id.orderDetails_orderStatus)
    TextView orderStatus;
    //    private String name, address, mobile, createTime, state, goodsName, price, number, orderNum;
    private String time;
    private Context context;
    private String orderId;
    private String userId, orderNum, gid, goodsName, picture, Id, createTime, state, type, peopleName, mobile,
            price, address, number, money, remarks, userName, statusName, statusEnd, evaluate;
    private List<OrderDetailsBean.DataBean> dataBeanList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.e("0", "0000");
            switch (msg.what) {
                case CommunalInterfaces.GET_ORDER_DETAILS:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
//                        OrderDetailsBean.DataBean dataBean = new OrderDetailsBean.DataBean();
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                            Log.e("jsonobject json1", String.valueOf(jsonObject1));
                            userId = jsonObject1.getString("user_id");
                            orderNum = jsonObject1.getString("order_num");
                            Log.e("num is",orderNum);
                            gid = jsonObject1.getString("gid");
                            goodsName = jsonObject1.getString("goodsname");
                            picture = jsonObject1.getString("picture");
                            Id = jsonObject1.getString("id");
                            createTime = jsonObject1.getString("time");
                            state = jsonObject1.getString("state");
                            type = jsonObject1.getString("type");
                            peopleName = jsonObject1.getString("peoplename");
                            mobile = jsonObject1.getString("mobile");
                            price = jsonObject1.getString("price");
                            address = jsonObject1.getString("address");
                            number = jsonObject1.getString("number");
                            money = jsonObject1.getString("money");
                            remarks = jsonObject1.getString("remarks");
                            userName = jsonObject1.getString("username");
                            statusName = jsonObject1.getString("statusname");
                            statusEnd = jsonObject1.getString("statusend");
                            evaluate = jsonObject1.getString("evaluate");
                            if (state.equals("1")) {
                                orderStatus.setText("订单待付款");
                            } else if (state.equals("2")) {
                                orderStatus.setText("买家已付款");
                            } else if (state.equals("3")) {
                                orderStatus.setText("卖家已发货");
                            } else if (state.equals("4")) {
                                orderStatus.setText("交易成功");
                            } else if (state.equals("5")) {
                                orderStatus.setText("交易成功");
                            }
                            time = TimeToolUtils.timeStampDateString(createTime, "yyyy-MM-dd HH:mm:ss");
                            orderReserveName.setText(userName);//此处设置的是user name
                            orderReserveAddressName.setText(address);
                            orderReservePhone.setText(mobile);
                            orderDetailsGoodNameTv.setText(goodsName);
                            txGoodsCount.setText(number);
                            txShoppingClothPrice.setText("¥" + price);
                            tvOrderNumber.setText(orderNum);
                            tvCreateTime.setText(time);
//                        dataBeanList.add(dataBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_details);
        context = this;
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("orderId");
        Log.e("orderid is", orderId);

        if (NetUtil.isConnnected(context)) {
            new ShopRequest(context, handler).getOrderDetails(orderId);
        }
//        getIntentValue();
//        time = TimeToolUtils.timeStampDateString(createTime, "yyyy-MM-dd HH:mm:ss");
        initView();
    }

//    private void getIntentValue() {
//


//        name = getIntent().getStringExtra("name");
//        address = getIntent().getStringExtra("address");
//        mobile = getIntent().getStringExtra("mobile");
//        state = getIntent().getStringExtra("state");
//        goodsName = getIntent().getStringExtra("goodsName");
//        price = getIntent().getStringExtra("price");
//        number = getIntent().getStringExtra("number");
//        orderNum = getIntent().getStringExtra("orderNum");
//        createTime = getIntent().getStringExtra("createTime");
//        Log.e("time is",createTime);

//    }

    private void initView() {
//        dataBeen = new ArrayList<>();
        topTitleText.setText("订单详情");
//        orderReserveName.setText(goodsName);
//        orderReserveAddressName.setText(address);
//        orderReservePhone.setText(mobile);
//        orderDetailsGoodNameTv.setText(goodsName);
//        txGoodsCount.setText(number);
//        txShoppingClothPrice.setText("¥" + price);
//        tvOrderNumber.setText(orderNum);
//        tvCreateTime.setText(time);
//        if (state.equals("1")) {
//            orderStatus.setText("订单待付款");
//        } else if (state.equals("2")) {
//            orderStatus.setText("买家已付款");
//        } else if (state.equals("3")) {
//            orderStatus.setText("卖家已发货");
//        } else if (state.equals("4")) {
//            orderStatus.setText("交易成功");
//        } else if (state.equals("5")) {
//            orderStatus.setText("交易成功");
//        }
    }


    @OnClick({R.id.btn_back, R.id.orderDetails_btn_apply_serve})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.orderDetails_btn_apply_serve:
                break;
        }
    }


}
