package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.business.StoreHomepage;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/7/19.
 */
public class StoreHomePageAdapter extends BaseAdapter {
    private ArrayList<StoreHomepage.DataBean> goods_list;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private String shopid, shop_uid, shop_photo;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public StoreHomePageAdapter(Context context, ArrayList<StoreHomepage.DataBean> goods_list, String shopid, String shop_uid, String shop_photo) {
        layoutInflater = LayoutInflater.from(context);
        this.goods_list = goods_list;
        this.context = context;
        this.shopid = shopid;
        this.shop_uid = shop_uid;
        this.shop_photo = shop_photo;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return goods_list.size();
    }

    @Override
    public Object getItem(int position) {
        return goods_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final StoreHomepage.DataBean dataBean = goods_list.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_store_homepage_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String pic = dataBean.getPicture();
        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_goods_pic, displayImageOptions);
        holder.tx_goods_price.setText("￥" + dataBean.getPrice());
        holder.tx_goods_name.setText(dataBean.getGoodsname());
        holder.img_goods_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.img_goods_pic) {
                    Intent intent = new Intent();
                    intent.putExtra("pic", dataBean.getPicture());
                    intent.putExtra("shopid", shopid);
                    intent.putExtra("id", dataBean.getId());
                    intent.putExtra("price", dataBean.getPrice());
                    intent.putExtra("oprice",dataBean.getOprice());
                    intent.putExtra("goodsname", dataBean.getGoodsname());
                    intent.putExtra("shopname", dataBean.getShowid());
                    intent.putExtra("shop_uid", shop_uid);
                    intent.putExtra("shop_photo", shop_photo);
                    intent.setClass(context, GoodsDetailActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView img_goods_pic;
        TextView tx_goods_name, tx_goods_price;

        public ViewHolder(View convertView) {
            img_goods_pic = (ImageView) convertView.findViewById(R.id.img_goods_pic);
            tx_goods_name = (TextView) convertView.findViewById(R.id.tx_goods_name);
            tx_goods_price = (TextView) convertView.findViewById(R.id.tx_goods_price);
        }
    }
}
