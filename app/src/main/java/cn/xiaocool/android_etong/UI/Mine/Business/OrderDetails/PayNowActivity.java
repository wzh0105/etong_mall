package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.demo.PayResult;
import cn.xiaocool.android_etong.demo.SignUtils;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/8/11.
 */
public class PayNowActivity extends Activity {
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.pay_way0_icon)
    ImageView payWay0Icon;
    @BindView(R.id.pay_way0_name)
    TextView payWay0Name;
    @BindView(R.id.iv_payWay0_item)
    ImageView ivPayWay0Item;
    @BindView(R.id.rl_payWay0)
    RelativeLayout rlPayWay0;
    @BindView(R.id.pay_way1_icon)
    ImageView payWay1Icon;
    @BindView(R.id.pay_way1_name)
    TextView payWay1Name;
    @BindView(R.id.iv_payWay1_item)
    ImageView ivPayWay1Item;
    @BindView(R.id.rl_payWay1)
    RelativeLayout rlPayWay1;
    @BindView(R.id.pay_way2_icon)
    ImageView payWay2Icon;
    @BindView(R.id.pay_way2_name)
    TextView payWay2Name;
    @BindView(R.id.pay_now_price)
    TextView payNowPrice;
    @BindView(R.id.pay_now_price_tv)
    TextView payNowBtnPrice;
    @BindView(R.id.iv_payWay2_item)
    ImageView ivPayWay2Item;
    @BindView(R.id.rl_payWay2)
    RelativeLayout rlPayWay2;
    @BindView(R.id.rl_confirm_pay)
    RelativeLayout rlConfirmPay;
    private String orderId;
    private String shopName;
    private String price;
    private Context context;
    // 商户PID
    public static final String PARTNER = "2088421788927620";
    // 商户收款账号
    public static final String SELLER = "m15935244346@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE =
            "MIICXQIBAAKBgQDb1tJNjFKOADOP+HsTOUF+erjzCzb31CWT71bSL5dVH9zfkn/1" +
                    "7gSo3RAuuwtnAFweR2bSyXZwoDnNJyHIOturrA9l5mctIiTcwq2aLKMUa7e9bFQo" +
                    "B+TXOrmlKiRmwmHQrcFMqlQVwESQufZpkv4X+miNPgA6tzv3En6fg1ylkQIDAQAB" +
                    "AoGBAKuONemMcrQb1iEo8KqsyL5x+Li57Lhz1qteYCuANiGYzt2tzqvVhc9dTA6b" +
                    "qqdDsZ9zavtdek8jyledjRBbViZrwtXxQ7Wz8aEEHlmAvyjBpCGN5rclzZV3K4Lr" +
                    "+qoFC5uV8+1ejjsOjuVdmMLfL2pplqEIxi7rdzhdDRkmyQ0RAkEA/44kVcqrov8S" +
                    "hEsUvX7aDyk66Hm8nDC/91mqQ54sQjNJjuwc5bf2xipIDOLdRVxxXWN1X97p7RGE" +
                    "19V+TgELRQJBANw4xFMwkNsCGwE+tU/dBrR9eZHnCdWgs/Qv102r1dww6EVSsyB4" +
                    "F8PWZhD7YPmr6g2s06y9M15j3vlOO4X3b90CQQDlwrw0TfUmpMHXI1HQVT5kJyOJ" +
                    "Y/oJS2MsVfdYt9r+4fGeh/YrKsy5ucXxn/5koApkdklPGrGirP+MtavLIfEpAkAs" +
                    "1gXqpgWoAUq8OycxBmAaGT3KHc7bxqc8vQzJzIVzGiYADzFXF/xGq/0F0hhRXNX/" +
                    "SMOj9LVjr1OzUGFACjulAkB7EGfTW9NNglS3/jdeqZGs3WnuR1S/bwBXU7RkdgL2" +
                    "6jXXxn63BHT8zzfD5DhbrxHjYakSkL9wCQomPHE5n6If";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.PAY_ORDER_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        if (jsonObject.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "支付订单成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "支付失败！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        new ShopRequest(context, handler).payOrder(orderId);//支付订单，订单状态改变
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(context, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shop_pay_now);
        context = this;
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("orderId");
        Log.e("orderid=", orderId);
//        shopName = getIntent().getStringExtra("orderId");\
        price = getIntent().getStringExtra("price");
        initView();
    }

    private void initView() {
        topTitleText.setText("支付订单");
        payWay0Icon.setSelected(true);
        payWay1Icon.setSelected(false);
        payWay2Icon.setSelected(false);
        payNowPrice.setText("¥" + price);
        payNowBtnPrice.setText("立即支付¥" + price);
    }

    @OnClick({R.id.btn_back, R.id.rl_payWay0, R.id.rl_payWay1, R.id.rl_payWay2, R.id.rl_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_payWay0:
                ivPayWay0Item.setSelected(true);
                ivPayWay1Item.setSelected(false);
                ivPayWay2Item.setSelected(false);
                break;
            case R.id.rl_payWay1:
                ivPayWay0Item.setSelected(false);
                ivPayWay1Item.setSelected(true);
                ivPayWay2Item.setSelected(false);
                break;
            case R.id.rl_payWay2:
                ivPayWay0Item.setSelected(false);
                ivPayWay1Item.setSelected(false);
                ivPayWay2Item.setSelected(true);
                break;
            case R.id.rl_confirm_pay:
                if (ivPayWay2Item.isSelected()) {
                    pay();//调用支付功能

                } else {
                    ToastUtils.makeShortToast(context, "目前仅支持支付宝支付！");
                }
                break;
        }
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay() {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    finish();
                                }
                            }).show();
            return;
        }
        // 订单
        String orderInfo = getOrderInfo("E通商城支付商品", "该测试商品的详细描述", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

}
