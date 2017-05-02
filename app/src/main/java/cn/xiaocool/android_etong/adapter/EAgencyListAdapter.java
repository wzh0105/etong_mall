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
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.business.EAgencyShopBean;
import cn.xiaocool.android_etong.bean.business.OrderList;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by wzh on 2016/11/20.
 */
public class EAgencyListAdapter extends BaseAdapter {
    private List<EAgencyShopBean.DataBean> list;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public EAgencyListAdapter(Context context, List<EAgencyShopBean.DataBean> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final EAgencyShopBean.DataBean dataBean = list.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.e_agent_shop_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String pic = dataBean.getPhoto();
//        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + pic, holder.ivPic, displayImageOptions);
        holder.shopName.setText(dataBean.getShopname());
        holder.city.setText(dataBean.getCity());
        return convertView;
    }

    class ViewHolder {
        TextView shopName, city;
        ImageView ivPic;

        public ViewHolder(View convertView) {
            ivPic = (ImageView) convertView.findViewById(R.id.e_agency_pic);
            shopName = (TextView) convertView.findViewById(R.id.e_agency_shop_name);
            city = (TextView) convertView.findViewById(R.id.e_agency_city);
        }
    }
}
