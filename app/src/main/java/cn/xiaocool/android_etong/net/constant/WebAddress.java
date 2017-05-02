package cn.xiaocool.android_etong.net.constant;

/**
 * Created by 潘 on 2016/6/21.
 */
public interface WebAddress extends NetBaseConstant {
    /**
     * 发送验证码后缀
     */
    String SEND_CODE = NET_BASE_PREFIX + "a=SendMobileCode";
    /**
     * 注册后缀
     */
    String REGISTER = NET_BASE_PREFIX + "a=AppRegister";
    /**
     * 修改密码
     */
    String FORGETPASSWORD = NET_BASE_PREFIX + "a=forgetpwd";
    /**
     * 登录
     */
    String LOGIN = NET_BASE_PREFIX + "a=applogin";
    /**
     * 获取个人资料
     */
    String GETUSERINFO = NET_BASE_PREFIX + "a=getuserinfo";

    /**
     * 上传头像
     */
    String UPLOADAVATAR = NET_BASE_PREFIX + "a=uploadavatar";
    /**
     * 修改头像资料
     */
    String UPLOADUSERAVATAR = NET_BASE_PREFIX + "a=UpdateUserAvatar";
    /**
     * 修改昵称
     */
    String UPDATAUSERNAME = NET_BASE_PREFIX + "a=UpdateUserName";
    /**
     * 修改性别
     */
    String UPDATAUSERSEX = NET_BASE_PREFIX + "a=UpdateUserSex";
    /**
     * 修改手机
     */
    String UPDATAUSERPHONE = NET_BASE_PREFIX + "a=UpdateUserPhone";
    /**
     * 修改支付密码
     */
    String UPDATEPAYPASSWORD = NET_BASE_PREFIX + "a=UpdatePayPass";
    /**
     * 创建店铺
     */
    String CREATESHOP = NET_BASE_PREFIX + "a=CreateShop";
    /**
     * 下载头像
     */
    String GETAVATAR = "http://mwn.xiaocool.net/uploads/microblog/";

    /**
     * 获取我的店铺
     */

    String GETMYSHOP = NET_BASE_PREFIX + "a=GetMyShop";
    /**
     * 发布商品
     */

    String PUBLISHGOODS = NET_BASE_PREFIX + "a=PublishGoods";

    /**
     * 获取上架产品列表
     */

    String GETSHOPGOODLIST = NET_BASE_PREFIX + "a=GetShopGoodList";

    /**
     * 获取下架产品列表
     */

    String GETSHOPGOODLIST_XIAJIA = NET_BASE_PREFIX + "a=GetShopGoodList";

    /**
     * 下架产品
     */

    String GOODSXIAJIA = NET_BASE_PREFIX + "a=GoodsXiajia";

    /**
     * 上架产品
     */
    String GOODSSHANGJIA = NET_BASE_PREFIX + "a=GoodsShangjia";

    /**
     * 删除产品
     */

    String DELETEGOODS = NET_BASE_PREFIX + "a=DeleteGoods";

    /**
     * 修改店铺地址
     */

    String UPDATASHOPADDRESS = NET_BASE_PREFIX + "a=UpdateShopAddress";

    /**
     * 修改店铺头像
     */

    String UPDATESHOPPHOTO = NET_BASE_PREFIX + "a=UpdateShopPhoto";

    /**
     * 修改店铺名称
     */

    String UPDATASHOPNAME = NET_BASE_PREFIX + "a=UpdateShopName";

    /**
     * 修改店铺地址
     */

    String UPDATA_SHOP_ADDRESS = NET_BASE_PREFIX + "a=UpdateShopAddress";

    /**
     * 修改商品详情信息
     */

    String CHANGE_GOOD_INTRO = NET_BASE_PREFIX + "a=GetGoodsInfo";
    /**
     * 修改商品详情单条信息
     */

    String CHANGE_GOOD_INTRO_ITEM = NET_BASE_PREFIX;
    /**
     * 收藏商品
     */

    String LIKE_GOOD = NET_BASE_PREFIX + "a=addfavorite";
    /**
     * 取消收藏商品
     */

    String CANCLE_LIKE_GOOD = NET_BASE_PREFIX + "a=cancelfavorite";
    /**
     * 我收藏的宝贝
     */
    String MY_LIKE_GOOD = NET_BASE_PREFIX + "a=getfavoritelist";
    /**
     * 我收藏的店铺
     */
    String MY_LIKE_SHOP = NET_BASE_PREFIX + "a=getfavoritelist";

    /**
     * 获取单个商品详情
     */

    String GET_GOODS_INFO = NET_BASE_PREFIX + "a=GetGoodsInfo";

    /**
     * 获取单个店铺详情
     */

    String GET_SHOP_INFO = NET_BASE_PREFIX + "a=GetShopInfo";

    /**
     * 商城产品购买
     */

    String BOOKING_SHOPPING = NET_BASE_PREFIX + "a=bookingshopping";

    /**
     * 获取我的商城订单列表
     */

    String GETSHOPPINGORDERLIST = NET_BASE_PREFIX + "a=getshoppingorderlist";
    /**
     * 获取首页新品上市
     */
    String GET_NEW_ARRIVAL = NET_BASE_PREFIX + "a=GetHotGoodList";
    /**
     * 添加商品附加属性
     */
    String UPLOAD_GOOD_STANDARD = NET_BASE_PREFIX + "a=AddGoodsProperty";
    /**
     * 获取商品对应的附加属性列表
     */
    String GOOD_ATTACHED_PROPERTY = NET_BASE_PREFIX + "a=GetGoodPropertyList";
    /**
     * 上传商品的附加属性
     */
    String UPLOAD_GOOD_ATTRIBUTE = NET_BASE_PREFIX + "a=AddGoodsProperty";
    /**
     * 获取商品对应的附加属性列表
     */
    String OBTAIN_GOOD_ATTRIBUTE = NET_BASE_PREFIX + "a=GetGoodsPropertyList";
    /**
     * 获取店铺列表
     */

    String GET_SHOP_LIST = NET_BASE_PREFIX + "a=GetShopList";

    /**
     * 添加购物车
     */

    String ADD_SHOPPING_CART = NET_BASE_PREFIX + "a=AddShoppingCart";

    /**
     * 获取我的购物车
     */

    String GET_SHOPPPING_CART = NET_BASE_PREFIX + "a=GetShoppingCart";


    /**
     * 修改购物车
     */

    String Edit_Shopping_Cart = NET_BASE_PREFIX + "a=EditShoppingCart";

    /**
     * 删除购物车
     */

    String Delete_Shopping_Cart = NET_BASE_PREFIX + "a=DeleteShoppingCart";
    /**
     * 获取首页每日好店列表
     */

    String GET_HOMEPAGE_EVERY_GOODSHOP = NET_BASE_PREFIX + "a=GetDayShop";
    /**
     * 获取首页猜你喜欢
     */

    String GET_GUESS_LIKE = NET_BASE_PREFIX + "a=IsLike";
    /**
     * 获取卖家订单列表
     */

    String GET_SELLER_ORDER_LIST = NET_BASE_PREFIX + "a=Shopgetorderlist";

    /**
     * 产品发货
     */

    String SELLER_DELIVER_GOOD = NET_BASE_PREFIX + "a=DeliverOrder";
    /**
     * 产品发货
     */

    String PAY_ORDER_LIST = NET_BASE_PREFIX + "a=PayOrder";

    /**
     * 确认收货
     */

    String CONFIRM_GOOD = NET_BASE_PREFIX + "a=CountersignOrder";
    /**
     * 买家评价订单
     */

    String BUY_WRITE_COMMENT = NET_BASE_PREFIX + "a=BuyerSetEvaluate";
    /**
     * 我的评论
     */

    String MY_COMMENT = NET_BASE_PREFIX + "a=GetMyEvaluatelists";
    /**
     * 首页每日好店
     */

    String EVERYDAY_GOODSHOP = NET_BASE_PREFIX + "a=GetDayShop";


    /**
     * 搜索店铺
     */

    String SearchShops = NET_BASE_PREFIX + "a=SearchShops";

    /**
     * 搜索商品
     */

    String SearchGoods = NET_BASE_PREFIX + "a=SearchGoods";

    /**
     * e抢购
     */

    String IsE = NET_BASE_PREFIX + "a=IsE";

    /**
     * 今日特价
     */

    String IsPrice = NET_BASE_PREFIX + "a=IsPrice";

    /**
     * 新客专享
     */

    String IsNew = NET_BASE_PREFIX + "a=IsNew";

    /**
     * 猜你喜欢
     */

    String IsLike = NET_BASE_PREFIX + "a=IsLike";

    /**
     * 获取首页菜单列表
     */

    String GET_MENU = NET_BASE_PREFIX + "a=GetMenu";

    /**
     * 获取首页分类数据
     */
    String GET_GOODTYPE = NET_BASE_PREFIX + "a=getGoodsTypeList";
    /**
     * 获取商品评价信息
     */

    String GetGoodsComments = NET_BASE_PREFIX + "a=GetGoodsComments";

    /**
     * 获取商店type的附加属性
     */

    String GetGoodPropertyList = NET_BASE_PREFIX + "a=GetGoodPropertyList";

    /**
     * 添加商品的附加属性
     */

    String AddGoodsProperty = NET_BASE_PREFIX + "a=AddGoodsProperty";

    /**
     * 获取产品的附加属性
     */

    String GetGoodsPropertyList = NET_BASE_PREFIX + "a=GetGoodsPropertyList";

    /**
     * 取消订单
     */

    String CANCEL_ORDER = NET_BASE_PREFIX + "a=DeleteOrder";
    /**
     * 搜索订单
     */

    String SEARCH_ORDER = NET_BASE_PREFIX + "a=SearchOrderList";

    /**
     * 发送聊天信息
     */

    String SendChatData = NET_BASE_PREFIX + "a=SendChatData";

    /**
     * 获取聊天信息（两个人之间的）
     */

    String xcGetChatData = NET_BASE_PREFIX + "a=xcGetChatData";

    /**
     * 获取聊天列表
     */

    String xcGetChatListData = NET_BASE_PREFIX + "a=xcGetChatListData";

    /**
     * 获取我的钱包
     */

    String GetMyWallet = NET_BASE_PREFIX + "a=GetMyWallet";

    /**
     * 获取我的足迹
     */

    String GetMyBrowseHistory = NET_BASE_PREFIX + "a=GetMyBrowseHistory";
    /**
     *获取相关推荐
     */

    /**
     * 获取商品分类列表
     */

    String GET_GOODS_TYPE_LIST = NET_BASE_PREFIX + "a=getShopTypeList&type=0";

    String SHOP_GET_EVALUATE = NET_BASE_PREFIX + "a=SellerGetEvaluatelists";
    /**
     * 卖家确认验证码
     */
    String VerifyShoppingCode = NET_BASE_PREFIX + "g=apps&m=index&a=VerifyShoppingCode";




    String GET_GOOD_RECOMMEND = NET_BASE_PREFIX + "a=GetRecommendGoodsList";

    /**
     * 获取订单信息(传入订单id)
     */

    String GET_ORDER_DETAILS = NET_BASE_PREFIX + "a=GetOrderInfoById";
    /**
     * 修改商品轮播图
     */

    String CHANGE_GOOD_PICS = NET_BASE_PREFIX + "a=UpdateGoodsPicture";
    /**
     * 修改图文详情
     */

    String CHANGE_GOOD_PIC_DETAILS = NET_BASE_PREFIX + "a=UpdateGoodsCPicList";
    /**
     * 绑定微信和userid
     */

    String BIND_WECHAT_AND_USERID = NET_BASE_PREFIX + "a=UpdateUserWeixin";

    /**
     * 检查微信是否已经绑定了
     */

    String CHECK_WECHAT_BIND = NET_BASE_PREFIX + "a=GetUserInfoByWeixin";

    /**
     * 获取我的银行信息
     */

    String GetUserBankInfo = NET_BASE_PREFIX + "a=GetUserBankInfo";
    /**
     * 获取我的支付密码信息
     */

    String GetUserPayPass = NET_BASE_PREFIX + "a=GetUserPayPass";

    /**
     * 绑定个人银行卡账号
     */

    String UpdateUserBank = NET_BASE_PREFIX + "a=UpdateUserBank";
    /**
     * 分享H5页面(店铺)
     */

    String SHARE_SHOP_TO_FRIEND = SHARE_H5_PREFIX + "a=shop&id=";
    /**
     * 分享H5页面(商品)
     */

    String SHARE_GOOD_TO_FRIEND = SHARE_H5_PREFIX + "a=goods&id=";

    /**
     * 获取我的代理店铺列表
     */

    String GET_MY_AGENCY_SHOP_LIST = NET_BASE_PREFIX + "a=GetShopListByAgentId";

    /**
     * 提现申请
     */

    String ApplyWithdraw = NET_BASE_PREFIX + "a=ApplyWithdraw";

    /**
     * 我的提现申请记录
     */

    String GetMyApplyWithdraw = NET_BASE_PREFIX + "a=GetMyApplyWithdraw";
    /**
     * 限时抢购
     */

    String GetTimeGoodList = NET_BASE_PREFIX + "a=GetTimeGoodList";
    /**
     * qq登录
     */

    String GetUserInfoByQQ = NET_BASE_PREFIX + "a=GetUserInfoByQQ";

    /**
     * 绑定QQ
     */

    String UpdateUserQQ = NET_BASE_PREFIX + "a=UpdateUserQQ";

    /**
     * 删除我的足迹
     */

    String DELETE_MY_FOOTPRINT = NET_BASE_PREFIX + "a=deleteBrowseHistoryByUserid";
    /**
     * 删除我的足迹某一条目
     */

    String DELETE_MY_FOOTPRINT_ITEM = NET_BASE_PREFIX + "a=deleteBrowseHistoryById";

    /**
     * 分享赚佣金H5页面
     */

    String SHARE_TO_EARN = SHARE_TO_EARN_PREFIX + "&userid=";
    /**
     * 添加留言反馈
     */

    String ADD_SUGGESTIONS = NET_BASE_PREFIX + "a=addfeedback";
    /**
     * 添加留言反馈
     */

    String GET_SUGGESTIONS = NET_BASE_PREFIX + "a=getfeedbackList";

    /**
     * 营业额查询
     */

    String ShopGetTotalorder = NET_BASE_PREFIX + "a=ShopGetTotalorder";

    /**
     * 营业额列表
     */
    String ShopGetTotalOrderList = NET_BASE_PREFIX + "a=ShopGetTotalOrderList";
    /**
     * 发布同城互动
     */
    String PUBLISH_CITY_BBS = NET_BASE_PREFIX + "a=addbbsposts";
    /**
     * 活动报名
     */
    String ACTIVITY_REGISTER = NET_BASE_PREFIX + "a=ApplyActivity";

    /**
     * 活动检测是否报名
     */
    String CHECK_ACTIVITY_REGISTER = NET_BASE_PREFIX + "a=CheckHadApply";


    /**
     * 获取分类中的货物数据
     */
    String GET_GOODS_LIST =NET_BASE_PREFIX + "a=GetTypeGoodList";
}


