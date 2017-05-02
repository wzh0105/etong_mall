package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.bean.HttpBean;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.callback.ListRefreshCallBack;
import cn.xiaocool.android_etong.dao.ApiStores;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.util.KeyBoardUtils;
import cn.xiaocool.android_etong.util.NoScrollGridView;
import cn.xiaocool.android_etong.util.NoScrollListView;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.CommentPopupWindow;
import cn.xiaocool.android_etong.view.NiceDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;


/**
 * Created by wzh on 2016/12/31.
 */
public class GetBBSListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<CityBBSBean.DataBean> dataBeenList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private PrefectureMyAdapter prefectureMyAdapter ;
    private static long lastClickTime;
    private UserInfo user;
    private ListRefreshCallBack resfrsh;
    private CommentPopupWindow commentPopupWindow;
    private  View location;
    public GetBBSListAdapter(Context context, List<CityBBSBean.DataBean> dataBeenList,View location,ListRefreshCallBack resfrsh) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeenList = dataBeenList;
        this.context = context;
        this.location = location;
        this.resfrsh = resfrsh;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        user = new UserInfo(context);
    }

    @Override
    public int getCount() {
        return dataBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final CityBBSBean.DataBean dataBean = dataBeenList.get(position);
        String picName = dataBean.getPic();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.prefecturemy_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + dataBean.getPhoto(),
                viewHolder.ivHead, displayImageOptions);
        ArrayList<String> list=new ArrayList<String>();
        for(int i=0;i<arrayPic.length;i++){
            list.add(arrayPic[i]);
        }
        prefectureMyAdapter = new PrefectureMyAdapter(context,list);
        viewHolder.activity_post_trend_gv_addpic.setAdapter(prefectureMyAdapter);
//        switch (arrayPic.length) {
//            case 0:
//                viewHolder.picLL0.setVisibility(View.GONE);
//
//                viewHolder.ivPic0.setVisibility(View.GONE);
//                viewHolder.ivPic1.setVisibility(View.GONE);
//                viewHolder.ivPic2.setVisibility(View.GONE);
//                break;
//            case 1:
//                viewHolder.ivPic0.setVisibility(View.VISIBLE);
//                viewHolder.ivPic1.setVisibility(View.GONE);
//                viewHolder.ivPic2.setVisibility(View.GONE);
//                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
//                        viewHolder.ivPic0, displayImageOptions);
//                break;
//            case 2:
//                viewHolder.ivPic0.setVisibility(View.VISIBLE);
//                viewHolder.ivPic1.setVisibility(View.VISIBLE);
//                viewHolder.ivPic2.setVisibility(View.GONE);
//                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
//                        viewHolder.ivPic0, displayImageOptions);
//                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[1],
//                        viewHolder.ivPic1, displayImageOptions);
//                break;
//            case 3:
//                viewHolder.ivPic0.setVisibility(View.VISIBLE);
//                viewHolder.ivPic1.setVisibility(View.VISIBLE);
//                viewHolder.ivPic2.setVisibility(View.VISIBLE);
//                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
//                        viewHolder.ivPic0, displayImageOptions);
//                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[1],
//                        viewHolder.ivPic1, displayImageOptions);
//                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[2],
//                        viewHolder.ivPic2, displayImageOptions);
//                break;
//
//        }


        viewHolder.tvName.setText(dataBean.getName());
        viewHolder.tvTitle.setText(dataBean.getTitle());
        viewHolder.tvContent.setText(dataBean.getContent());
//        viewHolder.ivHead.setImageURI();
//
        viewHolder.trend_item_tv_praise.setText(""+dataBean.getLike().size());
        viewHolder.trend_item_tv_comment.setText(""+dataBean.getComment().size());
        viewHolder.trend_item_lv_comment.setAdapter(new CommentAdapter(context, dataBean.getComment(), new ListRefreshCallBack() {
            @Override
            public void success() {
                resfrsh.success();
            }

            @Override
            public void error() {
                resfrsh.error();
            }
        }));
        //评论按钮
        viewHolder.trend_item_ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentPopupWindow = new CommentPopupWindow(context, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_comment:
                                if (commentPopupWindow.ed_comment.getText().length() > 0) {
                                    sendRemark(dataBean.getMid(), commentPopupWindow.ed_comment.getText().toString());
                                    commentPopupWindow.dismiss();
                                    commentPopupWindow.ed_comment.setText("");
                                } else {

                                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });
                final EditText editText = commentPopupWindow.ed_comment;
                commentPopupWindow.showAtLocation(location, Gravity.BOTTOM, 0, 0);
                //弹出系统输入法
                KeyBoardUtils.showKeyBoardByTime(editText,1);
            }
        });
        //点赞
        viewHolder.trend_item_ll_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFastClick()) {
                    return;
                } else {
                    if (isPraise(dataBean.getLike())) {
                        praiseHttpCallBack(false,dataBean.getMid());
                    } else {
                        praiseHttpCallBack(true,dataBean.getMid());
                    }
                }
            }
        });
        //判断本人是否已经点赞
        if (isPraise(dataBean.getLike())) {
            //点赞成功后图片变红
            viewHolder.trend_item_ll_praise.setSelected(true);
        } else {
            //取消点赞后
            viewHolder.trend_item_ll_praise.setSelected(false);
        }


        //判断是否本人
        if(user.getUserId().equals(dataBean.getUserid())){
            viewHolder.delete_layout.setVisibility(View.VISIBLE);
            viewHolder.delete_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setVersionDialog(dataBean.getMid());
                }
            });
        }else{
            viewHolder.delete_layout.setVisibility(View.GONE);

        }

        return convertView;
    }

    private void setVersionDialog(final String mid) {
        final NiceDialog mDialog = new NiceDialog(context);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = width-300;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mDialog.getWindow().setAttributes(layoutParams);
        mDialog.setTitle("提示");
        mDialog.setContent("确认删除吗?");
        mDialog.setOKButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(PREFIX)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiStores apiStores = retrofit.create(ApiStores.class);
                Call<HttpBean> call = apiStores.DeleteBbspos(user.getUserId(),mid);

                call.enqueue(new Callback<HttpBean>() {
                    @Override
                    public void onResponse(Call<HttpBean> call, Response<HttpBean> response) {
                        if (response.body().getStatus().equals("success")){
                            resfrsh.success();
                            ToastUtils.makeShortToast(context,"删除成功！");
                        }else {
                            resfrsh.error();
                            ToastUtils.makeShortToast(context,"删除失败！");
                        }
                    }

                    @Override
                    public void onFailure(Call<HttpBean> call, Throwable t) {
                        Log.e("err", t.toString());
                        resfrsh.error();
                        ToastUtils.makeShortToast(context,"删除失败了！");
                    }
                });
                mDialog.dismiss();
            }
        });
        mDialog.setCancelButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();

}

    /**
     * 评论
     * @param mid
     * @param content
     */
    private void sendRemark(String mid,String content) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Call<HttpBean> call = apiStores.SetComment(user.getUserId(),mid,content,"1");

        call.enqueue(new Callback<HttpBean>() {
            @Override
            public void onResponse(Call<HttpBean> call, Response<HttpBean> response) {
                Log.e("cc", "dd");
                if (response.body().getStatus().equals("success")){
                    resfrsh.success();
                }else {
                    resfrsh.error();
                }
            }

            @Override
            public void onFailure(Call<HttpBean> call, Throwable t) {
                Log.e("err", t.toString());
                resfrsh.error();
            }


        });
    }

    /**
     * 点赞和取消点赞
     * @param b
     * @param mid
     */
    private void praiseHttpCallBack(boolean b,String mid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREFIX)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        Log.e("mid",mid);
        Call<HttpBean> call = b==true ? apiStores.SetLike(user.getUserId(),mid,"1") : apiStores.ResetLike(user.getUserId(),mid,"1");

        call.enqueue(new Callback<HttpBean>() {
            @Override
            public void onResponse(Call<HttpBean> call, Response<HttpBean> response) {
                Log.e("cc===", response.body().getStatus());
                if (response.body().getStatus().equals("success")){
                    resfrsh.success();
                    Log.e("cc", "success");
                }else {
                    resfrsh.error();
                    Log.e("cc", "error");
                }
            }

            @Override
            public void onFailure(Call<HttpBean> call, Throwable t) {
                Log.e("err", t.toString());
                resfrsh.error();
            }


        });
    }


    /**
     * 防止快速点赞
     *
     * @return
     */
    public boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 判断当前用户是否点赞
     */
    private boolean isPraise(List<CityBBSBean.LikeBean> praises) {
        for (int i = 0; i < praises.size(); i++) {
            if (praises.get(i).getUserid().equals(user.getUserId())) {
                return true;
            }
        }
        return false;
    }

    private class ViewHolder {
        TextView tvName, tvTitle, tvContent,trend_item_tv_praise,trend_item_tv_comment,item_sn_delet;
        ImageView ivHead, ivPic0, ivPic1, ivPic2,trend_item_iv_delete;
        LinearLayout picLL0,trend_item_ll_praise,trend_item_ll_comment;
        NoScrollGridView activity_post_trend_gv_addpic;
        NoScrollListView trend_item_lv_comment;
        RelativeLayout delete_layout;

        public ViewHolder(View view) {
            ivHead = (ImageView) view.findViewById(R.id.iv_prefecture_head);
            tvName = (TextView) view.findViewById(R.id.tv_prefecture_name);
            tvTitle = (TextView) view.findViewById(R.id.tv_prefecture_title);
            tvContent = (TextView) view.findViewById(R.id.tv_prefecture_content);
            ivPic0 = (ImageView) view.findViewById(R.id.tx_prefecture_pic0);
            ivPic1 = (ImageView) view.findViewById(R.id.tx_prefecture_pic1);
            ivPic2 = (ImageView) view.findViewById(R.id.tx_prefecture_pic2);
            picLL0 = (LinearLayout) view.findViewById(R.id.prefecturemy_pic_ll);
            activity_post_trend_gv_addpic = (NoScrollGridView) view.findViewById(R.id.activity_post_trend_gv_addpic);
            trend_item_lv_comment = (NoScrollListView) view.findViewById(R.id.trend_item_lv_comment);
            trend_item_tv_praise = (TextView) view.findViewById(R.id.trend_item_tv_praise);
            trend_item_tv_comment = (TextView) view.findViewById(R.id.trend_item_tv_comment);
            trend_item_ll_comment = (LinearLayout) view.findViewById(R.id.trend_item_ll_comment);
            trend_item_ll_praise = (LinearLayout) view.findViewById(R.id.trend_item_ll_praise);
            item_sn_delet = (TextView) view.findViewById(R.id.item_sn_delet);
            delete_layout = (RelativeLayout) view.findViewById(R.id.delete_layout);
            trend_item_iv_delete = (ImageView) view.findViewById(R.id.trend_item_iv_delete);
        }

    }
}
