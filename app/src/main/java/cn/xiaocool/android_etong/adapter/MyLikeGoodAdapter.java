package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.GoodsDetailActivity;
import cn.xiaocool.android_etong.bean.MyLikeBean.MyLikeGoodBean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/5/21.
 */
public class MyLikeGoodAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<MyLikeGoodBean.MyLikeGoodDataBean> myLikeGoodDataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    public MyLikeGoodAdapter(Context context, List<MyLikeGoodBean.MyLikeGoodDataBean> myLikeGoodDataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.myLikeGoodDataBeanList = myLikeGoodDataBeanList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return myLikeGoodDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return myLikeGoodDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final MyLikeGoodBean.MyLikeGoodDataBean bean = myLikeGoodDataBeanList.get(position);
        //初始化控件
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.my_like_good_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(myLikeGoodDataBeanList.get(position).getGoodsName());

        viewHolder.tvPrice.setText("¥" + myLikeGoodDataBeanList.get(position).getPrice());
        ImageView imgPic = (ImageView) convertView.findViewById(R.id.my_like_good_pic);
        ImageView imgStar = (ImageView) convertView.findViewById(R.id.my_like_good_star_level);
        String starNum = myLikeGoodDataBeanList.get(position).getStarLevel();
        Log.e("level", starNum);
        if (starNum.equals("0")) {
            imgStar.setImageResource(R.mipmap.ic_yellowstar_0);
        } else if (starNum.equals("0.5")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_0_5);
        } else if (starNum.equals("1")) {
            Log.e("okok", "okok");
            imgPic.setImageResource(R.mipmap.ic_yellowstar_1);
        } else if (starNum.equals("1.5")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_1_5);
        } else if (starNum.equals("2")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_2);
        } else if (starNum.equals("2.5")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_2_5);
        } else if (starNum.equals("3")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_3);
        } else if (starNum.equals("3.5")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_3_5);
        } else if (starNum.equals("4")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_4);
        } else if (starNum.equals("4.5")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_4_5);
        } else if (starNum.equals("5")) {
            imgPic.setImageResource(R.mipmap.ic_yellowstar_5);
        }

        String picName = myLikeGoodDataBeanList.get(position).getPhoto();
        String[] arrayPic = picName.split("[,]");
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0], imgPic, displayImageOptions);
        viewHolder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.my_like_good_btn_buy) {
                    Intent intent = new Intent();
                    intent.setClass(context, GoodsDetailActivity.class);
                    intent.putExtra("id", bean.getId());//传出goodId
                    intent.putExtra("artno", "");
                    intent.putExtra("shopid", "");//传出shopid
                    intent.putExtra("brand", "");
                    intent.putExtra("goodsname", bean.getGoodsName());
                    intent.putExtra("adtitle", "");
                    intent.putExtra("oprice","");
                    intent.putExtra("price", bean.getPrice());//传出price
                    intent.putExtra("unit","");
                    intent.putExtra("description", bean.getDescription());
                    intent.putExtra("pic","");//传出pic
                    intent.putExtra("showid", "");
                    intent.putExtra("address", "");
                    intent.putExtra("freight", "");
                    intent.putExtra("pays", "");
                    intent.putExtra("racking", "");
                    intent.putExtra("recommend", "");
                    intent.putExtra("shopname", "");//店铺名字
                    intent.putExtra("sales", "");
                    intent.putExtra("paynum", "");
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
        private final TextView tvTitle;
        private final Button btnBuy;
        private final TextView tvPrice;

        public ViewHolder(View view) {
            tvPrice = (TextView) view.findViewById(R.id.my_like_good_price);
            tvTitle = (TextView) view.findViewById(R.id.my_like_good_title);
            btnBuy = (Button) view.findViewById(R.id.my_like_good_btn_buy);
        }

    }
}
