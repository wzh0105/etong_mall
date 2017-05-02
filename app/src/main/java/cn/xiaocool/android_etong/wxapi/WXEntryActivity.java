package cn.xiaocool.android_etong.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.xiaocool.android_etong.view.etongApplaction;

/**
 * Created by hzh on 2016/11/11.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private BaseResp resp = null;
    private etongApplaction applaction;
    private String judgeCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applaction = (etongApplaction) getApplication();
        judgeCode = applaction.getjudgeCode();
        api = WXAPIFactory.createWXAPI(this, "wxb32c00ffa8140d93", false);
        api.handleIntent(getIntent(), this);
        Log.e("aaa", "abc");
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp != null) {
            this.resp = resp;
            applaction.setResp(resp);
        }
        switch (judgeCode) {
            case "0"://微信调用模式为  分享购
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        result = "分享购成功！现在可以享受优惠价！";
                        applaction.setShareBuyCode(1);
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        result = "取消分享购！";
                        applaction.setShareBuyCode(0);
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "发送被拒绝";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        result = "发送返回";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
                break;
            case "1"://微信调用模式为  分享店铺
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        result = "店铺分享成功！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        result = "取消店铺分享！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "发送被拒绝";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        result = "发送返回";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
                break;
            case "2"://微信调用模式为  分享商品
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        result = "商品分享成功！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        result = "取消分享商品！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "发送被拒绝";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        result = "发送返回";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
                break;
            case "3"://微信调用模式为  微信登录
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        result = "微信登录成功！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        result = "取消微信登录！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "发送被拒绝";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        result = "发送返回";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
                break;
            case "4"://微信调用模式为  分享赚佣金
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        result = "分享微信赚佣金成功！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        result = "取消微信分享赚佣金！";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "发送被拒绝";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    default:
                        result = "发送返回";
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }
}