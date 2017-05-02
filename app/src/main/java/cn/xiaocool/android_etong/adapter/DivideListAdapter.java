package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XScrollView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.HomePage.TypeGoodsList;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.ImgLoadUtil;
import cn.xiaocool.android_etong.util.NoScrollGridView;
import cn.xiaocool.android_etong.util.NoScrollListView;
import cn.xiaocool.android_etong.util.StringJoint;



/**
 * Created by 潘 on 2016/8/8.
 */
public class DivideListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<TypeGoodsList> typeGoodsLists;
    private Context mContext;
    private NoScrollGridView listView;
    public DivideListAdapter(Context context,List<TypeGoodsList> typeGoodsLists,NoScrollGridView listView ) {
        this.listView = listView;
        this.mContext = context;
        this.typeGoodsLists = typeGoodsLists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return typeGoodsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return typeGoodsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_divide_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TypeGoodsList good = typeGoodsLists.get(position);

        String[] imgs = good.getPicture().split("[,]");
        final Object tag = new Object();
        if (imgs.length>0){
//            if (position>listView.getFirstVisiblePosition()&&position<listView.getLastVisiblePosition()){

                Picasso.with(mContext).load(WebAddress.GETAVATAR + imgs[0]).resize(100,100).tag(tag).into(holder.itemImg);
//                ImgLoadUtil.display(WebAddress.GETAVATAR + imgs[0],holder.itemImg);
//            }
        }


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int newState) {
                if (newState == AbsListView.SCREEN_STATE_OFF)
                {
                    Picasso.with(mContext).resumeTag(tag);
                }
                else
                {
                    Picasso.with(mContext).pauseTag(tag);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        holder.itemDescrpTv.setText(good.getGoodsname());
        holder.itemPriceTv.setText("¥" + good.getPrice());
        holder.itemNumTv.setText("已购" + good.getPaynum() + "件");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_descrp_tv)
        TextView itemDescrpTv;
        @BindView(R.id.item_price_tv)
        TextView itemPriceTv;
        @BindView(R.id.item_num_tv)
        TextView itemNumTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
