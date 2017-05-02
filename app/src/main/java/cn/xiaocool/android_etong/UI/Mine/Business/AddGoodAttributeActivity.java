package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.AddGoodAttributeAdapter;
import cn.xiaocool.android_etong.bean.UploadGoodSanndard.UploadStandardBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/7/31.
 */
public class AddGoodAttributeActivity extends Activity {
    private AddGoodAttributeAdapter addGoodAttributeAdapter;
    private GridView gridView;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private TextView tvUploadNow;
    private String goodId, type;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.UPLOAD_GOOD_ATTRIBUTE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(AddGoodAttributeActivity.this, "上传规格成功！");
                            finish();
//                            popupDialog();
                        } else {
                            ToastUtils.makeShortToast(AddGoodAttributeActivity.this, "上传规格失败！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

//    private void popupDialog() {
//            LayoutInflater inflater = LayoutInflater.from(this);
//            RelativeLayout layout = (RelativeLayout) inflater.inflate(
//                    R.layout.upload_inventory_dialog, null);
//
//            // 对话框
//            final Dialog dialog = new AlertDialog.Builder(this)
//                    .create();
//            dialog.show();
//            dialog.getWindow().setContentView(layout);
//
//            // 取消按钮
//            Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
//            btnCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//
//                }
//            });
//
//            // 确定按钮
//            Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
//            btnOK.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    Intent intent = new Intent();
//                    intent.setClass(this,UploadInventoryActivity.class);
//                    intent.putExtra("goodId",goodId);
//                    startActivity(intent);
//                    AddGoodAttributeActivity.this.finish();
//                }
//            });
//        }

    private StringBuffer plist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_good_attribute);
        initView();
        List<UploadStandardBean.DataBean.PlistBean> list =
                (ArrayList<UploadStandardBean.DataBean.PlistBean>) getIntent().getSerializableExtra("list");
        addGoodAttributeAdapter = new AddGoodAttributeAdapter(this, list);
        goodId = getIntent().getStringExtra("goodId");
        type = getIntent().getStringExtra("type");
        gridView.setAdapter(addGoodAttributeAdapter);
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView_add_good_attribute);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("选择规格");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_back) {
                    finish();
                }
            }
        });
        tvUploadNow = (TextView) findViewById(R.id.add_good_attribute_btn_upload);
        tvUploadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.add_good_attribute_btn_upload) {
                    ergodicView();
                    String attribute = plist.toString().substring(0, plist.toString().length() - 1);
                    new ShopRequest(AddGoodAttributeActivity.this, handler).uploadGoodAttribute(goodId, type, attribute);
                    Log.e("stringbuffer", attribute);
                }
            }
        });
    }

    private void ergodicView() {
        plist = new StringBuffer();
        for (int i = 0; i < gridView.getChildCount(); i++) {
            View view = gridView.getChildAt(i);
            TextView tv = (TextView) view.findViewById(R.id.add_good_attribute_tv_name);
            if (tv.isSelected() == true) {
                plist.append(tv.getTag() + ",");
            }
        }
    }
}
