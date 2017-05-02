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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.StoreHomepageActivity;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/8/8.
 */
public class LocalAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImageOptions;
    private List<Local> locals;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public LocalAdapter(Context context, List<Local> locals) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.locals = locals;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return locals.size();
    }

    @Override
    public Object getItem(int position) {
        return locals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Local local = locals.get(position);
        if (convertView == null) {
            convertView=inflater.inflate(R.layout.local_restaurant_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        String pic = local.getPicture();
        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.imgRestaurant, displayImageOptions);
        holder.txRestaurantName.setText(local.getGoodsname());
        holder.txRestaurantIntroduce.setText(local.getDescription());
        holder.txRestaurantPrice.setText("￥" + local.getPrice());
        holder.txRestaurantMenshijia.setText("门市价￥"+local.getOprice());
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shopid", local.getShopid());
                intent.setClass(context, StoreHomepageActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_restaurant)
        ImageView imgRestaurant;
        @BindView(R.id.tx_restaurant_name)
        TextView txRestaurantName;
        @BindView(R.id.tx_restaurant_introduce)
        TextView txRestaurantIntroduce;
        @BindView(R.id.tx_restaurant_distance)
        TextView txRestaurantDistance;
        @BindView(R.id.tx_restaurant_price)
        TextView txRestaurantPrice;
        @BindView(R.id.tx_restaurant_menshijia)
        TextView txRestaurantMenshijia;
        @BindView(R.id.tx_restaurant_yishou)
        TextView txRestaurantYishou;
        @BindView(R.id.rl_item)
        LinearLayout rl_item;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
