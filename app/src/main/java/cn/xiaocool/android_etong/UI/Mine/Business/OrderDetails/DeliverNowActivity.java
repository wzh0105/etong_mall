package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.TimeToolUtils;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/8/10.
 */
public class DeliverNowActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.seller_deliver_now_img)
    ImageView sellerDeliverNowImg;
    @BindView(R.id.seller_deliver_orderNum)
    TextView sellerDeliverOrderNum;
    @BindView(R.id.seller_deliver_payTime)
    TextView sellerDeliverPayTime;
    @BindView(R.id.seller_deliver_finishTime)
    TextView sellerDeliverFinishTime;
    @BindView(R.id.seller_deliver_spiner)
    Spinner sellerDeliverSpiner;
    @BindView(R.id.seller_deliver_et_express)
    EditText sellerDeliverEtExpress;
    @BindView(R.id.seller_deliver_btn)
    RelativeLayout sellerDeliverBtn;

    String picName, orderNum, payTime;
    long ordertime;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private String clickItemName;
    private String timeText;
    private String orderId;
    private Context context;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SELLER_DELIVER_GOOD:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "发货成功！");
                            end();
                        } else {
                            ToastUtils.makeShortToast(context, "发货失败！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private void end() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.seller_deliver_now);
        ButterKnife.bind(this);
        context = this;
        picName = getIntent().getStringExtra("picName");
        orderNum = getIntent().getStringExtra("orderNum");
        ordertime = getIntent().getLongExtra("orderTime", 0);
        orderId = getIntent().getStringExtra("orderId");
        Log.e("unix time is", String.valueOf(ordertime));
        timeText = TimeToolUtils.timeStampDate(ordertime, "yyyy MM-dd HH:mm:ss");//Unix时间戳转换
        initView();
//        picName = getIntent().getStringExtra("picName");
    }

    private void initView() {
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        imageLoader.displayImage(WebAddress.GETAVATAR + picName, sellerDeliverNowImg, displayImageOptions);
        topTitleText.setText("订单管理");
        sellerDeliverOrderNum.setText(orderNum);
        sellerDeliverFinishTime.setText(timeText);
        initSpinner();//初始化Spinner

    }

    private void initSpinner() {
        list.add("顺丰快递");
        list.add("圆通快递");
        list.add("申通快递");
        list.add("韵达快递");
        list.add("百世快递");
        list.add("邮政快递");
        list.add("EMS");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sellerDeliverSpiner.setAdapter(arrayAdapter);
        sellerDeliverSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clickItemName = arrayAdapter.getItem(position);
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick({R.id.btn_back, R.id.seller_deliver_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.seller_deliver_btn:
                if (NetUtil.isConnnected(this)) {
                    new ShopRequest(this, handler).sellerDeliverGood(orderId);
                }
                break;
        }
    }
}
