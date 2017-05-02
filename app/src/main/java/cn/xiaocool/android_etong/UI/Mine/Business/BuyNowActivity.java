package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/20.
 */
public class BuyNowActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private String id,goods_name,goods_price,goods_pic,shopname;
    private Button btn_comfirm;
    private TextView tx_goods_name,tx_goods_price;
    private ImageView img_jia,img_jian;
    private ImageView img_goods_pic_big,img_goods_pic_small;
    private TextView tx_goods_count;
    private int count = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.GET_GOODS_INFO:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")){
                           JSONObject jsonObject = json.getJSONObject("data");
                            goods_pic = jsonObject.getString("picture");
                            String[] arraypic = goods_pic.split("[,]");
                            goods_name = jsonObject.getString("goodsname");
                            tx_goods_name.setText(goods_name);
                            goods_price = jsonObject.getString("price");
                            tx_goods_price.setText("￥"+goods_price);
                            img_goods_pic_big.getBackground().setAlpha(0x000000);
                            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR+arraypic[0],img_goods_pic_big);
                            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR+arraypic[0],img_goods_pic_small);
                        }else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_buynow);
        context = this;
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        shopname = intent.getStringExtra("shopname");
        initview();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).getgoodsinfo(id);
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        img_jia = (ImageView) findViewById(R.id.img_jia);
        img_jia.setOnClickListener(this);
        img_jian = (ImageView) findViewById(R.id.img_jian);
        img_jian.setOnClickListener(this);
        tx_goods_count = (TextView) findViewById(R.id.tx_goods_count);
        btn_comfirm = (Button) findViewById(R.id.btn_comfirm);
        btn_comfirm.setOnClickListener(this);
        img_goods_pic_big = (ImageView) findViewById(R.id.img_goods_pic_big);
        img_goods_pic_small = (ImageView) findViewById(R.id.img_goods_pic_small);
        tx_goods_name = (TextView) findViewById(R.id.tx_goods_name);
        tx_goods_price = (TextView) findViewById(R.id.tx_goods_price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.img_jia:
                count++;
                tx_goods_count.setText(String.valueOf(count));
                break;
            case R.id.img_jian:
                if (count>1){
                    count--;
                    tx_goods_count.setText(String.valueOf(count));
                }
                break;
            case R.id.btn_comfirm:
                Intent intent = new Intent();
                intent.putExtra("count",count);
                intent.putExtra("id",id);
                intent.putExtra("shopname",shopname);
                intent.setClass(context,ComfirmOrderActivity.class);
                startActivity(intent);
                break;
        }
    }
}
