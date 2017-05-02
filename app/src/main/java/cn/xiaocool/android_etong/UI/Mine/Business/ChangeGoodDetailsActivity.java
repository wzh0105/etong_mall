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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.cPicAdapter;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.net.constant.request.MineRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/7/20.
 */
public class ChangeGoodDetailsActivity extends Activity implements View.OnClickListener {
    private EditText etGoodInforItem;
    private Button btnSave;
    private String[] arraypic,arryPicStr;
    private RelativeLayout btnBack;
    private cPicAdapter cPicAdapter;
    private UserInfo user;
    private String goodid;
    private Context context;
    private List<String> lists;
    private String picName, picPath;
    private String picStr = "";
    private String picstr;
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY = 0x777;
    private ProgressDialog progressDialog,progressDialog1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CHANGE_GOOD_INTRO_ITEM:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(ChangeGoodDetailsActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangeGoodDetailsActivity.this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                case KEY:
                    Log.e("upload goodspic", "success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            picName = picName + ".jpg";
                            lists.add(picName);
                            cPicAdapter = new cPicAdapter(context, lists);
                            list_pic.setAdapter(cPicAdapter);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.GET_GOODS_INFO:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            JSONObject jsonObject1 = json.getJSONObject("data");
                            picstr = jsonObject1.getString("cpiclist");
                            arryPicStr = picstr.split(",");
                            for (String pic_name : arryPicStr) {
                                lists.add(pic_name);
                            }
                            cPicAdapter = new cPicAdapter(context, lists);
                            list_pic.setAdapter(cPicAdapter);
                            progressDialog1.dismiss();
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CHANGE_GOOD_PIC_DETAILS:
                    JSONObject jsonObject3 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject3.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "图片集修改成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "修改失败！请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private String suffix;
    private String infor;

//    @BindView(R.id.list_pic)
//    ListView list_pic;
//    @BindView(R.id.rl_upload_cpic)
//    RelativeLayout rl_upload_cpic;
    private ListView list_pic;
    private RelativeLayout rl_upload_cpic;
    private RelativeLayout btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_good_details);
        context = this;
        initView();

        user = new UserInfo();
        user.readData(context);

        Intent intent = getIntent();
        infor = intent.getStringExtra("changeInfor");
        suffix = intent.getStringExtra("webAddress");
        goodid = intent.getStringExtra("goodid");
        etGoodInforItem.setText(infor);
        etGoodInforItem.setSelection(etGoodInforItem.getText().length());//光标置于最后

        setDialog();

        progressDialog1 = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        progressDialog1.setMessage("正在加载...");
        progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog1.show();
        if (NetUtil.isConnnected(context)){
            new MainRequest(context, handler).getgoodsinfo(goodid);
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }

    }

    private void setDialog() {
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        lists = new ArrayList<>();
        list_pic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(context).setTitle("删除").setMessage("确认删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lists.remove(position);
                                cPicAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("已取消","");
                    }
                }).show();
                return false;
            }
        });
    }


    private void initView() {
        etGoodInforItem = (EditText) findViewById(R.id.upload_good_details_et);
//        btnSave = (Button) findViewById(R.id.business_btn_save);
//        btnSave.setOnClickListener(this);
        btnBack = (RelativeLayout) findViewById(R.id.rl_back);
        btnBack.setOnClickListener(this);
        list_pic = (ListView) findViewById(R.id.list_pic);
        rl_upload_cpic = (RelativeLayout) findViewById(R.id.rl_upload_cpic);
        rl_upload_cpic.setOnClickListener(this);
        btnConfirm = (RelativeLayout) findViewById(R.id.change_good_details_confirm);
        btnConfirm.setOnClickListener(this);
        ToastUtils.makeShortToast(context,"长按图片可删除");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.business_btn_save:
                if (NetUtil.isConnnected(this)) {
                    String infor = etGoodInforItem.getText().toString();
                    new MineRequest(this, handler).changeGoodIntroItem(suffix, infor);
                } else {
                    Toast.makeText(this, "无网络！请稍后重试！", Toast.LENGTH_SHORT).show();
                }
            case R.id.rl_upload_cpic:
                ShowPickDialog();
                break;
            case R.id.change_good_details_confirm:
                String str = lists.toString();
                String picAdd = str.replace("[", "").replace("]", "").replace(" ", "");//去除首尾[]和空格
                if (NetUtil.isConnnected(context)) {
                    new MainRequest(this, handler).changeGoodDetailsPics(goodid, picAdd);//传入good id和picture list
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent0 = new Intent();
            intent0.putExtra("good_details", "");
            setResult(1000, intent0);
        }
        return super.onKeyDown(keyCode, event);
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
        intent.setDataAndType(uri, "image/*");        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 340);
//        intent.putExtra("outputY", 340);
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
            picName = user.getUserId() + String.valueOf(new Date().getTime());
            storeImageToSDCARD(photo, picName, filepath);
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
            picPath = imagefile.getPath();
            Log.e("path=", picPath);
            fos.flush();
            fos.close();
            if (NetUtil.isConnnected(context)) {
                progressDialog.setMessage("正在加载...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new MainRequest(context, handler).uploadavatar(picPath, KEY);
            } else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
