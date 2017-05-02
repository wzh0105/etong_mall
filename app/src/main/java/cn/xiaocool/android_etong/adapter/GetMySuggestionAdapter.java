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
import cn.xiaocool.android_etong.bean.Mine.MySuggestionsBean;
import cn.xiaocool.android_etong.bean.UploadGoodSanndard.UploadStandardBean;
import cn.xiaocool.android_etong.util.TimeToolUtils;


/**
 * Created by wzh on 2016/12/25.
 */
public class GetMySuggestionAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<MySuggestionsBean.DataBean> dataBeanList;
    private Context context;

    public GetMySuggestionAdapter(Context context, List<MySuggestionsBean.DataBean> dataBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeanList = dataBeanList;
        this.context = context;
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.get_suggestion_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvContent.setText(dataBeanList.get(position).getContent());
        viewHolder.tvTime.setText(TimeToolUtils.timeStampDateString(dataBeanList.get(position).getCreate_time(),"yyyy-MM-dd HH:mm"));
        return convertView;
    }


    private class ViewHolder {
        TextView tvContent,tvTime;

        public ViewHolder(View view) {
            tvContent = (TextView) view.findViewById(R.id.write_suggestions_content);
            tvTime = (TextView) view.findViewById(R.id.write_suggestions_time);
        }

    }

}
