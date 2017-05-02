package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Shop.Detail;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 潘 on 2016/8/17.
 */
public class DetailAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImageOptions;
    private List<Detail.DataBean> dataBeans;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public DetailAdapter(Context context, List<Detail.DataBean> dataBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataBeans = dataBeans;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Detail.DataBean dataBean = dataBeans.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.business_goods_evaluate, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(WebAddress.GETAVATAR + dataBean.getPhoto(), holder.imgPrefectureHead, displayImageOptions);
        holder.txPrefectureName.setText(dataBean.getName());
        holder.tvContent.setText(dataBean.getContent());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = Long.valueOf(dataBean.getAdd_time());
        String d = format.format((new Date(time * 1000)));
        holder.tvTime.setText(d);
        try {
            Date data = format.parse(d);
            Log.e("time", String.valueOf(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.img_prefecture_head)
        CircleImageView imgPrefectureHead;
        @BindView(R.id.tx_prefecture_name)
        TextView txPrefectureName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
