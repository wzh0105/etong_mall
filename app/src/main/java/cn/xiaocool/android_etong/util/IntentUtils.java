package cn.xiaocool.android_etong.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wzh on 2016/4/29.
 */
public class IntentUtils {
    public static Intent getIntent(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        return intent;
    }

    public static Intent getIntents(Context mContext, Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
        return intent;
    }

    public static Intent changeInforIntent(Activity activity, Class clazz,String infor,String suffix) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtra("changeInfor",infor);
        intent.putExtra("webAddress",suffix);
        activity.startActivity(intent);
        return intent;
    }



}
