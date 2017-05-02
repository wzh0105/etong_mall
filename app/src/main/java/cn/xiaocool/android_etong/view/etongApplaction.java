package cn.xiaocool.android_etong.view;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.android_etong.bean.business.LocationService;

/**
 * Created by 潘 on 2016/6/21.
 */
public class etongApplaction extends Application {
    private static etongApplaction mInstance = null;
    public static int UID;
    public static String isFrist = "yes";
    public LocationService locationService;
    public Vibrator mVibrator;
    public BaseResp resp;//微信登录
    public static IWXAPI api;
    public String judgeCode;
    public int shareBuyCode = 0;

    private static RequestQueue requestQueue;
    private static RequestQueue requestQueueFile;
    private static etongApplaction myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        //请求队列
        requestQueue = Volley.newRequestQueue(this);
        requestQueueFile = Volley.newRequestQueue(this);
        myApplication = new etongApplaction();

        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //设置极光推送图片不好用...
//        CustomPushNotificationBuilder builder = new
//                CustomPushNotificationBuilder(getApplicationContext(),
//                R.layout.customer_notitfication_layout,
//                R.id.icon,
//                R.id.title,
//                R.id.text);
//        // 指定定制的 Notification Layout
//        builder.statusBarDrawable = R.drawable.logo_920;
//        // 指定最顶层状态栏小图标
//        builder.layoutIconDrawable = R.drawable.logo_920;
//        // 指定下拉状态栏时显示的通知图标
//        JPushInterface.setPushNotificationBuilder(2, builder);
        mInstance = this;
        initImageLoader(getApplicationContext());
        SharedPreferences sp = getSharedPreferences("UserUID", Context.MODE_PRIVATE);
        UID = sp.getInt("UID", 0);
        isFrist = sp.getString("isFrist", "");
        Log.e("hou", "APPlication:UID=" + UID);

//初始化微信
        api = WXAPIFactory.createWXAPI(this, "wxb32c00ffa8140d93", true);
        api.registerApp("wxb32c00ffa8140d93");


        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    public static etongApplaction getInstance() {
        return mInstance;
    }

    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "wxt_parent/Cache");// 获取到缓存的目录地址
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCacheExtraOptions(1000, 1000)
                // max width,max height，即保存的每个缓存文件的最大长宽default=device screen dimensions
                .discCacheExtraOptions(1000, 1000, Bitmap.CompressFormat.JPEG, 75, null)
                // Can slow ImageLoader, use it carefully (Better don't use
                // it)设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(5)// 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1).tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
                // 1024))
                // You can pass your own memory cache
                // .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCache(new WeakMemoryCache())
                // implementation你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024)
                // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5加密
                // .discCacheFileNameGenerator(new
                // HashCodeFileNameGenerator())// 将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(1000) // 缓存的File数量
                .discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                // connectTimeout (5s), readTimeout(30s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    public void setResp(BaseResp resp) {
        this.resp = resp;
    }

    public BaseResp getResp() {
        return resp;
    }


    public void setjudgeCode(String judgeCode) {
        this.judgeCode = judgeCode;
    }

    public String getjudgeCode() {
        return judgeCode;
    }

    public void setShareBuyCode(int shareBuyCode) {
        this.shareBuyCode = shareBuyCode;
    }

    public int getShareBuyCode() {
        return shareBuyCode;
    }

    /**
     * 拿到消息队列
     */
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    /**
     * 拿到消息队列
     */
    public static RequestQueue getFileRequestQueue() {
        return requestQueueFile;
    }

    /**
     * 拿到消息队列
     */
    public static Application getApplication() {
        return myApplication;
    }
}
