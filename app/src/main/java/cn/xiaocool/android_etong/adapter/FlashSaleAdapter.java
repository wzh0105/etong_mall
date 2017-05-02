package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/5/21.
 */
public class FlashSaleAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private int judge;

    public FlashSaleAdapter(Context context, List<NewArrivalBean.NewArrivalDataBean> newArrivalDataBeanList,int judge) {
        this.layoutInflater = LayoutInflater.from(context);
        this.newArrivalDataBeanList = newArrivalDataBeanList;
        this.context = context;
        this.judge = judge;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return newArrivalDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return newArrivalDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final NewArrivalBean.NewArrivalDataBean bean = newArrivalDataBeanList.get(position);
        String[] arrayPic;
        String picName = newArrivalDataBeanList.get(position).getPicture();
        arrayPic=null;
        if (!TextUtils.isEmpty(picName)){
            Log.e("picname=",picName);
            arrayPic = picName.split("[,]");
            Log.e("keyi", "keyi");
            if (convertView == null) {

                if (judge==1){
                    convertView = layoutInflater.inflate(R.layout.flash_sale_item3, null);
                }else if (judge==2){
                    convertView = layoutInflater.inflate(R.layout.flash_sale_item, null);
                }else if (judge==3){
                    convertView = layoutInflater.inflate(R.layout.flash_sale_item1, null);
                }
                Log.e("judge=", String.valueOf(judge));
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                        viewHolder.ivGoodPic, displayImageOptions);
                viewHolder.tvGoodDesc.setText(newArrivalDataBeanList.get(position).getDescription());
                viewHolder.tvGoodPrice.setText("¥" + newArrivalDataBeanList.get(position).getPrice());
                viewHolder.tvGoodOprice.setText("¥" + newArrivalDataBeanList.get(position).getOprice());
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                        viewHolder.ivGoodPic, displayImageOptions);
                viewHolder.tvGoodDesc.setText(newArrivalDataBeanList.get(position).getDescription());
                viewHolder.tvGoodPrice.setText("¥" + newArrivalDataBeanList.get(position).getPrice());
                viewHolder.tvGoodOprice.setText("¥" + newArrivalDataBeanList.get(position).getOprice());
            }
            if (judge==1){
                viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"已设置提醒",Toast.LENGTH_SHORT).show();
                    }
                });
            }else if (judge==2){

                viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.flash_sale_btn) {
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
                            intent.putExtra("paynum", bean.getPayNum());
                            context.startActivity(intent);
                        }
                    }
                });

            }else if (judge==3){
                viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"已结束",Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }else {
            Log.e("bukeyi",newArrivalDataBeanList.get(position).getId());
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView ivGoodPic;
        TextView tvGoodDesc, tvGoodPrice, tvGoodOprice;
        Button btn;

        public ViewHolder(View view) {
            ivGoodPic = (ImageView) view.findViewById(R.id.new_arrival_good_img1);
            tvGoodDesc = (TextView) view.findViewById(R.id.new_arrival_good_desc);
            tvGoodPrice = (TextView) view.findViewById(R.id.new_arrival_good_price);
            tvGoodOprice = (TextView) view.findViewById(R.id.new_arrival_good_oprice);
            btn = (Button) view.findViewById(R.id.flash_sale_btn);
            tvGoodOprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }
}
