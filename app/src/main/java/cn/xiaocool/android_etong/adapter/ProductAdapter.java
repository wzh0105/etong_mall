package cn.xiaocool.android_etong.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.Shop.ShoppingCart_StoreName;
import cn.xiaocool.android_etong.fragment.ShoppingFragment;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/7/31.
 */
public class ProductAdapter extends BaseAdapter {
    private List<Boolean> selected = new ArrayList<Boolean>();
    private RelativeLayout rl_select_;
    private LayoutInflater inflater;
    private DisplayImageOptions displayImageOptions;
    private List<ShoppingCart_StoreName.DataBean.GoodslistBean> list;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Boolean flag;
    StoreAdapter adapter;
    int storePosition;
    ShoppingFragment context;

    public ProductAdapter(ShoppingFragment context, List<ShoppingCart_StoreName.DataBean.GoodslistBean> list, StoreAdapter adapter, int storePosition, Boolean flag) {
        this.inflater = LayoutInflater.from(context.mContext);
        this.list = list;
        this.adapter = adapter;
        this.storePosition = storePosition;
        this.context = context;
        this.flag = flag;
        for (int j = 0; j < list.size(); j++) {
            getSelect().add(false);
        }
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    public List<Boolean> getSelect() {
        return selected;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final ShoppingCart_StoreName.DataBean.GoodslistBean product = list.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.shopping_cloth_item, null);
            holder = new ViewHolder();
            holder.img_shopping_chanpin = (ImageView) convertView.findViewById(R.id.img_shopping_chanpin);
            holder.tx_shopping_cloth_name = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_name);
            holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            holder.tx_shopping_cloth_price = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_price);
            holder.tx_shopping_cloth_oldprice = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_oldprice);
            holder.tx_shopping_cloth_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tx_goods_count = (TextView) convertView.findViewById(R.id.tx_goods_count);
            holder.tx_shopping_cloth_color = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_color);
            holder.tx_shopping_cloth_size = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_size);
            holder.rl_select = (RelativeLayout) convertView.findViewById(R.id.rl_select);
            holder.btn_down = (Button) convertView.findViewById(R.id.btn_down);
            holder.btn_up = (Button) convertView.findViewById(R.id.btn_up);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.cb_select = (CheckBox) convertView.findViewById(R.id.cb_select);
            holder.rl_select_ = (RelativeLayout) convertView.findViewById(R.id.rl_select_);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String pic = product.getPicture();
        String[] arraypic = pic.split("[,]");
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_shopping_chanpin, displayImageOptions);
        if (flag) {
            holder.tx_shopping_cloth_size.setVisibility(View.GONE);
            holder.tx_shopping_cloth_color.setVisibility(View.GONE);
            holder.tx_shopping_cloth_name.setVisibility(View.GONE);
            holder.tx_shopping_cloth_price.setVisibility(View.GONE);
            holder.tx_shopping_cloth_oldprice.setVisibility(View.GONE);
            holder.tx_goods_count.setVisibility(View.GONE);
            holder.rl_select.setVisibility(View.VISIBLE);
        } else {
            holder.tx_shopping_cloth_size.setVisibility(View.VISIBLE);
            holder.tx_shopping_cloth_color.setVisibility(View.VISIBLE);
            holder.tx_shopping_cloth_name.setVisibility(View.VISIBLE);
            holder.tx_shopping_cloth_price.setVisibility(View.VISIBLE);
            holder.tx_shopping_cloth_oldprice.setVisibility(View.VISIBLE);
            holder.tx_goods_count.setVisibility(View.VISIBLE);
            holder.rl_select.setVisibility(View.GONE);
        }
        holder.tx_shopping_cloth_oldprice.setText(product.getOprice());
        holder.tx_shopping_cloth_price.setText(product.getPrice());
        holder.tx_shopping_cloth_name.setText(product.getGoodsname());
        holder.tx_goods_count.setText(product.getNumber());
        holder.tv_number.setText(product.getNumber());
        holder.tx_shopping_cloth_color.setText(product.getProname());
        holder.cb_select.setChecked(selected.get(position));
        Log.e("new bool",selected.get(position).toString());
        holder.rl_select_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("selected set position:" + position);
                Log.e("selected set position", String.valueOf(position));
                selected.set(position, !selected.get(position));
                Boolean bool = !selected.get(position);
                Log.e("bool", bool.toString());
                if (selected.contains(false)) {
                    adapter.getSelect().set(storePosition, false);
                } else {
                    adapter.getSelect().set(storePosition, true);
                }

                if (adapter.getSelect().contains(false)) {
                    context.checkAll(false);
                } else {
                    context.checkAll(true);
                }

                context.updateAmount();
                adapter.notifyDataSetChanged();
            }
        });
        final TextView tv_number = holder.tv_number;
        final CheckBox cb_select = holder.cb_select;
        holder.btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(tv_number.getText().toString());
                number++;
                tv_number.setText(String.valueOf(number));
                context.setGoodsNumber(storePosition, position, String.valueOf(number));
                if (cb_select.isChecked()) {
                    context.updateAmount();
                }
            }
        });

        holder.btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(tv_number.getText().toString());
                number--;
                if (number > 0) {
                    tv_number.setText(String.valueOf(number));
                    context.setGoodsNumber(storePosition, position, String.valueOf(number));
                    if (cb_select.isChecked()) {
                        context.updateAmount();
                    }
                }
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context.getActivity());
                dialog.setMessage("确认删除？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.DeleteDate(list.get(position).getGid());
                        list.remove(position);
                        if (list.size() == 0) {
                            context.removePosition(storePosition);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView img_shopping_chanpin;
        TextView tx_shopping_cloth_name, tv_delete, tx_shopping_cloth_price, tx_shopping_cloth_oldprice, tx_goods_count;
        CheckBox cb_select;
        RelativeLayout rl_select, rl_select_;

        Button btn_down, btn_up;
        TextView tv_number, tx_shopping_cloth_color, tx_shopping_cloth_size;
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

}
