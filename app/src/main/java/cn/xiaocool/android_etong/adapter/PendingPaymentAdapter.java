package cn.xiaocool.android_etong.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.BuyWriteCommentActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.CancelOrderActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.OrderDetailsActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.PayNowActivity;
import cn.xiaocool.android_etong.bean.Mine.PendingPayment;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.ToastUtils;

import static android.view.View.VISIBLE;

/**
 * Created by 潘 on 2016/8/1.
 */
public class PendingPaymentAdapter extends BaseAdapter {
    private List<PendingPayment.DataBean> dataBeans;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.CONFIRM_GOOD:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        if (jsonObject.getString("status").equals("success")) {
                            ToastUtils.makeShortToast(context, "确认收货成功！");
                            Intent intent = new Intent();
//                            intent.setClass(context,);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    public PendingPaymentAdapter(Context context, List<PendingPayment.DataBean> dataBeans) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.dataBeans = dataBeans;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final PendingPayment.DataBean product = dataBeans.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.pendingpayment_item, null);
            holder = new ViewHolder();
            holder.img_shopping_chanpin = (ImageView) convertView.findViewById(R.id.img_shopping_chanpin);
            holder.tx_shopping_cloth_name = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_name);
            holder.tx_shopping_cloth_price = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_price);
            holder.tx_goods_count = (TextView) convertView.findViewById(R.id.tx_goods_count);
            holder.tx_shopping_cloth_color = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_color);
            holder.tx_shopping_cloth_size = (TextView) convertView.findViewById(R.id.tx_shopping_cloth_size);
            holder.tvBtn = (TextView) convertView.findViewById(R.id.adapter_order_good_status_btn);
            holder.tvBtnRight2 = (TextView) convertView.findViewById(R.id.good_status_btn_2);
            holder.tvBtnRight3 = (TextView) convertView.findViewById(R.id.good_status_btn_3);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.good_status_tv);
            holder.rlGoodInfor = (RelativeLayout) convertView.findViewById(R.id.rl_good_infor_up);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String pic = product.getPicture();
        final String[] arraypic = pic.split("[,]");
        final String state = dataBeans.get(position).getState();
        final String deliverytype = dataBeans.get(position).getDeliverytype();
        holder.rlGoodInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.rl_good_infor_up) {
                    Intent intent = new Intent();
                    intent.putExtra("orderId", product.getId());//取orderId
//                    intent.putExtra("name", product.getUsername());
//                    intent.putExtra("address", product.getAddress());
//                    intent.putExtra("mobile", product.getMobile());
//                    intent.putExtra("state", product.getState());
//                    intent.putExtra("goodsName", product.getGoodsname());
//                    intent.putExtra("price", product.getPrice());
//                    intent.putExtra("number", product.getNumber());
//                    intent.putExtra("orderNum", product.getOrder_num());
//                    intent.putExtra("createTime", product.getTime());

                    intent.setClass(context, OrderDetailsActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        holder.tvBtnRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.good_status_btn_2) {
                    Intent intent = new Intent();
                    intent.putExtra("orderId", product.getId());
                    intent.setClass(context, CancelOrderActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        holder.tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.adapter_order_good_status_btn) {
                    if (state.equals("1")) {
                        Intent intent = new Intent();
                        intent.putExtra("orderId", product.getId());
                        intent.putExtra("price", product.getPrice());
                        Log.e(product.getId(),product.getPrice());
                        intent.setClass(context, PayNowActivity.class);

                        context.startActivity(intent);
                    } else if (state.equals("3")) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setMessage("确认收货？");
                        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new ShopRequest(context, handler).confirmGood(product.getId());
                                dataBeans.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                        dialog.show();
                    } else if (state.equals("4")) {
                        Intent intent = new Intent();
                        intent.setClass(context, BuyWriteCommentActivity.class);
                        intent.putExtra("orderId", product.getId());
                        intent.putExtra("name", product.getGoodsname());
                        intent.putExtra("picture", arraypic[0]);//传下去图片地址
                        context.startActivity(intent);
                    }
                }
            }
        });

        if (state.equals("0")) {
            holder.tvStatus.setText("已取消");
            holder.tvBtn.setVisibility(View.GONE);
            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("1")) {
            holder.tvStatus.setText("待付款");
            holder.tvBtn.setVisibility(VISIBLE);
            holder.tvBtn.setText("马上付款");
            holder.tvBtnRight2.setVisibility(VISIBLE);
            holder.tvBtnRight2.setText("取消订单");

            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("2")) {
            holder.tvStatus.setText("待发货");
            holder.tvBtn.setVisibility(VISIBLE);
            holder.tvBtn.setText("提醒发货");
            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("3")) {
            holder.tvStatus.setText("待收货");
            holder.tvBtn.setText("确认收货");
            holder.tvBtn.setVisibility(VISIBLE);

            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("4")) {
            holder.tvStatus.setText("待评论");
            holder.tvBtn.setText("去评价");
            holder.tvBtn.setVisibility(VISIBLE);

            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("5")) {
            holder.tvBtn.setText("再次评价");
            holder.tvBtn.setVisibility(VISIBLE);

            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("10")) {

            holder.tvBtn.setVisibility(View.GONE);

            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        }
//
        Log.e("pic=", arraypic[0]);
        imageLoader.displayImage(WebAddress.GETAVATAR + arraypic[0], holder.img_shopping_chanpin, displayImageOptions);
        holder.tx_shopping_cloth_name.setText(product.getGoodsname());
        holder.tx_shopping_cloth_price.setText("¥" + product.getMoney());
        holder.tx_goods_count.setText("X" + product.getNumber());

        return convertView;
    }

    class ViewHolder {
        ImageView img_shopping_chanpin;
        TextView tx_shopping_cloth_name, tx_shopping_cloth_price, tx_shopping_cloth_oldprice, tx_goods_count;
        TextView tvBtn, tvStatus, tvBtnRight2, tvBtnRight3;
        RelativeLayout rlGoodInfor;
        CheckBox cb_select;
        RelativeLayout rl_select;
        Button btn_down, btn_up;
        TextView tx_shopping_cloth_color, tx_shopping_cloth_size;
    }
}
