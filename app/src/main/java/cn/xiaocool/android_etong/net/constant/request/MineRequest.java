package cn.xiaocool.android_etong.net.constant.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/7/20.
 */
public class MineRequest {
    private UserInfo user;
    private Context mContext;
    private Handler handler;

    public MineRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
        user.readData(mContext);
    }

    //修改并上传宝贝详情信息
    public void changeGoodIntro(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + goodId;
                String result_data = NetUtil.getResponse(WebAddress.CHANGE_GOOD_INTRO, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CHANGE_GOOD_INTRO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改单条宝贝信息
    public void changeGoodIntroItem(final String suffix, final String infor) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = suffix + infor;
                String result_data = NetUtil.getResponse(WebAddress.CHANGE_GOOD_INTRO_ITEM, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CHANGE_GOOD_INTRO_ITEM;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //我的收藏的宝贝
    public void myLikeGood() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&type=1";
                String result_data = NetUtil.getResponse(WebAddress.MY_LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MY_LIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //我的收藏店铺
    public void myLikeShop() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&type=2";
                String result_data = NetUtil.getResponse(WebAddress.MY_LIKE_SHOP, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MY_LIKE_SHOP;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //我的评论
    public void myComment(final String type) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&type=" + type;//1是我的评价，2是卖家的评价
                String result_data = NetUtil.getResponse(WebAddress.MY_COMMENT, data);
                Log.e("get json success", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MY_COMMENT;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //删除我的足迹
    public void deleteMyFootprint(final String type) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&type=" + type;
                String result_data = NetUtil.getResponse(WebAddress.DELETE_MY_FOOTPRINT, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.DELETE_MY_FOOTPRINT;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //删除我的足迹某一条目
    public void deleteMyFootprintItem(final String type, final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&type=" + type + "&id=" + goodId;
                String result_data = NetUtil.getResponse(WebAddress.DELETE_MY_FOOTPRINT_ITEM, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.DELETE_MY_FOOTPRINT_ITEM;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //添加反馈留言
    public void addSuggestions(final String content) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&content=" + content;
                String result_data = NetUtil.getResponse(WebAddress.ADD_SUGGESTIONS, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ADD_SUGGESTIONS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的反馈留言列表
    public void getSuggestions() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebAddress.GET_SUGGESTIONS, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SUGGESTIONS;
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