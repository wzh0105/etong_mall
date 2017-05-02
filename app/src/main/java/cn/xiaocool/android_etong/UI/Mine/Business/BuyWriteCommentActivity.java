package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/8/12.
 */
public class BuyWriteCommentActivity extends Activity {


    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.buy_write_comment_pic)
    ImageView buyWriteCommentPic;
    @BindView(R.id.buy_write_comment_good_name)
    TextView buyWriteCommentGoodName;
    @BindView(R.id.buy_write_comment_et)
    EditText buyWriteCommentEt;
    @BindView(R.id.buy_write_comment_name)
    TextView buyWriteCommentName;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.rl_4)
    RelativeLayout rl4;
    @BindView(R.id.rl_5)
    RelativeLayout rl5;
    @BindView(R.id.ll_star)
    LinearLayout llStar;
    @BindView(R.id.buy_write_comment_now)
    RelativeLayout buyWriteCommentNow;
    private String orderId;
    private String goodName, goodPic;
    private Context context;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.BUY_WRITE_COMMENT:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        if (jsonObject.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "评价成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "评价失败！");
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
        setContentView(R.layout.buy_write_comment);
        context = this;
        orderId = getIntent().getStringExtra("orderId");
        goodName = getIntent().getStringExtra("name");
        goodPic = getIntent().getStringExtra("picture");
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        topTitleText.setText("立即评价");
        buyWriteCommentGoodName.setText(goodName);
        imageLoader.displayImage(WebAddress.GETAVATAR + goodPic, buyWriteCommentPic, displayImageOptions);
    }

    @OnClick({R.id.btn_back, R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_5, R.id.ll_star, R.id.buy_write_comment_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_1:
                llStar.setBackgroundResource(R.mipmap.ic_yellowstar_1);
                break;
            case R.id.rl_2:
                llStar.setBackgroundResource(R.mipmap.ic_yellowstar_2);
                break;
            case R.id.rl_3:
                llStar.setBackgroundResource(R.mipmap.ic_yellowstar_3);
                break;
            case R.id.rl_4:
                llStar.setBackgroundResource(R.mipmap.ic_yellowstar_4);
                break;
            case R.id.rl_5:
                llStar.setBackgroundResource(R.mipmap.ic_yellowstar_5);
                break;
            case R.id.ll_star:
                break;
            case R.id.buy_write_comment_now:
                String comment = buyWriteCommentEt.getText().toString();
                if (NetUtil.isConnnected(context)) {
                    new ShopRequest(context, handler).buyWriteComment(orderId, comment);
                }
                break;
        }
    }
}
