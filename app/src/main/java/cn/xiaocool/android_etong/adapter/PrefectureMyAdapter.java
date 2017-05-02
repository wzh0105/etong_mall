package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Local.ImageDetailActivity;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.util.ImgLoadUtil;
import cn.xiaocool.android_etong.util.NoScrollGridView;

/**
 * Created by hzh on 2017/1/6.
 */

public class PrefectureMyAdapter  extends BaseAdapter{

    private LayoutInflater inflater;
    private int i;
    private Context context;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<String> a;

    public PrefectureMyAdapter(Context context, ArrayList<String> a){
        this.context = context;
        this.a = a;
        this.inflater = LayoutInflater.from(context);
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
        this.i = a.size();
    }
    @Override
    public int getCount() {
        return a.size();
    }

    @Override
    public Object getItem(int i) {
        return a.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.use_img_item2, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position+1<=i){
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + a.get(position),
                    viewHolder.imageView, displayImageOptions);        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, ImageDetailActivity.class);
                intent.putStringArrayListExtra("Imgs", a);
                intent.putExtra("position", position);
                intent.putExtra("type", "4");
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
                imageView = (ImageView) view.findViewById(R.id.iv_user_img);
        }

    }
}
