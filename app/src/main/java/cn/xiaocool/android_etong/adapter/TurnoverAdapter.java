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

import java.util.List;

import cn.xiaocool.android_etong.Local;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.StoreHomepageActivity;
import cn.xiaocool.android_etong.bean.business.Turnover;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.view.SwipeListLayout;

import static cn.xiaocool.android_etong.util.TimeToolUtils.fromateTimeShowByRule;

/**
 * Created by hzh on 2016/12/28.
 */

public class TurnoverAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private DisplayImageOptions displayImageOptions;
    private List<Turnover.DataBean> locals;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public TurnoverAdapter(Context context, List<Turnover.DataBean> locals) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.locals = locals;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return locals.size();
    }

    @Override
    public Object getItem(int position) {
        return locals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Turnover.DataBean local = locals.get(position);
        if (convertView == null) {
            convertView=inflater.inflate(R.layout.activity_turnover_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        String pic = local.getPicture();
        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_restaurant, displayImageOptions);
        holder.tx_restaurant_name.setText(local.getGoodsname());
        holder.tx_restaurant_menshijia.setText(fromateTimeShowByRule(Long.parseLong(local.getTime())));
        holder.tv_price.setText(local.getMoney());
        return convertView;
    }
    class ViewHolder {
        ImageView img_restaurant;
        TextView tx_restaurant_name, tx_restaurant_menshijia, tv_price;

        public ViewHolder(View view) {
            img_restaurant = (ImageView) view.findViewById(R.id.img_restaurant);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tx_restaurant_name = (TextView) view.findViewById(R.id.tx_restaurant_name);
            tx_restaurant_menshijia = (TextView) view.findViewById(R.id.tx_restaurant_menshijia);

        }

    }

}
