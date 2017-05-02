package cn.xiaocool.android_etong.UI.Mine.Business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

import static android.R.attr.data;
import static com.baidu.location.h.i.B;

/**
 * Created by 潘 on 2016/7/14.
 */
public class CarouselPicActivity extends Activity implements View.OnClickListener {
    private Context context;
    private TextView tx_upload;
    private UserInfo user;
    private RelativeLayout rl_back;
    private LinearLayout lin_lunbo2, lin_lunbo3, lin_lunbo4, lin_lunbo5;
    private ImageView img_lunbo_pic1, img_lunbo_pic2, img_lunbo_pic3, img_lunbo_pic4, img_lunbo_pic5;
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int CHOOSE_BIG_PICTURE = 0x11111;

    private static final int KEY1 = 0x666;
    private static final int KEY2 = 0x667;
    private static final int KEY3 = 0x668;
    private static final int KEY4 = 0x669;
    private static final int KEY5 = 0x660;
    private String picname1, picname2, picname3, picname4, picname5;
    private String pic_path1, pic_path2, pic_path3, pic_path4, pic_path5;
    private int judge;
    private int state = 0;
    private ProgressDialog progressDialog;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp1.jpg";//temp file
    Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEY1:
                    Log.e("upload goodspic", "success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state = 1;
                            if (!(pic_path2 == null || pic_path2.equals(""))) {
                                progressDialog.setMessage("正在上传第二张照片");
                                new MainRequest(context, handler).uploadavatar(pic_path2, KEY2);
                            } else {
                                Log.e("path2", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                                initFinish();
                                finish();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY2:
                    Log.e("upload goodspic", "success");
                    String key2 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key2);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state = 1;
                            if (!(pic_path3 == null || pic_path3.equals(""))) {
                                progressDialog.setMessage("正在上传第三张照片");
                                new MainRequest(context, handler).uploadavatar(pic_path3, KEY3);
                            } else {
                                Log.e("path3", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                                initFinish();
                                finish();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY3:
                    Log.e("upload goodspic", "success");
                    String key3 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key3);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state = 1;
                            if (!(pic_path4 == null || pic_path4.equals(""))) {
                                progressDialog.setMessage("正在上传第四张照片");
                                new MainRequest(context, handler).uploadavatar(pic_path4, KEY4);
                            } else {
                                Log.e("path4", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                                initFinish();
                                finish();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case KEY4:
                    Log.e("upload goodspic", "success");
                    String key4 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key4);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state = 1;
                            if (!(pic_path5 == null || pic_path5.equals(""))) {
                                progressDialog.setMessage("正在上传第五张照片");
                                new MainRequest(context, handler).uploadavatar(pic_path5, KEY5);
                            } else {
                                Log.e("path4", "not set");
                                progressDialog.dismiss();
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                                initFinish();
                                finish();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case KEY5:
                    String key5 = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key5);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            state = 1;
                            if (!(pic_path5 == null || pic_path5.equals(""))) {
                                Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                initFinish();
                                finish();
                            } else {
                                progressDialog.dismiss();
                                initFinish();
                                finish();
                            }
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
//                    Log.e("upload goodspic", "success");
//                    String key5 = (String) msg.obj;
//                    try {
//                        JSONObject json = new JSONObject(key5);
//                        String state1 = json.getString("status");
//                        if (state1.equals("success")) {
//                            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        } else {
//                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.business_carouselpic);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        intiview();
    }

    private void intiview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        lin_lunbo2 = (LinearLayout) findViewById(R.id.lin_lunbo2);
        lin_lunbo2.setVisibility(View.GONE);
        lin_lunbo3 = (LinearLayout) findViewById(R.id.lin_lunbo3);
        lin_lunbo3.setVisibility(View.GONE);
        lin_lunbo4 = (LinearLayout) findViewById(R.id.lin_lunbo4);
        lin_lunbo4.setVisibility(View.GONE);
        lin_lunbo5 = (LinearLayout) findViewById(R.id.lin_lunbo5);
        lin_lunbo5.setVisibility(View.GONE);

        img_lunbo_pic1 = (ImageView) findViewById(R.id.img_lunbo_pic1);
        img_lunbo_pic1.setOnClickListener(this);
        img_lunbo_pic2 = (ImageView) findViewById(R.id.img_lunbo_pic2);
        img_lunbo_pic2.setOnClickListener(this);
        img_lunbo_pic3 = (ImageView) findViewById(R.id.img_lunbo_pic3);
        img_lunbo_pic3.setOnClickListener(this);
        img_lunbo_pic4 = (ImageView) findViewById(R.id.img_lunbo_pic4);
        img_lunbo_pic4.setOnClickListener(this);
        img_lunbo_pic5 = (ImageView) findViewById(R.id.img_lunbo_pic5);
        img_lunbo_pic5.setOnClickListener(this);

        tx_upload = (TextView) findViewById(R.id.tx_upload);
        tx_upload.setOnClickListener(this);
        initPic();
    }

    private void initPic() {
        pic_path1 = "";
        pic_path2 = "";
        pic_path3 = "";
        pic_path4 = "";
        pic_path5 = "";
        picname1 = "";
        picname2 = "";
        picname3 = "";
        picname4 = "";
        picname5 = "";
        Intent intent = getIntent();
        Log.e("pic_name1","success");
        pic_path1 = intent.getStringExtra("pic_path1");
        pic_path2 = intent.getStringExtra("pic_path2");
        pic_path3 = intent.getStringExtra("pic_path3");
        pic_path4 = intent.getStringExtra("pic_path4");
        pic_path5 = intent.getStringExtra("pic_path5");
        picname1 = intent.getStringExtra("picname1");
        picname2 = intent.getStringExtra("picname2");
        picname3 = intent.getStringExtra("picname3");
        picname4 = intent.getStringExtra("picname4");
        picname5 = intent.getStringExtra("picname5");
        Log.e("pic_name1","success");
        if (pic_path1==null||pic_path1.equals("")){
            Log.e("success11","success11");
        }else {
        }
        if (!TextUtils.isEmpty(pic_path1)){
            Log.e("pic_name1","success");
            lin_lunbo2.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname1+".jpg",img_lunbo_pic1);
        } if (!TextUtils.isEmpty(pic_path2)){
            lin_lunbo3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname2+".jpg",img_lunbo_pic2);
        } if (!TextUtils.isEmpty(pic_path3)){
            lin_lunbo4.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname3+".jpg",img_lunbo_pic3);
        } if (!TextUtils.isEmpty(pic_path4)){
            lin_lunbo5.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname4+".jpg",img_lunbo_pic4);
        } if (!TextUtils.isEmpty(pic_path5)){
            ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + picname5+".jpg",img_lunbo_pic5);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                Intent intent_data = new Intent();
                intent_data.putExtra("1111", "111");
                if (pic_path1 == null || pic_path1.equals("")) {
                    pic_path1 = "";
                }
                if (pic_path2 == null || pic_path2.equals("")) {
                    pic_path2 = "";
                }
                if (pic_path3 == null || pic_path3.equals("")) {
                    pic_path3 = "";
                }
                if (pic_path4 == null || pic_path4.equals("")) {
                    pic_path4 = "";
                }
                if (pic_path5 == null || pic_path5.equals("")) {
                    pic_path5 = "";
                }
                if (picname1 == null || picname1.equals("")) {
                    picname1 = "";
                }
                if (picname2 == null || picname2.equals("")) {
                    picname2 = "";
                }
                if (picname3 == null || picname3.equals("")) {
                    picname3 = "";
                }
                if (picname4 == null || picname4.equals("")) {
                    picname4 = "";
                }
                if (picname5 == null || picname5.equals("")) {
                    picname5 = "";
                }
                intent_data.putExtra("state", state);
                intent_data.putExtra("pic_path1", pic_path1);
                intent_data.putExtra("pic_path2", pic_path2);
                intent_data.putExtra("pic_path3", pic_path3);
                intent_data.putExtra("pic_path4", pic_path4);
                intent_data.putExtra("pic_path5", pic_path5);

                intent_data.putExtra("picname1", picname1);
                intent_data.putExtra("picname2", picname2);
                intent_data.putExtra("picname3", picname3);
                intent_data.putExtra("picname4", picname4);
                intent_data.putExtra("picname5", picname5);

                setResult(RESULT_OK, intent_data);
                finish();
                break;
            case R.id.img_lunbo_pic1:
                judge = 1;
                ShowPickDialog();
                break;
            case R.id.img_lunbo_pic2:
                judge = 2;
                ShowPickDialog();
                break;
            case R.id.img_lunbo_pic3:
                judge = 3;
                ShowPickDialog();
                break;
            case R.id.img_lunbo_pic4:
                judge = 4;
                ShowPickDialog();
                break;
            case R.id.img_lunbo_pic5:
                judge = 5;
                ShowPickDialog();
                break;
            case R.id.tx_upload:
                upload();
                break;
        }
    }

    private void upload() {
        if (pic_path1 == null || pic_path1.equals("")) {
            Toast.makeText(context, "请至少设置一张照片", Toast.LENGTH_SHORT).show();
        } else if (NetUtil.isConnnected(context)) {
            progressDialog.setMessage("正在上传第一张轮播图片");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            new MainRequest(context, handler).uploadavatar(pic_path1, KEY1);
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
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
                        imageUri =Uri.fromFile(tempFile);
                        cropImageUri(imageUri, 600, 600, CHOOSE_BIG_PICTURE);
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_ALBUM:// 图库
                    initBigPhoto(data.getData());
//                    startPhotoZoom(data.getData());
                    break;
                case PHOTO_REQUEST_CUT: // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
                case CHOOSE_BIG_PICTURE:

                    Log.e("TAG", "CHOOSE_BIG_PICTURE: data = " + data);//it seems to be null

                    if(imageUri != null){

                        Bitmap bitmap = decodeUriAsBitmap(imageUri) ;//decode bitmap
                        getImageToView1(bitmap);

                    }

//                    Log.e("next","break");
//                    if (data != null) {
//                        getImageToView1();
//                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

        private void initBigPhoto(Uri uri) {

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(uri, "image/*");

//        intent.setType("image/*");

            intent.putExtra("crop", "true");

            intent.putExtra("aspectX", 1);

            intent.putExtra("aspectY", 1);

            intent.putExtra("outputX", 600);

            intent.putExtra("outputY", 600);

            intent.putExtra("scale", true);

            intent.putExtra("return-data", false);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            intent.putExtra("noFaceDetection", true); // no face detection

            startActivityForResult(intent, CHOOSE_BIG_PICTURE);
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
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
    */
    private void getImageToView1(Bitmap bitmap) {

            if (judge == 1) {
                img_lunbo_pic1.setImageBitmap(bitmap);
                lin_lunbo2.setVisibility(View.VISIBLE);
                picname1 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(bitmap, picname1, filepath);
            } else if (judge == 2) {
                img_lunbo_pic2.setImageBitmap(bitmap);
                lin_lunbo3.setVisibility(View.VISIBLE);
                picname2 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(bitmap, picname2, filepath);
            } else if (judge == 3) {
                img_lunbo_pic3.setImageBitmap(bitmap);
                lin_lunbo4.setVisibility(View.VISIBLE);
                picname3 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(bitmap, picname3, filepath);
            } else if (judge == 4) {
                img_lunbo_pic4.setImageBitmap(bitmap);
                lin_lunbo5.setVisibility(View.VISIBLE);
                picname4 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(bitmap, picname4, filepath);
            } else if (judge == 5) {
                img_lunbo_pic5.setImageBitmap(bitmap);
                picname5 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(bitmap, picname5, filepath);
            }
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
            if (judge == 1) {
                img_lunbo_pic1.setImageDrawable(drawable);
                lin_lunbo2.setVisibility(View.VISIBLE);
                picname1 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, picname1, filepath);
            } else if (judge == 2) {
                img_lunbo_pic2.setImageDrawable(drawable);
                lin_lunbo3.setVisibility(View.VISIBLE);
                picname2 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, picname2, filepath);
            } else if (judge == 3) {
                img_lunbo_pic3.setImageDrawable(drawable);
                lin_lunbo4.setVisibility(View.VISIBLE);
                picname3 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, picname3, filepath);
            } else if (judge == 4) {
                img_lunbo_pic4.setImageDrawable(drawable);
                lin_lunbo5.setVisibility(View.VISIBLE);
                picname4 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, picname4, filepath);
            } else if (judge == 5) {
                img_lunbo_pic5.setImageDrawable(drawable);
                picname5 = user.getUserId() + String.valueOf(new Date().getTime());
                storeImageToSDCARD(photo, picname5, filepath);
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
            if (judge == 1) {
                pic_path1 = imagefile.getPath();
                Log.e("path=", pic_path1);
            } else if (judge == 2) {
                pic_path2 = imagefile.getPath();
                Log.e("path=", pic_path2);
            } else if (judge == 3) {
                pic_path3 = imagefile.getPath();
                Log.e("path=", pic_path3);
            } else if (judge == 4) {
                pic_path4 = imagefile.getPath();
                Log.e("path=", pic_path4);
            } else if (judge == 5) {
                pic_path5 = imagefile.getPath();
                Log.e("path=", pic_path5);
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            initFinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initFinish(){
        Intent intent_data = new Intent();
        intent_data.putExtra("1111", "111");
        if (pic_path1 == null || pic_path1.equals("")) {
            pic_path1 = "";
        }
        if (pic_path2 == null || pic_path2.equals("")) {
            pic_path2 = "";
        }
        if (pic_path3 == null || pic_path3.equals("")) {
            pic_path3 = "";
        }
        if (pic_path4 == null || pic_path4.equals("")) {
            pic_path4 = "";
        }
        if (pic_path5 == null || pic_path5.equals("")) {
            pic_path5 = "";
        }
        if (picname1 == null || picname1.equals("")) {
            picname1 = "";
        }
        if (picname2 == null || picname2.equals("")) {
            picname2 = "";
        }
        if (picname3 == null || picname3.equals("")) {
            picname3 = "";
        }
        if (picname4 == null || picname4.equals("")) {
            picname4 = "";
        }
        if (picname5 == null || picname5.equals("")) {
            picname5 = "";
        }
        intent_data.putExtra("pic_path1", pic_path1);
        intent_data.putExtra("pic_path2", pic_path2);
        intent_data.putExtra("pic_path3", pic_path3);
        intent_data.putExtra("pic_path4", pic_path4);
        intent_data.putExtra("pic_path5", pic_path5);
        intent_data.putExtra("picname1", picname1);
        intent_data.putExtra("picname2", picname2);
        intent_data.putExtra("picname3", picname3);
        intent_data.putExtra("picname4", picname4);
        intent_data.putExtra("picname5", picname5);

        setResult(RESULT_OK, intent_data);
    }

    private Bitmap decodeUriAsBitmap(Uri uri){

        Bitmap bitmap = null;

        try {

            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            return null;

        }

        return bitmap;

    }

    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", outputX);

        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, requestCode);
    }

}
