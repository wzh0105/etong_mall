package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/8/10.
 */
public class TravelAroundAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImageOptions;
    private List<Local> locals;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public TravelAroundAdapter(Context context,List<Local> locals){
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
            convertView = inflater.inflate(R.layout.activity_travel_around_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txRestaurantName.setText(local.getShopname());
        holder.txRestaurantPrice.setText("￥"+local.getPrice());
        String pic = local.getPicture();
        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.imgRestaurant, displayImageOptions);
        Log.e("levle", local.getLevel());
        if (local.getLevel().equals("0")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_0);
        } else if (local.getLevel().equals("0.5")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_0_5);
        } else if (local.getLevel().equals("1")) {
            Log.e("okok", "okok");
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_1);
        } else if (local.getLevel().equals("1.5")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_1_5);
        } else if (local.getLevel().equals("2")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_2);
        } else if (local.getLevel().equals("2.5")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_2_5);
        } else if (local.getLevel().equals("3")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_3);
        } else if (local.getLevel().equals("3.5")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_3_5);
        } else if (local.getLevel().equals("4")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_4);
        } else if (local.getLevel().equals("4.5")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_4_5);
        } else if (local.getLevel().equals("5")) {
            holder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_5);
        }
        holder.tvGoodsPingfen.setText(local.getLevel()+"分");
        holder.rl_travel_around.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("pic",local.getPicture());
                intent.putExtra("shopid",local.getShopid());
                intent.putExtra("id",local.getId());
                intent.putExtra("price",local.getPrice());
                intent.putExtra("goodsname",local.getGoodsname());
                intent.putExtra("shopname",local.getShopname());
                intent.setClass(context, GoodsDetailActivity.class);
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
        @BindView(R.id.img_shop_pingxing)
        ImageView imgShopPingxing;
        @BindView(R.id.tv_goods_pingfen)
        TextView tvGoodsPingfen;
        @BindView(R.id.tx_restaurant_price)
        TextView txRestaurantPrice;
        @BindView(R.id.rl_travel_around)
        RelativeLayout rl_travel_around;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
