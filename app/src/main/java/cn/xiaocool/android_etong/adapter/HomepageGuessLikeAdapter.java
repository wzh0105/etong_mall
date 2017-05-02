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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.HomePage.GuessLikeBean;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.util.ImgLoadUtil;


/**
 * Created by wzh on 2016/5/21.
 */
public class HomepageGuessLikeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<GuessLikeBean.GuessLikeDataBean> guessLikeDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public HomepageGuessLikeAdapter(Context context, List<GuessLikeBean.GuessLikeDataBean> guessLikeDataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.guessLikeDataBeanList = guessLikeDataBeanList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return guessLikeDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return guessLikeDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final GuessLikeBean.GuessLikeDataBean bean = guessLikeDataBeanList.get(position);
        String picName = guessLikeDataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.homepage_guess_ulike_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
//            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
//                    viewHolder.homepageGuessLikeImage, displayImageOptions);
//            viewHolder.newArrivalGoodDesc.setText(guessLikeDataBeanList.get(position).getDescription());
//            viewHolder.homepageGuessLikePrice.setText("¥" + guessLikeDataBeanList.get(position).getPrice());
//            viewHolder.homepageGuessLikePayNum.setText("¥" + guessLikeDataBeanList.get(position).getPaynum());


        }

        if (arrayPic.length>0){
            ImgLoadUtil.display(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],viewHolder.homepageGuessLikeImage);
//            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
//                    viewHolder.homepageGuessLikeImage, displayImageOptions);
        }
        viewHolder.newArrivalGoodDesc.setText(guessLikeDataBeanList.get(position).getDescription());
        viewHolder.homepageGuessLikePrice.setText("¥" + guessLikeDataBeanList.get(position).getPrice());
        viewHolder.homepageGuessLikePayNum.setText(guessLikeDataBeanList.get(position).getPaynum());


        viewHolder.llClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.homepage_guess_like_ll) {
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
                    intent.putExtra("shopname", bean.getShopname());//店铺名字
                    intent.putExtra("sales", bean.getSales());
                    intent.putExtra("paynum", bean.getPaynum());
                    context.startActivity(intent);
                }
            }
        });


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.homepage_guess_like_image)
        ImageView homepageGuessLikeImage;
        @BindView(R.id.new_arrival_good_desc)
        TextView newArrivalGoodDesc;
        @BindView(R.id.homepage_guess_like_price)
        TextView homepageGuessLikePrice;
        @BindView(R.id.homepage_guess_like_payNum)
        TextView homepageGuessLikePayNum;
        @BindView(R.id.homepage_guess_like_ll)
        LinearLayout llClick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
