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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.StoreHomepageActivity;
import cn.xiaocool.android_etong.bean.json.Ranking;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.NoScrollGridView;
import cn.xiaocool.android_etong.util.NoScrollListView;

/**
 * Created by hzh on 2017/1/17.
 */

public class RankingAdapter extends BaseAdapter {
    private Context context;
    private List<Ranking.DataBean> list;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public RankingAdapter(Context context, List<Ranking.DataBean> list){
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        final Ranking.DataBean dataBean  = list.get(i);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_hotel_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txRestaurantName.setText(dataBean.getShopname());
        imageLoader.displayImage(WebAddress.GETAVATAR + dataBean.getPhoto(), viewHolder.imgRestaurant, displayImageOptions);
        Log.e("levle", dataBean.getLevel());
        if (dataBean.getLevel().equals("0")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_0);
        } else if (dataBean.getLevel().equals("0.5")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_0_5);
        } else if (dataBean.getLevel().equals("1")) {
            Log.e("okok", "okok");
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_1);
        } else if (dataBean.getLevel().equals("1.5")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_1_5);
        } else if (dataBean.getLevel().equals("2")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_2);
        } else if (dataBean.getLevel().equals("2.5")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_2_5);
        } else if (dataBean.getLevel().equals("3")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_3);
        } else if (dataBean.getLevel().equals("3.5")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_3_5);
        } else if (dataBean.getLevel().equals("4")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_4);
        } else if (dataBean.getLevel().equals("4.5")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_4_5);
        } else if (dataBean.getLevel().equals("5")) {
            viewHolder.imgShopPingxing.setImageResource(R.mipmap.ic_yellowstar_5);
        }
        viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shopid", dataBean.getId());
                intent.setClass(context, StoreHomepageActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView imgRestaurant,imgShopPingxing;
        TextView txRestaurantName,txRestaurantDistance,txRestaurantPrice;
        RelativeLayout rl_item;

        ViewHolder(View view) {
            imgRestaurant = (ImageView) view.findViewById(R.id.img_restaurant);
            txRestaurantName = (TextView) view.findViewById(R.id.tx_restaurant_name);
            imgShopPingxing = (ImageView) view.findViewById(R.id.img_shop_pingxing);
            txRestaurantDistance =(TextView) view.findViewById(R.id.tx_restaurant_distance);
            txRestaurantPrice = (TextView) view.findViewById(R.id.tx_restaurant_price);
            rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        }

    }
}
