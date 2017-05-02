package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.bean.Shop.GoodRecommendBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/5/21.
 */
public class GoodRecommendAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<GoodRecommendBean.DataBean> goodsRecommendDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public GoodRecommendAdapter(Context context, List<GoodRecommendBean.DataBean> goodsRecommendDataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.goodsRecommendDataBeanList = goodsRecommendDataBeanList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return goodsRecommendDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsRecommendDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final GoodRecommendBean.DataBean bean = goodsRecommendDataBeanList.get(position);
        String picName = goodsRecommendDataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.good_recommend_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                viewHolder.ivGoodPic, displayImageOptions);
        viewHolder.tvGoodName.setText(goodsRecommendDataBeanList.get(position).getGoodsname());
        viewHolder.tvGoodDesc.setText(goodsRecommendDataBeanList.get(position).getDescription());
        viewHolder.tvGoodPrice.setText("¥" + goodsRecommendDataBeanList.get(position).getPrice());
//        viewHolder.tvPayNUm.setText("¥" + goodsRecommendDataBeanList.get(position).getPayNum());


        viewHolder.llClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ll_good_recommend) {
                    Intent intent = new Intent();
                    intent.setClass(context, GoodsDetailActivity.class);
                    intent.putExtra("id", bean.getId());//传出goodId
                    intent.putExtra("artno", bean.getArtno());
                    intent.putExtra("shopid", bean.getShopid());//传出shopid
                    intent.putExtra("brand", bean.getBrand());
                    intent.putExtra("goodsname", bean.getGoodsname());
                    intent.putExtra("adtitle", bean.getAdtitle());
                    intent.putExtra("oprice", bean.getOprice());
                    intent.putExtra("price", bean.getPrice());//传出price
                    intent.putExtra("unit", bean.getUnit());
                    intent.putExtra("description", bean.getDescription());
                    intent.putExtra("pic", bean.getPicture());//传出pic
                    intent.putExtra("showid", bean.getShowid());
                    intent.putExtra("address", bean.getAddress());
                    intent.putExtra("freight", bean.getFreight());
                    intent.putExtra("pays", bean.getPays());
                    intent.putExtra("racking", bean.getRacking());
                    intent.putExtra("recommend", bean.getRecommend());
//                    intent.putExtra("shopname", bean.getShopname());//店铺名字
//                    intent.putExtra("sales", bean.getSales());
//                    intent.putExtra("paynum", bean.getPayNum());
                    context.startActivity(intent);
                }
            }
        });


        return convertView;
    }

    private class ViewHolder {
        ImageView ivGoodPic;
        TextView tvGoodDesc, tvGoodPrice, tvGoodOprice, tvPayNUm,tvGoodName;
        LinearLayout llClick;

        public ViewHolder(View view) {
            ivGoodPic = (ImageView) view.findViewById(R.id.good_recommend_pic);
            tvGoodName = (TextView) view.findViewById(R.id.good_recommend_name);
            tvGoodDesc = (TextView) view.findViewById(R.id.good_recommend_des);
            tvGoodPrice = (TextView) view.findViewById(R.id.good_recommend_price);
//            tvGoodOprice = (TextView) view.findViewById(R.id.new_arrival_good_oprice);
//            tvPayNUm = (TextView) view.findViewById(R.id.quality_life_pauNum);
            llClick = (LinearLayout) view.findViewById(R.id.ll_good_recommend);
//            tvGoodOprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }
}
