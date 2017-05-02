package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
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
import cn.xiaocool.android_etong.UI.HomePage.SearchActivity;
import cn.xiaocool.android_etong.UI.HomePage.SearchResultGoodsActivity;
import cn.xiaocool.android_etong.bean.HomePage.HomeList3Bean;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;


/**
 * Created by wzh on 2016/5/21.
 */
public class HomepageListThreeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<HomeList3Bean.DataBean> dataBeenList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public HomepageListThreeAdapter(Context context, List<HomeList3Bean.DataBean> dataBeenList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeenList = dataBeenList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
//        final NewArrivalBean.NewArrivalDataBean bean = newArrivalDataBeanList.get(position);
//        String picName = newArrivalDataBeanList.get(position).getPicture();
//        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.homepage_list3_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.llClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.homepage_click_list3){
                    Intent intent = new Intent();
                    intent.putExtra("search_content",dataBeenList.get(position).getLevelthree_name());
                    intent.putExtra("city","null");//此处 city传入空
                    Log.e("searchname is",dataBeenList.get(position).getLevelthree_name());
                    intent.setClass(context, SearchResultGoodsActivity.class);
                    context.startActivity(intent);
                }

            }
        });

//        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + dataBeenList.get(position).getPic(),
//                viewHolder.homepageList3Pic, displayImageOptions);
        viewHolder.homepageList3Name.setText(dataBeenList.get(position).getLevelthree_name());


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.homepage_list3_pic)
        ImageView homepageList3Pic;
        @BindView(R.id.homepage_list3_name)
        TextView homepageList3Name;
        @BindView(R.id.homepage_click_list3)
        LinearLayout llClick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
