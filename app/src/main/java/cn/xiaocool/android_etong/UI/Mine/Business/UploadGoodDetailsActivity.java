package cn.xiaocool.android_etong.UI.Mine.Business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.cPicAdapter;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by wzh on 2016/10/22.
 */

public class UploadGoodDetailsActivity extends Activity implements View.OnClickListener {
    private cPicAdapter cPicAdapter;
    private UserInfo user;
    private Context context;
    private List<String> lists;
    private String picName, picPath;
    private String picStr = "";
    // 保存的文件的路径
    @SuppressLint("SdCardPath")
    private String filepath = "/sdcard/goodspic";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_CUT = 3;// 相册
    private static final int PHOTO_REQUEST_ALBUM = 2;// 剪裁
    private static final int KEY = 0x777;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEY:
                    Log.e("upload goodspic", "success");
                    String key = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(key);
                        String state1 = json.getString("status");
                        if (state1.equals("success")) {
                            picName = picName + ".jpg";
                            lists.add(picName);
                            Log.e("lists",lists.toString());
                            cPicAdapter = new cPicAdapter(context, lists);
                            list_pic.setAdapter(cPicAdapter);
                            progressDialog.dismiss();
//                            setListViewHeightBasedOnChildren(list_pic);
                        } else {
                            Toast.makeText(context, json.getString("data"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.top_title_text)
    TextView topTitleText;
    @BindView(R.id.upload_good_details_et)
    EditText uploadGoodDetailsEt;
    @BindView(R.id.upload_good_btn)
    RelativeLayout uploadGoodBtn;
    @BindView(R.id.list_pic)
    ListView list_pic;
    @BindView(R.id.rl_upload_cpic)
    RelativeLayout rl_upload_cpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_good_details);
        context = this;
        user = new UserInfo();
        user.readData(context);
        ButterKnife.bind(this);
        topTitleText.setText("输入;宝贝详情");
        Intent intent = getIntent();
        final String content = intent.getStringExtra("tv_content");
        uploadGoodDetailsEt.setText(content);
        uploadGoodDetailsEt.setSelection(content.length());//光标置后
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


    @OnClick({R.id.btn_back, R.id.upload_good_btn, R.id.rl_upload_cpic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent0 = new Intent();
                intent0.putExtra("good_details", "");
                intent0.putExtra("picStr", "");
                intent0.putExtra("result", (Serializable) lists);
                setResult(1000, intent0);
                finish();
                break;
            case R.id.upload_good_btn:
                Intent intent = new Intent();
                intent.putExtra("good_details", uploadGoodDetailsEt.getText().toString());//携带数据返回
                if (lists == null|| TextUtils.isEmpty(uploadGoodDetailsEt.getText().toString())) {
                    intent.putExtra("picStr", "");
                } else {
                    for (int i = 0; i < lists.size(); i++) {
                        picStr = picStr + lists.get(i).toString() + ",";
                    }
                    picStr= picStr.substring(0, picStr.length() - 1);
                    Log.e("picStr=", picStr);
                    intent.putExtra("picStr", picStr);
                }
                intent.putExtra("result", (Serializable) lists);
                setResult(1000, intent);
                finish();
                break;
            case R.id.rl_upload_cpic:
                ShowPickDialog();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent0 = new Intent();
            intent0.putExtra("good_details", "");
            intent0.putExtra("picStr", "");
            intent0.putExtra("result", (Serializable) lists);
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
//                      startPhotoZoom(Uri.fromFile(tempFile));
//                        startPhotoNotCrap(Uri.parse(tempFile.toString()));
                        decodeUriAsBitmap(Uri.fromFile(tempFile),tempFile);

                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_ALBUM:// 图库
//                    startPhotoZoom(data.getData());
                    if (data != null) {
                        startPhotoNotCrap(data.getData());
                    }
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

    /*
    *  从相册选照片不剪切
    */

    public void startPhotoNotCrap(Uri selectedImage){
        try {
//            Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor =getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
            Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
            bitmap = zoomImage(bitmap,600,400);
            Drawable drawable = new BitmapDrawable(this.getResources(), bitmap);
            picName = user.getUserId() + String.valueOf(new Date().getTime());
            storeImageToSDCARD(bitmap, picName, filepath);
        } catch (Exception e) {
            // TODO Auto-generatedcatch block
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 3);
//        intent.putExtra("aspectY", 2);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 100);
//        intent.putExtra("outputY", 100);
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


    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.setLayoutParams(params);

    }

    private Bitmap decodeUriAsBitmap(Uri uri,File file){

        Bitmap bitmap = null;

        try {

            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

            bitmap = zoomImage(bitmap,600,400);

            Drawable drawable = new BitmapDrawable(this.getResources(), bitmap);

            picName = user.getUserId() + String.valueOf(new Date().getTime());

            storeImageToSDCARD(bitmap,picName,filepath);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            return null;

        }

        return bitmap;

    }

    private Bitmap compressBmpFromBmp(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }
}
