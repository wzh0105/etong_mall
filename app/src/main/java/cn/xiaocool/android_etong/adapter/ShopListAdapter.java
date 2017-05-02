package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.StoreHomepageActivity;
import cn.xiaocool.android_etong.bean.Shop.ShopList;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/7/23.
 */
public class ShopListAdapter extends BaseAdapter {
    private ArrayList<ShopList.DataBean> shop_list;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ShopListAdapter(Context context,ArrayList<ShopList.DataBean> shop_list){
        this.layoutInflater = LayoutInflater.from(context);
        this.shop_list = shop_list;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return shop_list.size();
    }

    @Override
    public Object getItem(int position) {
        return shop_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final ShopList.DataBean dataBean = shop_list.get(position);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.activity_shoplist_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        imageLoader.displayImage(WebAddress.GETAVATAR+dataBean.getPhoto(),holder.img_shop_pic,displayImageOptions);
        holder.tx_shop_name.setText(dataBean.getShopname());
        holder.tx_shop_address.setText(dataBean.getAddress());
        holder.rl_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.rl_shop){
                    Intent intent = new Intent();
                    intent.putExtra("shopid",dataBean.getId());
                    intent.setClass(context, StoreHomepageActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    class ViewHolder{
        ImageView img_shop_pic;
        TextView tx_shop_name;
        TextView tx_shop_address;
        RelativeLayout rl_shop;
        public ViewHolder(View convertView){
            img_shop_pic = (ImageView)convertView.findViewById(R.id.img_restaurant);
            tx_shop_name = (TextView)convertView.findViewById(R.id.tx_restaurant_name);
            tx_shop_address = (TextView)convertView.findViewById(R.id.tx_restaurant_introduce);
            rl_shop = (RelativeLayout)convertView.findViewById(R.id.rl_shop);
        }
    }
}
