package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.UploadGoodSanndard.UploadStandardBean;


/**
 * Created by wzh on 2016/5/21.
 */
public class AddGoodAttributeAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<UploadStandardBean.DataBean.PlistBean> plistBeanList;
    private Context context;

    public AddGoodAttributeAdapter(Context context, List<UploadStandardBean.DataBean.PlistBean> plistBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.plistBeanList = plistBeanList;
        this.context = context;
//        List<UploadStandardBean.DataBean.PlistBean> plist = dataBeenList.get(0).getPlist();
    }

    @Override
    public int getCount() {
        return plistBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return plistBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
//        List<UploadStandardBean.DataBean.PlistBean> plist = plistBeanList.get(position).getName();
        Log.e("plist size", String.valueOf(plistBeanList.size()));
//        String itemName = plistBeanList.get(position).getName();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.add_good_attribute_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.tvAttributeName.setText(plistBeanList.get(position).getName());
            viewHolder.tvAttributeName.setTag(plistBeanList.get(position).getId());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.tvAttributeName.setText(plistBeanList.get(position).getName());
            viewHolder.tvAttributeName.setTag(plistBeanList.get(position).getId());
        }
        viewHolder.tvAttributeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.add_good_attribute_tv_name) {
                    if (!viewHolder.tvAttributeName.isSelected()) {
                        viewHolder.tvAttributeName.setSelected(true);
                        viewHolder.tvAttributeName.setTextColor(context.getResources().getColor(R.color.whilte));
                    } else {
                        viewHolder.tvAttributeName.setSelected(false);
                        viewHolder.tvAttributeName.setTextColor(context.getResources().getColor(R.color.black2));
                    }
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tvAttributeName;

        public ViewHolder(View view) {
            tvAttributeName = (TextView) view.findViewById(R.id.add_good_attribute_tv_name);
        }

    }

}
