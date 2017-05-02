package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.Serializable;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.AddGoodAttributeActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.EditStandardItemActivity;
import cn.xiaocool.android_etong.bean.UploadGoodSanndard.UploadStandardBean;


/**
 * Created by wzh on 2016/5/21.
 */
public class UploadGoodItemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<UploadStandardBean.DataBean> dataBeanList;
    private List<UploadStandardBean.DataBean.PlistBean> pList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private String goodId;
    public UploadGoodItemAdapter(Context context, List<UploadStandardBean.DataBean> dataBeanList,String goodId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeanList = dataBeanList;
        this.context = context;
        this.goodId = goodId;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String itemName = dataBeanList.get(position).getName();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.add_good_standard_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.tvStandardName.setText(itemName);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.tvStandardName.setText(itemName);
        }
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.standard_rl_item) {
                    Intent intent = new Intent();
                    intent.setClass(context, AddGoodAttributeActivity.class);
                    pList = dataBeanList.get(position).getPlist();//???
                    String type = dataBeanList.get(position).getId();
                    Log.e("size is", String.valueOf(pList.size()));
//                    Log.e("name is here",name);
                    intent.putExtra("list", (Serializable) pList);
                    intent.putExtra("goodId",goodId);
                    intent.putExtra("type",type);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tvStandardName;
        RelativeLayout rlItem;

        public ViewHolder(View view) {
            tvStandardName = (TextView) view.findViewById(R.id.edit_standard_tv);
            rlItem = (RelativeLayout) view.findViewById(R.id.standard_rl_item);
        }

    }
}
