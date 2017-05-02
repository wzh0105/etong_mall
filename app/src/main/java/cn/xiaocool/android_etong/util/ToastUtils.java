package cn.xiaocool.android_etong.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by wzh on 2016/7/21.
 */
public class ToastUtils {
    public static void makeShortToast(Context context, String text){
//        Toast toast = new Toast(context);
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

}
