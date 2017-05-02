package cn.xiaocool.android_etong.fragment;


import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.ForgetPasswordActivity;
import cn.xiaocool.android_etong.UI.LoginActivity;
import cn.xiaocool.android_etong.UI.Mine.AgentActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.ApplyShopActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.AuditShopActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.ChatListActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.MyCommentActivity;
import cn.xiaocool.android_etong.UI.Mine.BusinessActivity;
import cn.xiaocool.android_etong.UI.Mine.CouponActivity;
import cn.xiaocool.android_etong.UI.Mine.GetSuggestionsListActivity;
import cn.xiaocool.android_etong.UI.Mine.MemberCenterActivity;
import cn.xiaocool.android_etong.UI.Mine.MineEditActivity;
import cn.xiaocool.android_etong.UI.Mine.MineFootprintActivity;
import cn.xiaocool.android_etong.UI.Mine.MyEvaluateActivity;
import cn.xiaocool.android_etong.UI.Mine.MyLikeActivity;
import cn.xiaocool.android_etong.UI.Mine.RightsCenterActivity;
import cn.xiaocool.android_etong.UI.Mine.WalletActivity;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.tool.zxingCode.activity.CaptureActivity;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.etongApplaction;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.xiaocool.android_etong.net.constant.WebAddress.SHARE_TO_EARN;
import static cn.xiaocool.android_etong.util.StatusBarHeightUtils.getStatusBarHeight;
import static cn.xiaocool.android_etong.view.etongApplaction.api;

/**
 * Created by 潘 on 2016/6/12.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.mine_btn_allOrder)
    RelativeLayout mineBtnAllOrder;
    @BindView(R.id.mine_btn_noPay)
    Button mineBtnNoPay;
    @BindView(R.id.mine_btn_noUse)
    Button mineBtnNoUse;
    @BindView(R.id.mine_btn_noDeliver)
    Button mineBtnNoDeliver;
    @BindView(R.id.mine_btn_noConfirm)
    Button mineBtnNoConfirm;
    @BindView(R.id.mine_btn_noEvaluate)
    Button mineBtnNoEvaluate;
    @BindView(R.id.btn_fenxiang)
    Button btn_fenxiang;
    @BindView(R.id.btn_qianbao)
    Button btn_qianbao;
    @BindView(R.id.btn_zuji)
    Button btn_zuji;
    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;
    @BindView(R.id.btn_daili)
    Button btn_daili;
    @BindView(R.id.btn_weiquan)
    Button btn_weiquan;
    private ImageView img_setup, iv_saoyisao;
    private CircleImageView img_mine_head;
    private RelativeLayout ry_line, rl_mine_shoucang;
    private Button btn_kaidian;
    private TextView tx_mine_name, tx_mine_vip;
    private ProgressDialog progressDialog;
    private Context context;
    private String name, touxiang;
    private UserInfo userInfo;
    private SharedPreferences sp;
    private etongApplaction applaction;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //获取个人资料
                case CommunalInterfaces.GETUSERINFO:
                    try {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        String state = jsonObject.getString("status");
                        if (state.equals("success")) {
                            Log.e("success", "加载资料更新成功");
                            JSONObject object = jsonObject.getJSONObject("data");
                            if (object.getString("name").equals("null")) {
                                tx_mine_name.setText("未设置昵称");
                            } else {
                                tx_mine_name.setText(object.getString("name"));
                            }
                            name = object.getString("name");
                            touxiang = object.getString("photo");
                            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + object.getString("photo"), img_mine_head);
                            tx_mine_vip.setText("V"+object.getString("level"));
                        } else {
                            Toast.makeText(context, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GETMYSHOP:
                    Log.e("getmyshop", "getmyshop");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject.getString("status");
                            String data = jsonObject.getString("data");
                            if (status.equals("success")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String shopid = jsonObject1.getString("id");
                                String islocal = jsonObject1.getString("islocal");
                                progressDialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("shopid", shopid);
                                intent.putExtra("islocal", islocal);
                                intent.setClass(context, BusinessActivity.class);
                                startActivity(intent);
                            }
                            //未开店
                            else if (data.equals("-10")) {
                                //申请店铺
                                startActivity(new Intent(context, ApplyShopActivity.class));
                                progressDialog.dismiss();
                            } else if (data.equals("0")) {
                                //正在审核
                                startActivity(new Intent(context, AuditShopActivity.class));
                                progressDialog.dismiss();

                            } else if (data.equals("-1")) {
                                Toast.makeText(context, "您的认证失败", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else if (data.equals("-2")) {
                                Toast.makeText(context, "您的店铺已被禁用", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    btn_kaidian.setEnabled(true);
                    break;
                case CommunalInterfaces.GET_MY_SHOP_TEXT:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            btn_kaidian.setText("我的店铺");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;


            }
        }
    };
    private Button btnComment;
    private Button btnVip;
    private Button btnSuggestions, btncheckUpdate, btnShareToEarn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        applaction = new etongApplaction();
        context = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置状态栏高度
//        ry_line = (RelativeLayout) getView().findViewById(R.id.lin);
//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ry_line.getLayoutParams();
//        linearParams.height = getStatusBarHeight(context);
//        ry_line.setLayoutParams(linearParams);
        initview();
        userInfo = new UserInfo();
        userInfo.readData(context);
        sp = context.getSharedPreferences("list", context.MODE_PRIVATE);
        new MainRequest(context, handler).getMyShopText();
    }

    private void initview() {
        new MainRequest(context, handler).userinfo();
        btnVip = (Button) getView().findViewById(R.id.btn_huiyuanzhongxin);
        btnVip.setOnClickListener(this);
        tx_mine_name = (TextView) getView().findViewById(R.id.tx_mine_name);
        img_mine_head = (CircleImageView) getView().findViewById(R.id.img_mine_head);
        img_mine_head.setOnClickListener(this);
        btn_kaidian = (Button) getView().findViewById(R.id.btn_kaidian);
        btn_kaidian.setOnClickListener(this);
        img_setup = (ImageView) getView().findViewById(R.id.img_setup);
        img_setup.setOnClickListener(this);
        rl_mine_shoucang = (RelativeLayout) getView().findViewById(R.id.mine_btn_my_like);
        rl_mine_shoucang.setOnClickListener(this);
        mineBtnAllOrder.setOnClickListener(this);
        mineBtnNoPay.setOnClickListener(this);
        mineBtnNoUse.setOnClickListener(this);
        mineBtnNoDeliver.setOnClickListener(this);
        mineBtnNoConfirm.setOnClickListener(this);
        mineBtnNoEvaluate.setOnClickListener(this);
        btnComment = (Button) getView().findViewById(R.id.btn_comment);
        btnComment.setOnClickListener(this);
        btn_fenxiang = (Button) getView().findViewById(R.id.btn_fenxiang);
        btn_fenxiang.setOnClickListener(this);
        btn_qianbao.setOnClickListener(this);
        btn_zuji.setOnClickListener(this);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        iv_saoyisao = (ImageView) getView().findViewById(R.id.saoyisao);
        iv_saoyisao.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        btn_daili.setOnClickListener(this);
        btn_weiquan.setOnClickListener(this);
        tx_mine_vip = (TextView) getView().findViewById(R.id.tx_mine_vip);
        btnSuggestions = (Button) getView().findViewById(R.id.btn_suggestions);
        btnSuggestions.setOnClickListener(this);
        btncheckUpdate = (Button) getView().findViewById(R.id.btn_check_update);
        btncheckUpdate.setOnClickListener(this);
        btnShareToEarn = (Button) getView().findViewById(R.id.btn_share_to_earn);
        btnShareToEarn.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                new MainRequest(context, handler).userinfo();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_mine_head:
                startActivityForResult(new Intent(context, MineEditActivity.class), 1);
                break;
            case R.id.btn_kaidian:
                progressDialog.setMessage("正在加载");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new MainRequest(context, handler).getmyshop();
                break;
            case R.id.img_setup:
                showPopupMenu(img_setup);
                break;
            case R.id.mine_btn_my_like:
                IntentUtils.getIntent((Activity) context, MyLikeActivity.class);
                break;
            case R.id.btn_comment:
                IntentUtils.getIntent((Activity) context, MyCommentActivity.class);//跳转我的评价
                break;
            case R.id.mine_btn_allOrder:
                Intent intent = new Intent();
                intent.putExtra("indent", 0);
                intent.setClass(context, MyEvaluateActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_btn_noPay:
                Intent intent1 = new Intent();
                intent1.putExtra("indent", 1);
                intent1.setClass(context, MyEvaluateActivity.class);
                startActivity(intent1);
                break;
            case R.id.mine_btn_noUse:
                Intent intent2 = new Intent();
                intent2.putExtra("indent", 2);
                intent2.setClass(context, MyEvaluateActivity.class);
                startActivity(intent2);
                break;
            case R.id.mine_btn_noDeliver:
                Intent intent3 = new Intent();
                intent3.putExtra("indent", 3);
                intent3.setClass(context, MyEvaluateActivity.class);
                startActivity(intent3);
                break;
            case R.id.mine_btn_noConfirm:
                Intent intent4 = new Intent();
                intent4.putExtra("indent", 4);
                intent4.setClass(context, MyEvaluateActivity.class);
                startActivity(intent4);
                break;
            case R.id.mine_btn_noEvaluate:
                Intent intent5 = new Intent();
                intent5.putExtra("indent", 5);
                intent5.setClass(context, MyEvaluateActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_fenxiang:
                Intent intent6 = new Intent();
                intent6.setClass(context, ChatListActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_qianbao:
                Intent intent7 = new Intent();
                intent7.setClass(context, WalletActivity.class);
                intent7.putExtra("name", name);
                intent7.putExtra("touxiang", touxiang);
                startActivity(intent7);
                break;
            case R.id.btn_zuji:
                Intent intent8 = new Intent();
                intent8.setClass(context, MineFootprintActivity.class);
                startActivity(intent8);
                break;
            case R.id.saoyisao:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            case R.id.rl_coupon:
                startActivity(new Intent(getActivity(), CouponActivity.class));
                break;
            case R.id.btn_daili:
                startActivity(new Intent(getActivity(), AgentActivity.class));
                break;
            case R.id.btn_weiquan:
                startActivity(new Intent(getActivity(), RightsCenterActivity.class));
                break;
            case R.id.btn_huiyuanzhongxin:
                startActivity(new Intent(getActivity(), MemberCenterActivity.class));
                break;
            //意见反馈
            case R.id.btn_suggestions:
                startActivity(new Intent(getActivity(), GetSuggestionsListActivity.class));
                break;
            //检查更新
            case R.id.btn_check_update:

                break;
            //分享赚佣金
            case R.id.btn_share_to_earn:
                showSharePopwindow();
                break;

        }
    }

    private void showPopupMenu(View view) {
        Log.e("popupmenu", "success");
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(context, view);
        //menu 布局
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.updatepass:
                        Intent intent1 = new Intent(context, ForgetPasswordActivity.class);
                        startActivityForResult(intent1, 5);
                        break;
                    case R.id.quit:
                        userInfo.clearDataExceptPhone(context);
                        SharedPreferences.Editor e = sp.edit();
                        e.clear();
                        e.commit();
                        userInfo.setUserId(null);
                        startActivity(new Intent(context, LoginActivity.class));
                        getActivity().finish();
                        JPushInterface.stopPush(context);
                        Toast.makeText(context, "退出登录成功", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
//        // PopupMenu关闭事件 重写方法
//        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(context, "关闭PopupMenu", Toast.LENGTH_SHORT).show();
//            }
//        });
        popupMenu.show();

    }


    /**
     * 显示分享到社交app的popupWindow
     */
    private void showSharePopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.show_share_good_popup_window, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setOutsideTouchable(true); // 设置popupwindow外部可点击
        window.setFocusable(true); // 获取焦点

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x0000);
        window.setBackgroundDrawable(dw);


        //设置背景半透明
        WindowManager.LayoutParams lp = this.getActivity().getWindow().getAttributes();
        lp.alpha = 0.3f;
        this.getActivity().getWindow().setAttributes(lp);
//                        backgroundAlpha(1f);
        ColorDrawable cd = new ColorDrawable(0x0000);
        window.setBackgroundDrawable(cd);


//        // 设置popWindow的显示和消失动画
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(this.getView().findViewById(R.id.btn_share_to_earn),
                Gravity.BOTTOM, 0, 0);


        // 这里检验popWindow里的button是否可以点击
//        Button first = (Button) view.findViewById(R.id.first);
        Button icWeChat = (Button) view.findViewById(R.id.pop_share_to_weChat_icon);
        Button icFriend = (Button) view.findViewById(R.id.pop_share_to_weChat_friend_icon);
        Button icQQ = (Button) view.findViewById(R.id.pop_share_to_qq_icon);
        Button icMicroBlog = (Button) view.findViewById(R.id.pop_share_to_microBlog_icon);
        icWeChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                share2weixin(0, userInfo.getUserId());//好友
                applaction.setjudgeCode("4");//设置微信分享赚佣金4
                window.dismiss();
            }
        });
        icFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                share2weixin(1, userInfo.getUserId());//朋友圈
                applaction.setjudgeCode("4");//设置微信分享赚佣金4
                window.dismiss();
            }
        });
        icQQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtils.makeShortToast(context, "分享到QQ功能正在开发中");
            }
        });
        icMicroBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtils.makeShortToast(context, "分享到微博功能正在开发中");
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                //设置背景变回原色
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

    }

    //分享到微信
    private void share2weixin(int flag, String userId) {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = SHARE_TO_EARN + userId;//分享赚佣金传入userId
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = "e通商城";
        msg.description = "我推荐了e通商城app给你，快来体验吧！";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.drawable.share_to_wechat_icon);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
    }


}
