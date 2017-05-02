package cn.xiaocool.android_etong.net.constant.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Network;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.NetBaseUtils;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.SPUtils;
import cn.xiaocool.android_etong.view.etongApplaction;

/**
 * Created by wzh on 2016/7/20.
 */
public class HomeRequest {
    private UserInfo user;
    private Context mContext;
    private Handler handler;

    public HomeRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
        user.readData(mContext);
    }

    //获取首页新品上市
    public void getNewArrival(final String recommend) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = recommend;
                String result_data = NetUtil.getResponse(WebAddress.GET_NEW_ARRIVAL, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_NEW_ARRIVAL;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取首页每日好店
    public void getEveryDayShop() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&type=10";
                String result_data = NetUtil.getResponse(WebAddress.GET_HOMEPAGE_EVERY_GOODSHOP, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_HOMEPAGE_EVERY_GOODSHOP;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取首页猜你喜欢
    public void getGuessLike() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String result_data = "";
                    String data = "";
                    result_data = NetUtil.getResponse(WebAddress.GET_GUESS_LIKE, data);
                    Log.e("accept like success",result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_GUESS_LIKE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void getGoodsTypeList(final String type) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String result_data = "";


                String data = "&type=" + type;
                result_data = NetUtil.getResponse(WebAddress.GET_GOODTYPE, data);
                Log.e("getGoodsTypeList",WebAddress.GET_GOODTYPE+data);


                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_MENU;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void GetTypeGoodList(final String type) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String result_data = "";
                String data = "&type=" + type;
                result_data = NetUtil.getResponse(WebAddress.GET_GOODS_LIST, data);
                Log.e("GetTypeGoodList",WebAddress.GET_GOODS_LIST+data);


                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_MENU;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //获取分类一级菜单
    public void getMenu(final String suffix,final String name) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String result_data = "";
                if (NetUtil.isConnnected(etongApplaction.getInstance().getBaseContext())){
                    String data = suffix + name;
                    result_data = NetUtil.getResponse(WebAddress.GET_MENU, data);
                    SPUtils.put(mContext,WebAddress.GET_MENU,result_data);
                    Log.e("getmenu",WebAddress.GET_MENU+data);

                }else {
                    result_data = (String) SPUtils.get(mContext,WebAddress.GET_MENU,"");
                }

                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_MENU;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取分类三级菜单
    public void getMenu3(final String name) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data ="&leveltwo_name=" + name;
                String result_data = NetUtil.getResponse(WebAddress.GET_MENU, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_MENU3;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的足迹
    public void GetMyBrowseHistory() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data ="&userid=" + user.getUserId()+"&type=1";
                String result_data = NetUtil.getResponse(WebAddress.GetMyBrowseHistory, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GetMyBrowseHistory;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}