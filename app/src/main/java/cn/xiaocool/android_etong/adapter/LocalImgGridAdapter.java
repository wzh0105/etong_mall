package cn.xiaocool.android_etong.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.util.ImgLoadUtil;


/**
 * Created by Administrator on 2016/5/27.
 */
public class LocalImgGridAdapter extends BaseAdapter{

    private int i;
    private LayoutInflater mLayoutInflater;
    private ArrayList<PhotoInfo> mWorkImgs;
    private Context mContext;
    private Handler handler;
    public LocalImgGridAdapter(ArrayList<PhotoInfo> workImgs, Context context, Handler handler) {
        this.mContext = context;
        this.mWorkImgs = workImgs;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.i = mWorkImgs.size();
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return i > 8 ? 9 : i+1;
    }

    @Override
    public Object getItem(int position) {
        return i+1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.user_img_item, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_user_img);
            viewHolder.delete_img = (ImageView)convertView.findViewById(R.id.delete_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        if (position+1<=i){
            ImgLoadUtil.display("file:/" + mWorkImgs.get(position).getPhotoPath(), viewHolder.imageView);
            viewHolder.delete_img.setVisibility(View.VISIBLE);
        }
        viewHolder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 5231;
                message.obj = position;
                handler.sendMessage(message);
            }
        });
        return convertView;
    }

    private static class MyGridViewHolder {
        ImageView imageView,delete_img;
    }
}
