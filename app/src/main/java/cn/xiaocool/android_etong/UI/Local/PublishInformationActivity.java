package cn.xiaocool.android_etong.UI.Local;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.EditGoodPicAdapter;
import cn.xiaocool.android_etong.adapter.LocalImgGridAdapter;
import cn.xiaocool.android_etong.bean.PhotoWithPath;
import cn.xiaocool.android_etong.callback.PushImage;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.GalleryFinalUtil;
import cn.xiaocool.android_etong.util.GetImageUtil;
import cn.xiaocool.android_etong.util.NetUtil;
import cn.xiaocool.android_etong.util.NoScrollGridView;
import cn.xiaocool.android_etong.util.PushImageUtil;
import cn.xiaocool.android_etong.util.StringJoint;
import cn.xiaocool.android_etong.util.ToastUtils;

/**
 * Created by hzh on 2016/12/29.
 */

public class PublishInformationActivity extends FragmentActivity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private ArrayList<PhotoInfo> mPhotoList;
    private ArrayList<PhotoWithPath> photoWithPaths;

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private LocalImgGridAdapter localImgGridAdapter;
    private List list = new ArrayList();
    private GalleryFinalUtil galleryFinalUtil;
    private EditGoodPicAdapter editGoodPicAdapter;
    private NoScrollGridView activityPostTrendGvAddpic;
    private GridView gridView;
    private String picname;
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
                            ToastUtils.makeShortToast(context, "图片上传成功！");
                            finish();
                        } else {
                            ToastUtils.makeShortToast(context, "修改失败！请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.PUBLISH_CITY_BBS:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject1.getString("status");
                        if (status.equals("success")) {
                            progressDialog.dismiss();
                            ToastUtils.makeShortToast(context, "发布成功！");
                            finish();
                        } else {
                            progressDialog.dismiss();
                            ToastUtils.makeShortToast(context, "发布失败！请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5231:
                    mPhotoList.remove((int)msg.obj);
                    photoWithPaths.remove((int)msg.obj);
                    localImgGridAdapter = new LocalImgGridAdapter(mPhotoList, context,handler);
                    activityPostTrendGvAddpic.setAdapter(localImgGridAdapter);
                    break;

            }
        }
    };


    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private String storePicName;
    private String picPath;
    private static final int KEY = 0x0808;
    private EditText etTitle;
    private EditText etContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish_information);
        context = this;
        initView();

        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);

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
        galleryFinalUtil = new GalleryFinalUtil(9);
        setGrigView();

    }

    private void initView() {
        mPhotoList = new ArrayList<>();
        photoWithPaths = new ArrayList<>();
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        ImageView ivAddPic = (ImageView) findViewById(R.id.publish_iv_add_pic);
        ivAddPic.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView_show__publish_pics);
        TextView tvConfirm = (TextView) findViewById(R.id.publish_btn_confirm);
        tvConfirm.setOnClickListener(this);
        etTitle = (EditText) findViewById(R.id.publish_et_title);
        etTitle.setOnClickListener(this);
        etContent = (EditText) findViewById(R.id.publish_et_content);
        etTitle.setOnClickListener(this);
        activityPostTrendGvAddpic = (NoScrollGridView) findViewById(R.id.activity_post_trend_gv_addpic);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.publish_iv_add_pic:
                if (list.size() < 5) {
                    ShowPickDialog();
                }
                break;
            case R.id.publish_btn_confirm:
                progressDialog.setMessage("正在加载");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                sendTrend();
                break;
        }
    }


    private void sendTrend() {

        //上传图片成功后发布
        new PushImageUtil().setPushIamge(this, photoWithPaths, new PushImage() {
            @Override
            public void success(boolean state) {
                //获得图片字符串
                ArrayList<String> picArray = new ArrayList<>();
                for (PhotoWithPath photo : photoWithPaths) {
                    picArray.add(photo.getPicname());
                }
                picname = StringJoint.arrayJointchar(picArray, ",");
                Log.e("success","pic");
                String str = list.toString();
                String picAdd = str.replace("[", "").replace("]", "").replace(" ", "");//去除首尾[]和空格
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                if (!(content.length() == 0)) {
                    if (NetUtil.isConnnected(context)) {
                        Log.e("picname=",picname);
                        new MainRequest(PublishInformationActivity.this, handler).publishCityBBS("1", "", "", "", title, content, picname, "", "");
                    } else {
                        ToastUtils.makeShortToast(context, "请检查网络！");
                        progressDialog.dismiss();

                    }
                } else {
                    ToastUtils.makeShortToast(context, "输入不能为空！");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void error() {
                Toast.makeText(context,"图片上传失败",Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * 设置添加图片
     */
    private void setGrigView() {
        localImgGridAdapter = new LocalImgGridAdapter(mPhotoList, context,handler);
        activityPostTrendGvAddpic.setAdapter(localImgGridAdapter);
        activityPostTrendGvAddpic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mPhotoList.size()) {
                    showActionSheet();
                }
            }
        });
    }

    /**
     * 相册拍照弹出框
     */
    private void showActionSheet() {
        ActionSheet.createBuilder(context,getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("打开相册", "拍照")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {

                        switch (index) {
                            case 0:
                                galleryFinalUtil.openAblum(context, mPhotoList, REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                                break;
                            case 1:
                                //获取拍照权限
                                if (galleryFinalUtil.openCamera(context, mPhotoList, REQUEST_CODE_CAMERA, mOnHanlderResultCallback)) {
                                    return;
                                } else {
                                    String[] perms = {"android.permission.CAMERA"};
                                    int permsRequestCode = 200;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(perms, permsRequestCode);
                                    }
                                }
                                break;

                            default:
                                break;
                        }
                    }
                })
                .show();
    }


    /**
     * 选择图片后 返回的图片数据
     */

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                photoWithPaths.clear();
                mPhotoList.clear();
                mPhotoList.addAll(resultList);
                photoWithPaths.addAll(GetImageUtil.getImgWithPaths(resultList));

                localImgGridAdapter = new LocalImgGridAdapter(mPhotoList, context,handler);
                activityPostTrendGvAddpic.setAdapter(localImgGridAdapter);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 授权权限
     *
     * @param permsRequestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {

            case 200:

                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                    galleryFinalUtil.openCamera(context, mPhotoList, REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    Toast.makeText(this,"已拒绝进入相机，如想开启请到设置中开启！",Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }









//此处为旧版本 选择图片截屏 不使用，换成九宫格多张图片选择   相关视图设置成 gone
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
            storePicName = "picture" + String.valueOf(new Date().getTime());
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


}
