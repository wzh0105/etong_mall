package cn.xiaocool.android_etong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;
import cn.xiaocool.android_etong.fragment.ShoppingFragment;

/**
 * Created by 潘 on 2016/7/31.
 */
public class StoreAdapter extends BaseAdapter {

    private LinkedList<Boolean> selected = new LinkedList<Boolean>();
    private Boolean judge = false;
    private LayoutInflater inflater;
    private List<ShoppingCart_StoreName.DataBean> list;
    List<ProductAdapter> pAdapterList = new ArrayList<ProductAdapter>();
    private ShoppingFragment context;
    private RelativeLayout rl_store;
    private CheckBox cb_store;

    public StoreAdapter(ShoppingFragment context, List<ShoppingCart_StoreName.DataBean> list) {
        this.inflater = LayoutInflater.from(context.mContext);
        this.list = list;
        this.context = context;
        for (int i = 0; i < list.size(); i++) {
            getSelect().add(false);
            ProductAdapter pAdapter = new ProductAdapter(context, list.get(i).getGoodslist(),
                    this, i, judge);
            pAdapterList.add(pAdapter);
        }
    }

    public List<Boolean> getSelect() {
        return selected;
    }

    public ProductAdapter getPAdapter(int position) {
        return pAdapterList.get(position);
    }

    public List<ProductAdapter> getPAdapterList() {
        return pAdapterList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        final ShoppingCart_StoreName.DataBean store = list.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.shopping_cloth, null);
            holder = new ViewHolder();
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_edit = (TextView) convertView.findViewById(R.id.tv_edit);
            holder.list_goods = (ListView) convertView.findViewById(R.id.list_goods);
            holder.cb_store=(CheckBox)convertView.findViewById(R.id.cb_store);
            holder.rl_store = (RelativeLayout) convertView.findViewById(R.id.rl_store);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cb_store.setChecked(selected.get(position));
        holder.list_goods.setAdapter(pAdapterList.get(position));
        fixListViewHeight(holder.list_goods);
        holder.tv_shop_name.setText(store.getShopname());
        final TextView tv_edit = holder.tv_edit;
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judge = !judge;
                pAdapterList.get(position).setFlag(judge);
                if (judge) {
                    tv_edit.setText("完成");
                } else {
                    context.setUpdate();
                    tv_edit.setText("编辑");
                }
                notifyDataSetChanged();
            }
        });

        holder.rl_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("selected set position:" + position);
                selected.set(position, !selected.get(position));
                for (int i = 0; i < pAdapterList.get(position).getSelect().size(); i++) {
                    pAdapterList.get(position).getSelect().set(i, selected.get(position));
//                    System.out.println("selected " + i + ":" + pAdapterList.get(position).getSelect().get(i));
                }
                if (selected.contains(false)) {
                    context.checkAll(false);
                } else {
                    context.checkAll(true);
                }
                context.updateAmount();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_shop_name, tv_edit;
        ListView list_goods;
        RelativeLayout rl_store;
        CheckBox cb_store;
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
        return position;
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

            // 计算所有子项的高度和q

            totalHeight += listViewItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        // listView.getDividerHeight()获取子项间分隔符的高度

        // params.height设置ListView完全显示需要的高度

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }
}
