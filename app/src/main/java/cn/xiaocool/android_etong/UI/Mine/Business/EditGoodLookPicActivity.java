package cn.xiaocool.android_etong.UI.Mine.Business;

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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.EditGoodPicAdapter;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by wzh on 2016/7/28.
 */
public class EditGoodLookPicActivity extends Activity implements View.OnClickListener {
    private String picName;
    private RelativeLayout rlBack, rlUpload, rlConfirm;
    private TextView tvTitle;
    private ImageView ivPic0, ivPic1, ivPic2, ivPic3, ivPic4;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List list = new ArrayList();
    private EditGoodPicAdapter editGoodPicAdapter;
    private GridView gridView;
    private ProgressDialog progressDialog;
    private boolean isShowDelete = false;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEY:
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                            Log.e("yes", "success0");
                            //刷新新加图片
                            list.add(storePicName + ".jpg");
                            editGoodPicAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "上传失败！", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.CHANGE_GOOD_PICS:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            ToastUtils.makeShortToast(context, "轮播图修改成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "修改失败！请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private UserInfo user;
    private Context context;
    private String storePicName;
    private String picPath;
    private static final int KEY = 0x0808;
    private String goodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_good_look_pic);
        context = this;
        user = new UserInfo(context);
        user.readData(context);
        initView();
//        initPic();
        Intent intent = getIntent();
        picName = intent.getStringExtra("picName");
        goodId = intent.getStringExtra("goodId");
        String picArray[] = picName.split("[,]");
        for (int i = 0; i < picArray.length; i++) {
            list.add(picArray[i]);
        }
        editGoodPicAdapter = new EditGoodPicAdapter(this, list);
        gridView.setAdapter(editGoodPicAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                }
                editGoodPicAdapter.setIsShowDelete(isShowDelete);
                return true;
            }
        });
////        ImageView[] ivPicArr = {ivPic0,ivPic1,ivPic2,ivPic3,ivPic4};
//        for (int i = 0; i < picArray.length; i++) {
//            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picArray[i], ivPicArr[i], displayImageOptions);
//        }
    }

    private void initPic() {
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    //    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                   int position, long id) {
//        if (isShowDelete) {
//            isShowDelete = false;
//        } else {
//            isShowDelete = true;
//        }
//        editGoodPicAdapter.setIsShowDelete(isShowDelete);
//        Log.e("itemlong","is ok");
//        return true;
//    }
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView_show_pics);
//        ivPic0 = (ImageView) findViewById(R.id.editGood_iv_pic0);
//        ivPic1 = (ImageView) findViewById(R.id.editGood_iv_pic1);
//        ivPic2 = (ImageView) findViewById(R.id.editGood_iv_pic2);
//        ivPic3 = (ImageView) findViewById(R.id.editGood_iv_pic3);
//        ivPic4 = (ImageView) findViewById(R.id.editGood_iv_pic4);
//        ivPic0.setOnClickListener(this);
//        ivPic1.setOnClickListener(this);
//        ivPic2.setOnClickListener(this);
//        ivPic3.setOnClickListener(this);
//        ivPic4.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("修改轮播图");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        rlUpload = (RelativeLayout) findViewById(R.id.edit_rl_upload_pic);
        rlUpload.setOnClickListener(this);
        rlConfirm = (RelativeLayout) findViewById(R.id.edit_rl_confirm);
        rlConfirm.setOnClickListener(this);
        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
    }

    //弹出选择相册 拍照
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

    //回调
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

    //保存裁剪后的数据
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
//            if () {
//                img_lunbo_pic1.setImageDrawable(drawable);
//                lin_lunbo2.setVisibility(View.VISIBLE);
            storePicName = user.getUserId() + String.valueOf(new Date().getTime());
            storeImageToSDCARD(photo, storePicName, filepath);
//            }
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
//            if (judge == 1) {
            picPath = imagefile.getPath();
            Log.e("pic now at", picPath);
//            }
            fos.flush();
            fos.close();

            if (NetUtil.isConnnected(context)) {
                if (!(picPath == null || picPath.equals(""))) {
                    Log.e("aaa", "aabb");
                    new MainRequest(context, handler).uploadavatar(picPath, KEY);
                } else {

                    Toast.makeText(context, "错误！图片地址为空！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.edit_rl_upload_pic:
                if (list.size() < 5) {
                    ShowPickDialog();
                } else {
                    ToastUtils.makeShortToast(context, "最多上传5张轮播图！");
                }
                break;
            case R.id.edit_rl_confirm:
                String str = list.toString();
                String picAdd = str.replace("[", "").replace("]", "").replace(" ", "");//去除首尾[]和空格
                if (NetUtil.isConnnected(context)) {
                    new MainRequest(this, handler).changeGoodPics(goodId, picAdd);//传入goodid和照片列表
                }
        }
    }
}
