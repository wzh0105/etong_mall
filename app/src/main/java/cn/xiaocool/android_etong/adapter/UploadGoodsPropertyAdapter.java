package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.UploadGoodsPropertyLabelActivity;
import cn.xiaocool.android_etong.bean.Shop.Property;

/**
 * Created by 潘 on 2016/8/19.
 */
public class UploadGoodsPropertyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImageOptions;
    private List<Property.DataBean> dataBeans;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private String goodsid;
    private String type;

    public UploadGoodsPropertyAdapter(Context context, List<Property.DataBean> dataBeans,String goodsid,String type) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataBeans = dataBeans;
        this.goodsid = goodsid;
        this.type = type;
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
        final Property.DataBean dataBean = dataBeans.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_upload_goods_property_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvProperty.setText(dataBean.getName());
        holder.tvProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                bundle.putSerializable("object", dataBean);
                intent.putExtra("111", "object");
                intent.putExtras(bundle);
                intent.putExtra("goodsid",goodsid);
                intent.putExtra("type",type);
                intent.setClass(context, UploadGoodsPropertyLabelActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_property)
        TextView tvProperty;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
