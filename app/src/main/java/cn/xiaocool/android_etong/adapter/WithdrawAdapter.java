package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Mine.WithdrawRecord;

/**
 * Created by 潘 on 2016/11/20.
 */

public class WithdrawAdapter extends BaseAdapter {
    private Context context;
    private List<WithdrawRecord.DataBean> lists;
    private LayoutInflater inflater;

    public WithdrawAdapter(Context context, List<WithdrawRecord.DataBean> lists) {
        super();
        this.context = context;
        this.lists = lists;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final WithdrawRecord.DataBean dataBean = lists.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_withdraw_record_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvMoney.setText("提现: "+dataBean.getMoney());
        holder.tvTime.setText(dataBean.getTime());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
