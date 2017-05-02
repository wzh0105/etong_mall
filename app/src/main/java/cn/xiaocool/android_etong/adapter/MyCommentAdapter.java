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
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.OrderDetailsActivity;
import cn.xiaocool.android_etong.bean.Mine.MyCommentBean;
import cn.xiaocool.android_etong.bean.Mine.PendingPayment;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;


/**
 * Created by wzh on 2016/8/13.
 */
public class MyCommentAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<MyCommentBean.DataBean> dataBeanList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    public MyCommentAdapter(Context context, List<MyCommentBean.DataBean> dataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeanList = dataBeanList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final MyCommentBean.DataBean product = dataBeanList.get(position);
        String picName = dataBeanList.get(position).getPicture();
        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.buy_my_evaluate_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + arrayPic[0],
                viewHolder.shopMyEvaluatePic, displayImageOptions);
        viewHolder.shopMyEvaluateName.setText(dataBeanList.get(position).getGoodsname());//商品名字
        viewHolder.shopEvaluateContent.setText(dataBeanList.get(position).getContent());//获取评价内容
        //设置点击跳转订单详情
        viewHolder.llShopEvaluateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ll_shop_evaluate_item) {
                    Intent intent = new Intent();
                    intent.putExtra("orderId", product.getOrderid());//取orderId

                    intent.setClass(context, OrderDetailsActivity.class);
                    context.startActivity(intent);
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
        @BindView(R.id.shop_my_evaluate_pic)
        ImageView shopMyEvaluatePic;
        @BindView(R.id.shop_my_evaluate_name)
        TextView shopMyEvaluateName;
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

//    static class ViewHolder {
//        @BindView(R.id.my_comment_good_pic)
//        ImageView myCommentGoodPic;
//        @BindView(R.id.my_comment_good_name)
//        TextView myCommentGoodName;
//        @BindView(R.id.my_comment_good_comment)
//        TextView myCommentGoodComment;
//
//        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }
}
