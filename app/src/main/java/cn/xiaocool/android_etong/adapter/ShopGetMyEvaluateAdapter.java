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
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.business.ShopMyEvaluateBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/10/26.
 */
public class ShopGetMyEvaluateAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<ShopMyEvaluateBean.DataBean> evaluateDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public ShopGetMyEvaluateAdapter(Context context, List<ShopMyEvaluateBean.DataBean> evaluateDataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.evaluateDataBeanList = evaluateDataBeanList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return evaluateDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return evaluateDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final ShopMyEvaluateBean.DataBean bean = evaluateDataBeanList.get(position);
        String picName = evaluateDataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.shop_my_evaluate_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                viewHolder.shopMyEvaluatePic, displayImageOptions);
        viewHolder.shopMyEvaluateName.setText(evaluateDataBeanList.get(position).getGoodsname());//名字
        viewHolder.shopEvaluateContent.setText(evaluateDataBeanList.get(position).getContent());//评价内容

        viewHolder.llShopEvaluateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.new_arrival_rl_click) {
//                    Intent intent = new Intent();
//                    intent.setClass(context, GoodsDetailActivity.class);
//                    intent.putExtra("id", bean.getId());//传出goodId
//                    intent.putExtra("artno", bean.getArtno());
//                    intent.putExtra("shopid", bean.getShopid());//传出shopid
//                    intent.putExtra("brand", bean.getBrand());
//                    intent.putExtra("goodsname", bean.getGoodsname());
//                    intent.putExtra("adtitle", bean.getAdtitle());
//                    intent.putExtra("oprice", bean.getOprice());
//                    intent.putExtra("price", bean.getPrice());//传出price
//                    intent.putExtra("unit", bean.getUnit());
//                    intent.putExtra("description", bean.getDescription());
//                    intent.putExtra("pic", bean.getPicture());//传出pic
//                    intent.putExtra("showid", bean.getShowid());
//                    intent.putExtra("address", bean.getAddress());
//                    intent.putExtra("freight", bean.getFreight());
//                    intent.putExtra("pays", bean.getPays());
//                    intent.putExtra("racking", bean.getRacking());
//                    intent.putExtra("recommend", bean.getRecommend());
//                    intent.putExtra("shopname", bean.getShopname());//店铺名字
//                    intent.putExtra("sales", bean.getSales());
//                    intent.putExtra("paynum", bean.getPayNum());
//                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.seller_order_shop_pic_icon)
        ImageView sellerOrderShopPicIcon;
        @BindView(R.id.seller_order_good_status_tv)
        TextView sellerOrderGoodStatusTv;
        @BindView(R.id.shop_my_evaluate_color_name)
        TextView shopMyEvaluateColorName;
        @BindView(R.id.shop_my_evaluate_size_name)
        TextView shopMyEvaluateSizeName;
        @BindView(R.id.shop_my_evaluate_count_name)
        TextView shopMyEvaluateCountName;
        @BindView(R.id.seller_order_tx_goods_count)
        TextView sellerOrderTxGoodsCount;
        @BindView(R.id.shop_my_evaluate_name)
        TextView shopMyEvaluateName;
        @BindView(R.id.shop_my_evaluate_pic)
        ImageView shopMyEvaluatePic;
        @BindView(R.id.rl_good_infor_up)
        RelativeLayout rlGoodInforUp;
        @BindView(R.id.gray_line2)
        ImageView grayLine2;
        @BindView(R.id.shop_evaluate_content)
        TextView shopEvaluateContent;
        @BindView(R.id.ll_shop_evaluate_item)
        LinearLayout llShopEvaluateItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


//    private class ViewHolder {
//        ImageView ivGoodPic;
//        TextView tvGoodDesc, tvGoodPrice, tvGoodOprice;
//        LinearLayout clickLayout;
//
//        public ViewHolder(View view) {
//            clickLayout = (LinearLayout) view.findViewById(R.id.new_arrival_rl_click);
//            ivGoodPic = (ImageView) view.findViewById(R.id.new_arrival_good_img);
//            tvGoodDesc = (TextView) view.findViewById(R.id.new_arrival_good_desc);
//            tvGoodPrice = (TextView) view.findViewById(R.id.new_arrival_good_price);
//            tvGoodOprice = (TextView) view.findViewById(R.id.new_arrival_good_oprice);
//            tvGoodOprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        }
//
//    }
}
