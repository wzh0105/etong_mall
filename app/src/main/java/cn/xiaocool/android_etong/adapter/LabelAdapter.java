package cn.xiaocool.android_etong.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

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
 * Created by 潘 on 2016/8/20.
 */
public class LabelAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private UploadGoodsPropertyLabelActivity context;
    private DisplayImageOptions displayImageOptions;
    private List<Property.DataBean.PlistBean> plistBeans;
    private Boolean judge = false;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public LabelAdapter(UploadGoodsPropertyLabelActivity context, List<Property.DataBean.PlistBean> plistBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.plistBeans = plistBeans;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return plistBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return plistBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Property.DataBean.PlistBean plistBean = plistBeans.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.label_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btnLabel.setText(plistBean.getName());
        Log.e("标签名称", plistBean.getName());
        final Button button = holder.btnLabel;
        holder.btnLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judge = !judge;
                button.setSelected(judge);
                context.setSelect(position,judge);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.btn_label)
        Button btnLabel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
