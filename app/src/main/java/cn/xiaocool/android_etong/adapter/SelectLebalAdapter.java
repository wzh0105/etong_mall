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
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.Shop.Property;

/**
 * Created by 潘 on 2016/8/22.
 */
public class SelectLebalAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private GoodsDetailActivity context;
    private DisplayImageOptions displayImageOptions;
    private List<Property.DataBean.PlistBean> plistBeans;
    private Boolean judge = false;
    private List<Boolean> booleans;
    private int firstPosition;
    private SelectPropertyAdapter adapter;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public SelectLebalAdapter(GoodsDetailActivity context, List<Property.DataBean.PlistBean> plistBeans , List<Boolean> booleans , int firstPosition,SelectPropertyAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        this.inflater = LayoutInflater.from(context);
        this.plistBeans = plistBeans;
        this.booleans = booleans;
        this.firstPosition = firstPosition;
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
            convertView = inflater.inflate(R.layout.select_label, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btnLabel.setText(plistBean.getName());
        final Button button = holder.btnLabel;
        Log.e("刷新", "Label");
        holder.btnLabel.setSelected(booleans.get(position));
        Log.e("bool",booleans.get(position).toString());
        holder.btnLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button.setSelected(!booleans.get(position));
                Boolean bool = !booleans.get(position);
                Log.e("firstPosition", String.valueOf(firstPosition));
                Log.e("position", String.valueOf(position));
                Log.e("bool", bool.toString());
                adapter.setSelect(firstPosition, position,!booleans.get(position));
//                booleans.set(position,!booleans.get(position));
//                notifyDataSetChanged();
//                refresh(position, !booleans.get(position));
                adapter.notifyDataSetChanged();
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

    public void refresh(int position,Boolean bool){
        for (int i=0;i<booleans.size();i++){
            if (i==position){
                booleans.set(position,bool);
            }
            booleans.set(i,false);
        }
        notifyDataSetChanged();
    }
}
