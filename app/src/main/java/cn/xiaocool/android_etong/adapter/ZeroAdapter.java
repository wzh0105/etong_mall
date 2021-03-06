package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.bean.HomePage.ZeroBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;

/**
 * Created by SJL on 2016/8/22.
 */
public class ZeroAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<ZeroBean.ZeroDataBean> zeroDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public ZeroAdapter(Context context, List<ZeroBean.ZeroDataBean> zeroDataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.zeroDataBeanList = zeroDataBeanList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return zeroDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return zeroDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final ZeroBean.ZeroDataBean bean = zeroDataBeanList.get(position);
        String picName = zeroDataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.everyday_choiceness_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.ivGoodPic, displayImageOptions);
            viewHolder.tvGoodName.setText(zeroDataBeanList.get(position).getGoodsname());
            viewHolder.tvGoodDesc.setText(zeroDataBeanList.get(position).getDescription());
            viewHolder.tvGoodPrice.setText("¥" + zeroDataBeanList.get(position).getPrice());
            viewHolder.tvGoodOprice.setText("¥" + zeroDataBeanList.get(position).getOprice());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                    viewHolder.ivGoodPic, displayImageOptions);
            viewHolder.tvGoodName.setText(zeroDataBeanList.get(position).getGoodsname());
            viewHolder.tvGoodDesc.setText(zeroDataBeanList.get(position).getDescription());
            viewHolder.tvGoodPrice.setText("¥" + zeroDataBeanList.get(position).getPrice());
            viewHolder.tvGoodOprice.setText("¥" + zeroDataBeanList.get(position).getOprice());
        }

        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.everyday_choiceness_btn_buy) {
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

    private class ViewHolder {
        ImageView ivGoodPic;
        TextView tvGoodDesc, tvGoodPrice, tvGoodOprice, tvGoodName;
        Button btn;

        public ViewHolder(View view) {
            ivGoodPic = (ImageView) view.findViewById(R.id.everyday_choiceness_pic);
            tvGoodName = (TextView) view.findViewById(R.id.everyday_choiceness_name);
            tvGoodDesc = (TextView) view.findViewById(R.id.everyday_choiceness_desc);
            tvGoodPrice = (TextView) view.findViewById(R.id.everyday_choiceness_price);
            tvGoodOprice = (TextView) view.findViewById(R.id.everyday_choiceness_oprice);
            btn = (Button) view.findViewById(R.id.everyday_choiceness_btn_buy);
            tvGoodOprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

}
