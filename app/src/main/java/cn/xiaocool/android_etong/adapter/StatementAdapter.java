package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;
import cn.xiaocool.android_etong.util.NoScrollListView;

/**
 * Created by 潘 on 2016/8/26.
 */
public class StatementAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ShoppingCart_StoreName.DataBean> dataBeans;
    private StatementItemAdapter statementItemAdapter;

    public StatementAdapter(Context context, List<ShoppingCart_StoreName.DataBean> dataBeans) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataBeans = dataBeans;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingCart_StoreName.DataBean dataBean = dataBeans.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_statement_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txShopName.setText(dataBean.getShopname());
        statementItemAdapter = new StatementItemAdapter(context, dataBean.getGoodslist());
        holder.listGoods.setAdapter(statementItemAdapter);
        int amount = 0;
        for (int j = 0; j < dataBean.getGoodslist().size(); j++) {
            amount += Double.valueOf(dataBean.getGoodslist().get(j).getPrice())
                    * Integer.valueOf(dataBean.getGoodslist().get(j).getNumber());
        }
        holder.txGoodsPriceSubtotal.setText(String.valueOf(amount));
        fixListViewHeight(holder.listGoods);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tx_shop_name)
        TextView txShopName;
        @BindView(R.id.list_goods)
        NoScrollListView listGoods;
        @BindView(R.id.et_customer_remark)
        EditText etCustomerRemark;
        @BindView(R.id.tx_goods_price_subtotal)
        TextView txGoodsPriceSubtotal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void fixListViewHeight(ListView listView) {

        // 如果没有设置数据适配器，则ListView没有子项，返回。

        ListAdapter listAdapter = listView.getAdapter();

        int totalHeight = 0;

        if (listAdapter == null) {

            return;

        }

        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {

            View listViewItem = listAdapter.getView(index, null, listView);

            // 计算子项View 的宽高

            listViewItem.measure(0, 0);

            // 计算所有子项的高度和

            totalHeight += listViewItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        // listView.getDividerHeight()获取子项间分隔符的高度

        // params.height设置ListView完全显示需要的高度

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }
}
