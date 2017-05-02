package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/8/29.
 */
public class StatementItemAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ShoppingCart_StoreName.DataBean.GoodslistBean> goodslistBeans;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public StatementItemAdapter(Context context, List<ShoppingCart_StoreName.DataBean.GoodslistBean> goodslistBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.goodslistBeans = goodslistBeans;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return goodslistBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return goodslistBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final ShoppingCart_StoreName.DataBean.GoodslistBean product = goodslistBeans.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.statementadapter_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String pic = product.getPicture();
        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.imgGoodsPic, displayImageOptions);
        holder.txGoodsName.setText(product.getGoodsname());
        holder.txGoodsCount.setText("数量："+product.getNumber());
        holder.txGoodsPrice.setText(product.getPrice());
        holder.tvLebal.setText(product.getProname());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_goods_pic)
        ImageView imgGoodsPic;
        @BindView(R.id.tx_goods_name)
        TextView txGoodsName;
        @BindView(R.id.tv_lebal)
        TextView tvLebal;
        @BindView(R.id.tx_goods_count)
        TextView txGoodsCount;
        @BindView(R.id.tx_goods_price)
        TextView txGoodsPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
