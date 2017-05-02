package cn.xiaocool.android_etong.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import cn.xiaocool.android_etong.R;


/**
 * Created by Administrator on 2016/3/14.
 */

public class ImgLoadUtil {

    private static DisplayImageOptions options =new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.hyx_default)
            .showImageOnFail(R.drawable.hyx_default)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .build();

    private static DisplayImageOptions headerOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.hyx_default)
            .showImageOnFail(R.drawable.hyx_default)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .displayer(new FadeInBitmapDisplayer(200))
            .build();

    public static void display(String uri,ImageView imageView){
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void displayHeader(String uri,ImageView imageView){
        ImageLoader.getInstance().displayImage(uri, imageView, headerOptions);
    }

//    public static void displayUserHeader(ImageView imageView){
//        String uri = MyApplication.getInstance().getUser().getImg();
//        ImageLoader.getInstance().displayImage(uri, imageView, headerOptions);
//    }

    /**下载gif*/
    public static  Bitmap disPlayGif(String url){
        return  ImageLoader.getInstance().loadImageSync(url);
    }
}
