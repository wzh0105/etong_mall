package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.bean.HttpBean;
import cn.xiaocool.android_etong.bean.Mine.WithdrawRecord;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.callback.ListRefreshCallBack;
import cn.xiaocool.android_etong.dao.ApiStores;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.util.ToastUtils;
import cn.xiaocool.android_etong.view.NiceDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.PREFIX;

/**
 * Created by 潘 on 2016/11/20.
 */

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<CityBBSBean.CommentBean> lists;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions displayImageOptions;
    private ListRefreshCallBack refreshCallBack;
    public CommentAdapter(Context context, List<CityBBSBean.CommentBean> lists,ListRefreshCallBack refreshCallBack) {
        super();
        this.context = context;
        this.lists = lists;
        this.inflater = LayoutInflater.from(context);
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        this.refreshCallBack = refreshCallBack;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final CityBBSBean.CommentBean dataBean = lists.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_trend_comment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(dataBean.getName());
        holder.content.setText(dataBean.getContent());
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + dataBean.getAvatar(),
                holder.avatar, displayImageOptions);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否是本人
                if (new UserInfo(context).getUserId().equals(dataBean.getUserid())){
                    setVersionDialog(dataBean.getId());
                }

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_trend_comment_tv_name)
        TextView name;
        @BindView(R.id.item_trend_comment_tv_content)
        TextView content;
        @BindView(R.id.item_trend_comment_iv_avatar)
        ImageView avatar;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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
                Call<HttpBean> call = apiStores.DeleteComment(new UserInfo(context).getUserId(),mid,"1");

                call.enqueue(new Callback<HttpBean>() {
                    @Override
                    public void onResponse(Call<HttpBean> call, Response<HttpBean> response) {
                        if (response.body().getStatus().equals("success")){
                            refreshCallBack.success();
                            ToastUtils.makeShortToast(context,"删除成功！");
                        }else {
                            refreshCallBack.error();
                            ToastUtils.makeShortToast(context,"删除失败！");
                        }
                    }

                    @Override
                    public void onFailure(Call<HttpBean> call, Throwable t) {
                        Log.e("err", t.toString());
                        refreshCallBack.error();
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
}
