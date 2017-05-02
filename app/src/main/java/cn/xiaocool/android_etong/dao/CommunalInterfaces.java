package cn.xiaocool.android_etong.dao;

/**
 * Created by 潘 on 2016/6/21.
 */
public interface CommunalInterfaces {
    //main number 0x0000开始
    int SEND_CODE = 0x0000;//发送验证码
    int BTN_UNTOUCH = 0x0001;//发送验证码按钮不可点击
    int BTN_TOUCH = 0x0002;//发送验证码按钮可点击
    int REGISTER = 0x0003;//注册
    int FORGETPASSWORD = 0x0004;//忘记密码
    int LOGIN = 0x0005;//登录
    int BIND_WECHAT_AND_USERID = 0x0006;//绑定微信
    int CHECK_WECHAT_BIND = 0x0007;//检查是否已经绑定微信
    int DELETE_MY_FOOTPRINT = 0x0008;//删除我的足迹
    int DELETE_MY_FOOTPRINT_ITEM = 0x0009;//删除我的足迹某一条目
    int ADD_SUGGESTIONS = 0x0010;//添加留言反馈
    int GET_SUGGESTIONS = 0x0011;//获取留言反馈列表
    int PUBLISH_CITY_BBS = 0x0012;//发布同城互动
    int ACTIVITY_REGISTER = 0x0013;//我的店铺活动报名
    int CHECK_ACTIVITY_REGISTER = 0x0014;//我的店铺检测活动报名
    //首页商城(0x0100开始)
    int LIKE_GOOD = 0x0100;//收藏商城商品
    int CANCLE_LIKE_GOOD = 0x0101;//取消收藏商城商品
    int GET_NEW_ARRIVAL = 0x0102;//获取首页新品上市
    int GET_HOMEPAGE_EVERY_GOODSHOP = 0x0103;//获取每日好店
    int GET_GUESS_LIKE = 0x0104;//获取猜你喜欢
    int GET_MENU = 0x0105;//获取菜单列表
    int GET_MENU3 = 0x0106;//获取三级列表

    //修改资料
    int UPLOADAVATAR = 0x0006;//上传头像
    int UPUSERAVATAR = 0x0007;//修改用户头像资料
    int GETUSERINFO = 0x0008;//获取用户基本资料
    int UPDATAUSERNAME = 0x0009;//修改姓名
    int UPDATAUSERSEX = 0x0010;//修改性别
    int UPDATAUSERPHONE = 0x0011;//修改手机号
    int UPDATAUSERPASS = 0x0061;//修改账号密码

    //商家
    int AUTHENTICATION = 0x0012; //商家身份认证
    int POSITIVE_PIC = 0x0013; //身份证前面照片
    int OPPOSITE_PIC = 0x0014; //身份证后面照片
    int LICENSE_PIC = 0x0015; //营业执照照片
    int CREATESHOP = 0x0016;//创建店铺
    int GETMYSHOP = 0x0017;//获取我的店铺
    int PUBLISHGOODS = 0x0018;//发布商城
    int GETSHOPGOODLIST = 0x0019;//获取商城商品——上架
    int GETSHOPGOODLIST_XIAJIA = 0x0020;//获取商城商品——下架
    int GOODSXIAJIA = 0x0021;//下架
    int GOODSSHANGJIA = 0x0022;//上架
    int DELETEGOODS = 0x0023;//删除产品
    int UPDATASHOPADDRESS = 0x0024;//修改商铺地址(头像)
    int UPDATASHOPNAME = 0x0025;//修改店铺名称
    int CHANGE_GOOD_INTRO = 0x0026;//修改宝贝详情
    int CHANGE_GOOD_INTRO_ITEM = 0x0027;//修改宝贝详情单条信息
    int GET_GOODS_INFO = 0x0028;//获取单个商品详情
    int GET_SHOP_INFO = 0x0029;//获取单个店铺详情
    int BOOKING_SHOPPING = 0x0030;//商城产品购买
    int GET_SHOPPING_ORDER_LIST = 0x0031;// 获取我的商城订单列表
    int MY_LIKE_GOOD = 0x0032;//我喜欢的宝贝
    int MY_LIKE_SHOP = 0x0033;//我喜欢的商店
    int UPLOAD_GOOD_STANDARD = 0x0050;//上传商品属性
    int GOOD_ATTACHED_PROPERTY = 0x0051;//获取商品附加属性
    int UPLOAD_GOOD_ATTRIBUTE = 0x0052;//上传商品附加属性
    int OBTAIN_GOOD_ATTRIBUTE = 0x0052;//获取商品附加属性传入goodid
    int GET_SELLER_ORDER_LIST = 0x0053;//获取卖家中心订单
    int SELLER_DELIVER_GOOD = 0x0054;//产品发货
    int CONFIRM_GOOD = 0x0055;//确认收货
    int PAY_ORDER_LIST = 0x0055;//支付订单
    int MY_COMMENT = 0x0056;//我的评价
    int BUY_WRITE_COMMENT = 0x005;//买家评论订单
    int UPDATE_SHOP_PHOTO = 0x0034;//修改店铺头像
    int GET_SHOP_LIST = 0x0035;//获取店铺列表
    int UPDATA_SHOP_ADDRESS = 0x0036;//修改店铺地址
    int ADD_SHOPPING_CART = 0x0037;//添加购物车
    int GET_SHOPPING_CART = 0x0038;//获取我的购物车
    int EDIT_SHOPPING_CART = 0x0039;//修改购物车
    int DELETE_SHOPPING_CART = 0x0040;//删除购物车
    int UPDATA_SHOPPING_CART = 0x0041;//更新购物车
    int SEARCH_SHOPS = 0x0042;//搜索店铺
    int SEARCH_GOODS = 0x0043;//搜索商品
    int IsE = 0x0044;//e抢购
    int IsPrice = 0x0045;//今日特价
    int IsNew = 0x0046;//新客专享
    int IsLike = 0x0047;//猜你喜欢
    int GetGoodsComments = 0x0048;//获取商品评价信息
    int GetGoodPropertyList = 0x0049;//获取商品附加属性
    int AddGoodsProperty = 0x0050;//添加产品的附加属性
    int GetGoodsPropertyList = 0x0051;//获取商品附加属性
    int CANCEL_ORDER = 0x0070;//取消订单
    int SEARCH_ORDER = 0x0071;//搜索订单
    int GET_MY_SHOP_TEXT = 0x0072;//获取我的店铺状态以设置文字
    int SendChatData = 0x0073;//发送聊天信息
    int xcGetChatData = 0x0074;//获取聊天信息（两个人之间的）
    int xcGetChatListData = 0x0075;//获取聊天列表
    int GetMyWallet = 0x0076;//获取我的钱包
    int GetMyBrowseHistory = 0x0077;//获取我的足迹
    int GET_GOOD_RECOMMEND = 0x0078;//获取商品相关推荐
    int GET_GOODS_TYPE_LIST = 0x0079;//获取商品分类列表
    int SHOP_GET_EVALUATE = 0x0080;//卖家获取收到的评价
    int VerifyShoppingCode = 0x0081;//卖家确认验证码
    int GET_ORDER_DETAILS = 0x0082;//根据订单id查询订单信息
    int CHANGE_GOOD_PICS = 0x0083;//修改商品轮播图
    int CHANGE_GOOD_PIC_DETAILS = 0x0084;//修改商品图文详情图片集
    int GetUserBankInfo = 0x0085;//获取我的银行信息
    int UpdateUserBank = 0x0086;// 绑定个人银行卡账号
    int GET_MY_AGENCY_SHOP_LIST = 0x0087;
    int ApplyWithdraw = 0x0088;// 提现申请
    int GetMyApplyWithdraw = 0x0089;// 我的提现申请记录
    int GetTimeGoodList = 0x0090;//根据时间获得 限时抢购
    int GetUserInfoByQQ = 0x0091;//QQ登录
    int UpdateUserQQ = 0x0092;//绑定QQ
    int UpdatePayPass = 0x0093;//修改支付密码
    int ShopGetTotalorder = 0x0094;//营业额查询
    int ShopGetTotalOrderList = 0x0095;//营业额列表

}
