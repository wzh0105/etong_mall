package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/8/31.
 */
public class CancelOrderActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.mine_order_et_cancelOrder)
    EditText mineOrderEtCancelOrder;
    @BindView(R.id.mine_order_btn_confirm)
    Button mineOrderBtnConfirm;
    private Context context;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CANCEL_ORDER:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "订单取消成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "订单取消失败！请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cancel_order);
        ButterKnife.bind(this);
        context = this;
        topTitleText.setText("取消订单");
        orderId = getIntent().getStringExtra("orderId");
    }


    @OnClick({R.id.btn_back, R.id.mine_order_btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.mine_order_btn_confirm:
                String reason = mineOrderEtCancelOrder.getText().toString();
                if (NetUtil.isConnnected(context)) {
                    new ShopRequest(context, handler).cancelOrder(orderId, reason);
                }
                break;
        }
    }
}
