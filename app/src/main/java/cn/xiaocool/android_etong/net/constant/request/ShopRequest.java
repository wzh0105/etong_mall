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
public class ShopRequest {
    private UserInfo user;
    private Context mContext;
    private Handler handler;

    public ShopRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
        user.readData(mContext);
    }

    //收藏宝贝
    public void likeGood(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodId + "&type=1";
                Log.e("user id is ", user.getUserId());
                String result_data = NetUtil.getResponse(WebAddress.LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LIKE_GOOD;
                    msg.obj = obj;
//                    Log.e(WebAddress.LIKE_GOOD+data,result_data);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //取消收藏店铺
    public void cancelLikeShop(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodId + "&type=" + "2";
                String result_data = NetUtil.getResponse(WebAddress.CANCLE_LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CANCLE_LIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //取消收藏宝贝
    public void cancelLike(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodId + "&type=" + "1";
                String result_data = NetUtil.getResponse(WebAddress.CANCLE_LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CANCLE_LIKE_GOOD;
                    msg.obj = obj;
                    Log.e(data, result_data);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //收藏店铺-------------尚未完成
    public void likeShop(final String shopId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + shopId + "&type=1";
                Log.e("user id is ", user.getUserId());
                String result_data = NetUtil.getResponse(WebAddress.LIKE_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LIKE_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    public void uploadStandard(final String goodId, final String standardName, final String standardType) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&goodsid=" + goodId + "&type=" + standardName + "&propertylist=" + standardType;
                String result_data = NetUtil.getResponse(WebAddress.UPLOAD_GOOD_STANDARD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPLOAD_GOOD_STANDARD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商品附加属性
    public void obtainAttachedProperty(final String goodType) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&goodstype=" + goodType;//传入商店类型type
                String result_data = NetUtil.getResponse(WebAddress.GOOD_ATTACHED_PROPERTY, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GOOD_ATTACHED_PROPERTY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //上传商品附加属性
    public void uploadGoodAttribute(final String goodsId, final String type, final String propertyList) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&goodsid=" + goodsId + "&type=" + type + "&propertylist=" + propertyList;
                String result_data = NetUtil.getResponse(WebAddress.UPLOAD_GOOD_ATTRIBUTE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPLOAD_GOOD_ATTRIBUTE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商品附加属性(传入goodid)
    public void obtainGoodProperty(final String goodId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&goodsid=" + goodId;
                String result_data = NetUtil.getResponse(WebAddress.OBTAIN_GOOD_ATTRIBUTE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.OBTAIN_GOOD_ATTRIBUTE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //产品发货
    public void sellerDeliverGood(final String orderId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + orderId;
                Log.e("deliver good success", orderId);
                String result_data = NetUtil.getResponse(WebAddress.SELLER_DELIVER_GOOD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SELLER_DELIVER_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //产品发货
    public void confirmGood(final String orderId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + orderId;
                Log.e("deliver good success", orderId);
                String result_data = NetUtil.getResponse(WebAddress.CONFIRM_GOOD, data);

                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CONFIRM_GOOD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //订单付款
    public void payOrder(final String orderId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + orderId;
                String result_data = NetUtil.getResponse(WebAddress.PAY_ORDER_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.PAY_ORDER_LIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //买家订单评论
    public void buyWriteComment(final String orderId, final String comment) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&orderid=" + orderId +
                        "&type=1" + "&content=" + comment + "&attitudescore=5&finishscore=5&effectscore=5";//星级评分写死
                String result_data = NetUtil.getResponse(WebAddress.BUY_WRITE_COMMENT, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BUY_WRITE_COMMENT;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //买家取消订单
    public void cancelOrder(final String orderId, final String reason) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&id=" + orderId + "&reason=" + reason;
                String result_data = NetUtil.getResponse(WebAddress.CANCEL_ORDER, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CANCEL_ORDER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //买家搜索订单
    public void searchOrder(final String orderName) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsname=" + orderName;
                String result_data = NetUtil.getResponse(WebAddress.SEARCH_ORDER, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEARCH_ORDER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商品相关推荐
    public void getGoodsRecommend(final String goodid, final String cityName,
                                  final String longitude, final String latitude) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodid + "&cityname=" + cityName
                        + "&longitude=" + longitude + "&latitude=" + latitude;
                String result_data = NetUtil.getResponse(WebAddress.GET_GOOD_RECOMMEND, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_GOOD_RECOMMEND;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商品列表list
    public void getGoodstypeList() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "";
                String result_data = NetUtil.getResponse(WebAddress.GET_GOODS_TYPE_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_GOODS_TYPE_LIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //卖家获取收到的评价
    public void getMyEvaluate() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebAddress.SHOP_GET_EVALUATE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SHOP_GET_EVALUATE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //卖家确认验证码
    public void verifyShoppingCode(final String result) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&code=" + result;
                String result_data = NetUtil.getResponse(WebAddress.VerifyShoppingCode, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.VerifyShoppingCode;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取订单详情
    public void getOrderDetails(final String orderId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&orderid=" + orderId;
                String result_data = NetUtil.getResponse(WebAddress.GET_ORDER_DETAILS, data);
                Log.e("resu;lt",result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_ORDER_DETAILS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //获取e代理店铺列表
    public void getMyAgencyShopList(final String agentId) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&agentid=" + agentId;
                String result_data = NetUtil.getResponse(WebAddress.GET_MY_AGENCY_SHOP_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_MY_AGENCY_SHOP_LIST;
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