package cn.xiaocool.android_etong.UI.Mine.Business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.KeyBoardUtils;
import cn.xiaocool.android_etong.util.NetBaseUtils;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/17.
 */
public class EditStoreActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private ImageView img_store_head;
    private UserInfo user;
    private String head;
    private String shopid,shopname;
    private EditText tx_store_name,tx_store_addrss;
    private Button btn_next;
    private ProgressDialog progressDialog;

    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/myheader";
    private String picname = "newpic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY = 0x232545;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case KEY:
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1=json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "商店头像上传成功");
                            new MainRequest(context,handler).UpdateShopPhoto(shopid, picname + ".jpg");
                        }else{
                            Toast.makeText(context, json.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.UPDATE_SHOP_PHOTO:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String state1=json.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "商店头像上传地址成功");
                            Toast.makeText(context, "店铺头像上传成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, json.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GETMYSHOP:
                    Log.e("getmyshop","getmyshop");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if(NetUtil.isConnnected(context)){
                        try {
                            String status = jsonObject.getString("status");
                            String data = jsonObject.getString("data");
                            if (status.equals("success")){
                                progressDialog.dismiss();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String shopid = jsonObject1.getString("id");
                                String head = jsonObject1.getString("photo");
                                String address = jsonObject1.getString("address");
                                String shopname = jsonObject1.getString("shopname");
                                Log.e("head=",head);
                                ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + jsonObject1.getString("photo"), img_store_head);
                                if (shopname.equals("null")||shopname==null||shopname.equals("")){
                                    tx_store_name.setText("未设置");
                                }else {
                                    tx_store_name.setText(shopname);
                                }
                                if (address.equals("null")||address==null||address.equals("")){
                                    tx_store_addrss.setText("未设置");
                                }else {
                                    tx_store_addrss.setText(address);
                                }
                                // 切换后将EditText光标置于末尾
                                CharSequence charSequence =tx_store_name.getText();
                                if (charSequence instanceof Spannable) {
                                    Spannable spanText = (Spannable) charSequence;
                                    Selection.setSelection(spanText, charSequence.length());
                                }
                                KeyBoardUtils.showKeyBoardByTime(tx_store_name, 300);
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(context,jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CommunalInterfaces.UPDATA_SHOP_ADDRESS:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String state1=jsonObject1.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "商店地址上传成功");
                            new MainRequest(context,handler).updateshopname(shopid,shopname);
                        }else{
                            Toast.makeText(context, jsonObject1.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.UPDATASHOPNAME:
                    JSONObject jsonObject2 = (JSONObject) msg.obj;
                    try {
                        String state1=jsonObject2.getString("status");
                        if (state1.equals("success")) {
                            Log.e("success", "商店名称上传成功");
                            Intent dateIntent = new Intent();
                            setResult(1, dateIntent);
                            finish();
                            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, jsonObject2.getString("data"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_store);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        progressDialog = new ProgressDialog(context,AlertDialog.THEME_HOLO_LIGHT);
        Intent intent = getIntent();
        shopid=intent.getStringExtra("shopid");
        initview();
        if(NetUtil.isConnnected(context)){
            progressDialog.setMessage("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context,handler).getmyshop();
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }


    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        img_store_head = (ImageView) findViewById(R.id.img_store_head);
        img_store_head.setOnClickListener(this);
        tx_store_name = (EditText) findViewById(R.id.tx_store_name);
        tx_store_addrss = (EditText) findViewById(R.id.tx_store_address);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            Intent dateIntent = new Intent();
            setResult(1, dateIntent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                Intent dateIntent = new Intent();
                setResult(1, dateIntent);
                finish();
                break;
            case R.id.img_store_head:
                ShowPickDialog();
                break;
            case R.id.btn_next:
                shopname = tx_store_name.getText().toString();
                shopname = shopname.trim();
                String shopaddress = tx_store_addrss.getText().toString();
                shopaddress = shopaddress.trim();
                if (!TextUtils.isEmpty(shopaddress)){
                    if (!TextUtils.isEmpty(shopname)){
                        if(NetUtil.isConnnected(context)){
                            new MainRequest(context,handler).UpdateShopAddress(shopid, shopaddress);
                        }else {
                            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context,"请输入店铺名",Toast.LENGTH_SHORT).show();
                        tx_store_name.findFocus();
                    }
                }else {
                    Toast.makeText(context,"请输入店铺地址",Toast.LENGTH_SHORT).show();
                    tx_store_addrss.findFocus();
                }

                break;
        }
    }
    protected void ShowPickDialog() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setNegativeButton("相册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFromGallery, PHOTO_REQUEST_ALBUM);

            }
        }).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(path, "newpic.jpg");
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                startActivityForResult(intentFromCapture, PHOTO_REQUEST_CAMERA);
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO_REQUEST_CAMERA:// 相册
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, "newpic.jpg");
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_ALBUM:// 图库
                    startPhotoZoom(data.getData());
                    break;

                case PHOTO_REQUEST_CUT: // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            img_store_head.setImageDrawable(drawable);
            picname = "avatar"+user.getUserId()+String.valueOf(new Date().getTime());
            storeImageToSDCARD(photo, picname, filepath);
            if(NetBaseUtils.isConnnected(context)){
                new MainRequest(context,handler).uploadavatar(head, KEY);
            }else {
                Toast.makeText(context,"网络问题，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * storeImageToSDCARD 将bitmap存放到sdcard中
     */
    public void storeImageToSDCARD(Bitmap colorImage, String ImageName, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File imagefile = new File(file, ImageName + ".jpg");
        try {
            imagefile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imagefile);
            colorImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            head = imagefile.getAbsolutePath();
            Log.e("path=", head);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
