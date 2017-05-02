package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.DeliverNowActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.SellerOrderDetailsActivity;
import cn.xiaocool.android_etong.bean.business.SellerOrderBean;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/8/1.
 */
public class SellerOrderAdapter extends BaseAdapter {
    private List<SellerOrderBean.DataBean> dataBeans;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private String islocal;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public SellerOrderAdapter(Context context, List<SellerOrderBean.DataBean> dataBeans, String islocal) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.dataBeans = dataBeans;
        this.islocal = islocal;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final SellerOrderBean.DataBean product = dataBeans.get(position);
        final String state = dataBeans.get(position).getState();

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.seller_order_item, null);
            holder = new ViewHolder();
            holder.img_shopping_chanpin = (ImageView) convertView.findViewById(R.id.seller_order_img_shopping_chanpin);
            holder.tx_shopping_cloth_name = (TextView) convertView.findViewById(R.id.seller_order_tx_shopping_cloth_name);
            holder.tx_shopping_cloth_price = (TextView) convertView.findViewById(R.id.seller_order_tx_shopping_cloth_price);
            holder.tx_goods_count = (TextView) convertView.findViewById(R.id.seller_order_tx_goods_count);
            holder.tx_shopping_cloth_color = (TextView) convertView.findViewById(R.id.seller_order_tx_shopping_cloth_color);
            holder.tx_shopping_cloth_size = (TextView) convertView.findViewById(R.id.seller_order_tx_shopping_cloth_size);
            holder.tvBtn = (TextView) convertView.findViewById(R.id.seller_order_adapter_good_status_btn);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.seller_order_good_status_tv);
            holder.rlGoodInfor = (RelativeLayout) convertView.findViewById(R.id.rl_good_infor_up);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        String pic = product.getPicture();
        final String[] arraypic = pic.split("[,]");
        holder.rlGoodInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.rl_good_infor_up) {
                    Intent intent = new Intent();
//                    intent.putExtra("orderId", product.getId());//取orderId
                    intent.putExtra("name", product.getUsername());
                    intent.putExtra("address", product.getAddress());
                    intent.putExtra("mobile", product.getMobile());
                    intent.putExtra("state", product.getState());
                    intent.putExtra("goodsName", product.getGoodsname());
                    intent.putExtra("price", product.getPrice());
                    intent.putExtra("number", product.getNumber());
                    intent.putExtra("orderNum", product.getOrder_num());
                    intent.putExtra("createTime", product.getTime());
                    intent.setClass(context, SellerOrderDetailsActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        holder.tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.seller_order_adapter_good_status_btn) {
                    if (state.equals("2")) {
                        Intent intent = new Intent();
                        intent.setClass(context, DeliverNowActivity.class);
                        intent.putExtra("picName", arraypic[0]);
                        intent.putExtra("orderId", product.getId());
                        intent.putExtra("orderNum", product.getOrder_num());
                        intent.putExtra("orderTime", product.getTime());
                        Log.e("orderNUm + time + pic", arraypic[0] + product.getOrder_num() + product.getTime());
                        context.startActivity(intent);
                    }
                } else if (state.equals("3")) {

                }
            }
        });
        if (islocal.equals("0")) {
            if (state.equals("2")) {
                holder.tvStatus.setText("待验证");
                holder.tvBtn.setText("请扫码验证");
                holder.tvBtn.setEnabled(false);
            } else if (state.equals("3")) {
                holder.tvStatus.setText("待确认");
                holder.tvBtn.setText("实时追踪");
            } else if (state.equals("4")) {
                holder.tvStatus.setText("已完成");
                holder.tvBtn.setText("评价");
            }
            Log.e("pic=", arraypic[0]);
            imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_shopping_chanpin, displayImageOptions);
            holder.tx_shopping_cloth_name.setText(product.getGoodsname());
            holder.tx_shopping_cloth_price.setText("¥" + product.getMoney());
            holder.tx_goods_count.setText("X" + product.getNumber());
        }
        else{
            if (state.equals("2")) {
                holder.tvStatus.setText("待发货");
                holder.tvBtn.setText("马上发货");
            } else if (state.equals("3")) {
                holder.tvStatus.setText("待确认");
                holder.tvBtn.setText("实时追踪");
            } else if (state.equals("4")) {
                holder.tvStatus.setText("已完成");
                holder.tvBtn.setText("评价");
            }
            Log.e("pic=", arraypic[0]);
            imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_shopping_chanpin, displayImageOptions);
            holder.tx_shopping_cloth_name.setText(product.getGoodsname());
            holder.tx_shopping_cloth_price.setText("¥" + product.getMoney());
            holder.tx_goods_count.setText("X" + product.getNumber());
        }
        return convertView;

    }

    class ViewHolder {
        ImageView img_shopping_chanpin;
        TextView tx_shopping_cloth_name, tx_shopping_cloth_price, tx_shopping_cloth_oldprice, tx_goods_count;
        TextView tvBtn, tvStatus;
        RelativeLayout rlGoodInfor;
        CheckBox cb_select;
        RelativeLayout rl_select;
        Button btn_down, btn_up;
        TextView tv_number, tx_shopping_cloth_color, tx_shopping_cloth_size;
    }
}
