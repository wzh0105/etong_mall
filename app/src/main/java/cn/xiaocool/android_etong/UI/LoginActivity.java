package cn.xiaocool.android_etong.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.xiaocool.android_etong.Listener.BaseUiListener;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.service.landDivideServeice;
import cn.xiaocool.android_etong.util.IntentUtils;
import cn.xiaocool.android_etong.util.KeyBoardUtils;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.etongApplaction;


/**
 * Created by 潘 on 2016/6/21.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private UserInfo user;
    private Context context;
    private Tencent mTencent;
    private BaseUiListener listener;
    private String phone, password;
    private EditText et_login_phone, et_login_password;
    private TextView tx_forget_password, tx_zhuce;
    private Button btn_login, btn_qq;
    private ProgressDialog progressDialog;
    private static final int MSG_SET_ALIAS = 1001;

    //以下为微信登录
    public static IWXAPI WXapi;
    private String weixinCode;
    private final static int LOGIN_WHAT_INIT = 1;
    private static String get_access_token = "";
    // 获取第一步的code后，请求以下链接获取access_token
    public static String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //获取用户个人信息
    public static String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    private BaseResp resp = null;
    private etongApplaction applaction;


    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.LOGIN:
                    try {
                        JSONObject json = (JSONObject) msg.obj;
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            Log.e("data", data);
                            JSONObject item = new JSONObject(data);
                            user.setUserId(item.getString("id"));
                            user.setUserImg(item.getString("photo"));
                            user.writeData(context);
                            progressDialog.dismiss();
                            if (JPushInterface.isPushStopped(context)) {
                                JPushInterface.resumePush(context);
                            }
                            // 调用 Handler 来异步设置别名
                            handle.sendMessage(handle.obtainMessage(MSG_SET_ALIAS, item.getString("id")));
                            Toast.makeText(LoginActivity.this, "登陆成功",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, data,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case MSG_SET_ALIAS:
                    Log.d("set alias", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                case CommunalInterfaces.CHECK_WECHAT_BIND:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")){
                            Log.e("openis",openid);
                            ToastUtils.makeShortToast(context,"微信登陆成功！");//跳转登录界面
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            user.setUserId(jsonObject1.getString("id"));
                            user.setUserImg(jsonObject1.getString("photo"));
                            user.writeData(context);
                            applaction.setResp(null);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Intent intent = new Intent();
                            intent.setClass(context, BindPhoneActivity.class);
                            intent.putExtra("openid", openid);
                            intent.putExtra("nickname", nickname);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //测试QQ登录代码
//                case CommunalInterfaces.GetUserInfoByQQ:
//                    JSONObject jsonObject1 = (JSONObject) msg.obj;
//                    try {
//                        String status = jsonObject1.getString("status");
//                        if (status.equals("success")) {
//                            JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
//                            user.setUserId(jsonObject2.getString("id"));
//                            user.setUserImg(jsonObject2.getString("photo"));
//                            user.writeData(context);
//                            context.startActivity(new Intent(context, MainActivity.class));
//                        }else {
//                            Intent intent = new Intent();
//                            intent.putExtra("openid","6EC516A02F07F5B7DFE2BF5D73EBEFCC");
//                            intent.setClass(context, QQBindingActivity.class);
//                            context.startActivity(intent);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
                default:
                    Log.i("handle msg", "Unhandled msg - " + msg.what);

            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("Set tag", logs);
                    Log.e("Set success","success");
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("Set alias", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    handle.sendMessageDelayed(handle.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    Log.e("Set success","success");

                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("Failed", logs);
            }
        }
    };
    private Button btnWeChat;
    private String openid;
    private String nickname;
    private String headimgurl;
    private Button btnBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        context = this;

        applaction = (etongApplaction) getApplication();

        listener = new BaseUiListener(context);

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("1105613476", this.getApplicationContext());
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        // 初始化视图
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        //启动服务 加载写入收货地址时的 省市区
        Intent i = new Intent(this, landDivideServeice.class);
        startService(i);
        user = new UserInfo();
        user.readData(this);
        if (user.isLogined()) {// 已登录
            IntentUtils.getIntent(LoginActivity.this, MainActivity.class);
            this.finish();
        }
        initview();
    }

    private void initview() {
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tx_forget_password = (TextView) findViewById(R.id.tx_forget_password);
        tx_forget_password.setOnClickListener(this);
        btn_qq = (Button) findViewById(R.id.btn_qq);
        btn_qq.setOnClickListener(this);
        tx_zhuce = (TextView) findViewById(R.id.tx_zhuce);
        tx_zhuce.setOnClickListener(this);
        btnWeChat = (Button) findViewById(R.id.btn_weixin);
        btnWeChat.setOnClickListener(this);
        btnBlog = (Button) findViewById(R.id.btn_weibo);
        btnBlog.setOnClickListener(this);
        if (!user.getUserPhone().equals("")) {
            user.readData(this);
            et_login_phone.setText(user.getUserPhone());
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = et_login_phone.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
        KeyBoardUtils.showKeyBoardByTime(et_login_phone, 300);
    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        Log.e("weixin","weixin");
        WXapi = WXAPIFactory.createWXAPI(this, "wxb32c00ffa8140d93", false);
        WXapi.registerApp("wxb32c00ffa8140d93");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);
        Log.e("weixin","weixin");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_zhuce:
                Intent intent = new Intent(LoginActivity.this, RegisiterActivity.class);
                startActivity(intent);
                break;
            case R.id.tx_forget_password:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_qq:
                //调用QQ登录
                login2();
//                listener.onComplete("{\"openid\":\"6EC516A02F07F5B7DFE2BF5D73EBEFCC\"}");
                //测试QQ登录代码
//                if (NetUtil.isConnnected(context)){
//                    new MainRequest(context,handle).GetUserInfoByQQ("6EC516A02F07F5B7DFE2BF5D73EBEFCC");
//                }else {
//                    Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.btn_weixin:
                //调用微信登录
                WXLogin();
                applaction.setjudgeCode("3");//设置微信调用模式为微信登录"3"
                break;
            case R.id.btn_weibo:
                //微博登录
                ToastUtils.makeShortToast(context,"微博登录开发中！");
                break;
        }
    }

    //登录
    private void login() {
        phone = et_login_phone.getText().toString();
        phone = phone.trim();
        password = et_login_password.getText().toString();
        user.setUserPhone(phone);
        user.setUserPassword(password);
        user.writeData(context);
        Log.e("et_login_phone is", et_login_phone.getText().toString());
        Log.e("et_login_password is", et_login_password.getText().toString());
        if (phone.length() == 11) {
            if (!password.equals("")) {
                progressDialog.setMessage("正在登陆");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new MainRequest(context, handle).login(phone, password);
            } else {
                Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                et_login_password.requestFocus();
            }
        } else {
            Toast.makeText(context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            et_login_phone.requestFocus();
        }
    }

    //QQ登录
    private void login2() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", listener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
            /*
             * resp是你保存在全局变量中的
			 */

        //设置微信登录全局变量

        resp = applaction.getResp();
        if (resp != null) {
            if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                // code返回
                weixinCode = ((SendAuth.Resp) resp).code;
                /*
				 * 将你前面得到的AppID、AppSecret、code，拼接成URL
				 */
                get_access_token = getCodeRequest(weixinCode);
                Thread thread = new Thread(downloadRun);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取access_token的URL（微信）
     *
     * @param code 授权时，微信回调给的
     * @return URL
     */
    public static String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID",
                urlEnodeUTF8("wxb32c00ffa8140d93"));//AppId
        GetCodeRequest = GetCodeRequest.replace("SECRET",
                urlEnodeUTF8("e05af493eca32b4e79c305cd4b72adea"));//
        GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }

    /**
     * 获取用户个人信息的URL（微信）
     *
     * @param access_token 获取access_token时给的
     * @param openid       获取access_token时给的
     * @return URL
     */
    public static String getUserInfo(String access_token, String openid) {
        String result = null;
        GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
                urlEnodeUTF8(access_token));
        GetUserInfo = GetUserInfo.replace("OPENID",
                urlEnodeUTF8(openid));
        result = GetUserInfo;
        return result;
    }

    public static String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Runnable downloadRun = new Runnable() {

        @Override
        public void run() {
            WXGetAccessToken();

        }
    };

    /**
     * 获取access_token等等的信息(微信)
     */
    private void WXGetAccessToken() {
        HttpClient get_access_token_httpClient = new DefaultHttpClient();
        HttpClient get_user_info_httpClient = new DefaultHttpClient();
        String access_token = "";
        String openid = "";
        try {
            HttpPost postMethod = new HttpPost(get_access_token);
            HttpResponse response = get_access_token_httpClient.execute(postMethod); // 执行POST方法
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream is = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                String str = "";
                StringBuffer sb = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                is.close();
                String josn = sb.toString();
                JSONObject json1 = new JSONObject(josn);
                access_token = (String) json1.get("access_token");
                openid = (String) json1.get("openid");


            } else {
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String get_user_info_url = getUserInfo(access_token, openid);
        WXGetUserInfo(get_user_info_url);
        Log.e("getuserinfor", get_user_info_url);
    }

    /**
     * 获取微信用户个人信息
     *
     * @param get_user_info_url 调用URL
     */
    private void WXGetUserInfo(String get_user_info_url) {
        Log.e("abcdef", "abcdefg");
        HttpClient get_access_token_httpClient = new DefaultHttpClient();
        openid = "";
        nickname = "";
        headimgurl = "";
        try {
            HttpGet getMethod = new HttpGet(get_user_info_url);
            HttpResponse response = get_access_token_httpClient.execute(getMethod); // 执行GET方法
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream is = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                String str = "";
                StringBuffer sb = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                is.close();
                String josn = sb.toString();
                JSONObject json1 = new JSONObject(josn);
                openid = (String) json1.get("openid");
                nickname = (String) json1.get("nickname");
                headimgurl = (String) json1.get("headimgurl");
                Log.e("return infor is", openid + "," + nickname + "," + headimgurl);
                if (NetUtil.isConnnected(context)){
                    new MainRequest(context,handle).checkWeChatBind(openid);//检查微信是否已经绑定手机号
                }else {

                }


            } else {
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTencent.logout(this);
    }


}

