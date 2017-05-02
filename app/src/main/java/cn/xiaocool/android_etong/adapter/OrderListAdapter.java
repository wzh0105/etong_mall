package cn.xiaocool.android_etong.adapter;

import android.content.Context;
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
import cn.xiaocool.android_etong.bean.business.OrderList;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/7/21.
 */
public class OrderListAdapter extends BaseAdapter {
    private ArrayList<OrderList.DataBean> orderlist;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public OrderListAdapter(Context context, ArrayList<OrderList.DataBean> orderlist) {
        this.layoutInflater = LayoutInflater.from(context);
        this.orderlist = orderlist;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return orderlist.size();
    }

    @Override
    public Object getItem(int position) {
        return orderlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final OrderList.DataBean dataBean = orderlist.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_my_shopping_order_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String pic = dataBean.getPicture();
        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_pic, displayImageOptions);
        holder.tx_biaoti.setText(dataBean.getGoodsname());
        holder.tx_price.setText("¥" + dataBean.getMoney());
        holder.tx_number.setText("x" + dataBean.getNumber());
        return convertView;
    }

    class ViewHolder {
        ImageView img_pic;
        TextView tx_biaoti, tx_price, tx_number;

        public ViewHolder(View convertView) {
            img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
            tx_biaoti = (TextView) convertView.findViewById(R.id.tx_biaoti);
            tx_price = (TextView) convertView.findViewById(R.id.tx_price);
            tx_number = (TextView) convertView.findViewById(R.id.tx_number);
        }
    }
}
