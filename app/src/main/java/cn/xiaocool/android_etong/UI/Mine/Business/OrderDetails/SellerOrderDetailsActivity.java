package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.util.TimeToolUtils;

/**
 * Created by wzh on 2016/8/8.
 */
public class SellerOrderDetailsActivity extends Activity {


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
    private String name, address, mobile, createTime, state, goodsName, price, number, orderNum;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_details);
        ButterKnife.bind(this);
        getIntentValue();
        initView();

    }

    private void getIntentValue() {
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        mobile = getIntent().getStringExtra("mobile");
        state = getIntent().getStringExtra("state");
        goodsName = getIntent().getStringExtra("goodsName");
        price = getIntent().getStringExtra("price");
        number = getIntent().getStringExtra("number");
        orderNum = getIntent().getStringExtra("orderNum");
        createTime = getIntent().getStringExtra("createTime");
        Log.e("time is",createTime);
        time = TimeToolUtils.timeStampDateString(createTime, "yyyy-MM-dd HH:mm:ss");
    }

    private void initView() {
        topTitleText.setText("订单详情");
        orderReserveName.setText(name);
        orderReserveAddressName.setText(address);
        orderReservePhone.setText(mobile);
        orderDetailsGoodNameTv.setText(goodsName);
        txGoodsCount.setText(number);
        txShoppingClothPrice.setText("¥" + price);
        tvOrderNumber.setText(orderNum);
        tvCreateTime.setText(time);
        if (state.equals("1")) {
            orderStatus.setText("订单待付款");
        } else if (state.equals("2")) {
            orderStatus.setText("买家已付款");
        } else if (state.equals("3")) {
            orderStatus.setText("卖家已发货");
        } else if (state.equals("4")) {
            orderStatus.setText("交易成功");
        }
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
