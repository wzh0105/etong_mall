package cn.xiaocool.android_etong.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

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
public class SelectPropertyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private GoodsDetailActivity context;
    private DisplayImageOptions displayImageOptions;
    private List<Property.DataBean> dataBeans;
    private List<List<Boolean>> booleans;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private SelectLebalAdapter selectLebalAdapter;

    public SelectPropertyAdapter(GoodsDetailActivity context, List<Property.DataBean> dataBeans,List<List<Boolean>> booleans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataBeans = dataBeans;
        this.booleans = booleans;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    public void  setSelect(int firstPosition , int secondPosition , Boolean judge){
        for (int i=0;i<booleans.get(firstPosition).size();i++){
            booleans.get(firstPosition).set(i,false);
            if (i==secondPosition){
                booleans.get(firstPosition).set(secondPosition,judge);
                Log.e("firstPosition="+firstPosition+" secondPosition="+secondPosition,judge.toString());
            }
        }
        context.setSelect(firstPosition, secondPosition,judge);
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

    private GridView gridView ;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Property.DataBean dataBean = dataBeans.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_upload_goods_property_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvProperty.setText(dataBean.getName());
        gridView = holder.gvProperty;
        selectLebalAdapter = new SelectLebalAdapter(context, dataBean.getPlist(),booleans.get(position),position,this);
        holder.gvProperty.setAdapter(selectLebalAdapter);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_property)
        TextView tvProperty;
        @BindView(R.id.gv_property)
        GridView gvProperty;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
