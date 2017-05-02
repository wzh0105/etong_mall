package cn.xiaocool.android_etong.Listener;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.UI.MainActivity;
import cn.xiaocool.android_etong.UI.QQBindingActivity;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/10/26.
 */

public class BaseUiListener implements IUiListener {
    private UserInfo user;
    private Context context;
    private String openid;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GetUserInfoByQQ:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            user.setUserId(jsonObject1.getString("id"));
                            user.setUserImg(jsonObject1.getString("photo"));
                            user.writeData(context);
                            context.startActivity(new Intent(context, MainActivity.class));
                        }else {
                            Intent intent = new Intent();
                            intent.putExtra("openid",openid);
                            intent.setClass(context, QQBindingActivity.class);
                            context.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };

    public BaseUiListener(Context context){
        this.context = context;
        user = new UserInfo();
        user.readData(context);
    }

    protected void doComplete(JSONObject values) {
        Log.e("response=",values.toString());
        try {
            openid = values.getString("openid");
            if (NetUtil.isConnnected(context)){
                new MainRequest(context,handler).GetUserInfoByQQ(openid);
            }else {
                Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onComplete(Object response) {
        doComplete((JSONObject) response);
    }

    @Override
    public void onError(UiError e) {
        Log.e("onError:", "code:" + e.errorCode + ", msg:"
                + e.errorMessage + ", detail:" + e.errorDetail);
    }
    @Override
    public void onCancel() {
        Log.e("onCancel", "");
    }
}
