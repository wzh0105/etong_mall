package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import org.json.JSONObject;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;

/**
 * Created by wzh on 2016/7/29.
 */
public class AddGoodStandardActivity extends Activity {
    String[] colorText, sizeText;
    String text;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.UPLOAD_GOOD_STANDARD:
                    JSONObject jsonObject = (JSONObject) msg.obj;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_good_standard_list);
        Bundle bundle = this.getIntent().getExtras();
        String goodId = getIntent().getStringExtra("goodId");
        colorText = bundle.getStringArray("colorArr");
        sizeText = bundle.getStringArray("sizeArr");
        Log.e("aa",colorText.toString() + sizeText.toString());
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < colorText.length; i++) {
            if (!colorText[i].equals("")) {
                for (int j = 0; j < sizeText.length; j++) {
                    if (!sizeText[j].equals("")) {
                        buffer.append(sizeText[j] + ",");
//                        new ShopRequest(this, handler).uploadStandard(goodId, colorText[i],buffer.toString());
                        Log.e(colorText[i], buffer.toString());
                    }
                }
            }

        }
    }
}
