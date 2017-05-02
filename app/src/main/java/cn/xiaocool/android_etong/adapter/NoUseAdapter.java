package cn.xiaocool.android_etong.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.BuyWriteCommentActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.CancelOrderActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.OrderDetailsActivity;
import cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails.PayNowActivity;
import cn.xiaocool.android_etong.bean.Mine.PendingPayment;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.ShopRequest;
import cn.xiaocool.android_etong.util.CodeUtils;
import cn.xiaocool.android_etong.util.ToastUtils;

import static android.view.View.VISIBLE;

/**
 * Created by SJL on 2016/10/21.
 */

public class NoUseAdapter extends BaseAdapter {
    private List<PendingPayment.DataBean> dataBeans;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private Bitmap bitmap=null;
    private String textContent="";
    private String text,userId;
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

    public NoUseAdapter(Context context, List<PendingPayment.DataBean> dataBeans) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final PendingPayment.DataBean product = dataBeans.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.no_use, null);
            holder = new ViewHolder();
            holder.iv_erweima = (ImageView) convertView.findViewById(R.id.iv_erweima);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.rl_detiels = (RelativeLayout) convertView.findViewById(R.id.rl_detiels);
            holder.tvBtn = (TextView) convertView.findViewById(R.id.adapter_order_good_status_btn);
            holder.tvBtnRight2 = (TextView) convertView.findViewById(R.id.good_status_btn_2);
            holder.tvBtnRight3 = (TextView) convertView.findViewById(R.id.good_status_btn_3);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.good_status_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        textContent = product.getTracking();
        holder.tv_number.setText(textContent);

        holder.iv_erweima = (ImageView) convertView.findViewById(R.id.iv_erweima);
        bitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_920));
        holder.iv_erweima.setImageBitmap(bitmap);

        String pic = product.getPicture();
        final String[] arraypic = pic.split("[,]");
        final String state = dataBeans.get(position).getState();
//        final String deliverytype = dataBeans.get(position).getDeliverytype();
        holder.rl_detiels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.rl_detiels) {
                    Intent intent = new Intent();
                    intent.putExtra("name", product.getUsername());
                    intent.putExtra("address", product.getAddress());
                    intent.putExtra("mobile", product.getMobile());
                    intent.putExtra("state", product.getState());
                    intent.putExtra("goodsName", product.getGoodsname());
                    intent.putExtra("price", product.getPrice());
                    intent.putExtra("number", product.getNumber());
                    intent.putExtra("orderNum", product.getOrder_num());
                    intent.putExtra("createTime", product.getTime());

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
            holder.tvBtn.setText("马上付款");
            holder.tvBtn.setVisibility(VISIBLE);
            holder.tvBtnRight2.setText("取消订单");
            holder.tvBtnRight2.setVisibility(VISIBLE);

            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("2")) {
            holder.tvStatus.setText("待使用");
            holder.tvBtn.setVisibility(View.GONE);
            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        } else if (state.equals("4")) {
            holder.tvStatus.setText("待评论");
            holder.tvBtn.setText("去评价");
            holder.tvBtn.setVisibility(VISIBLE);

            holder.iv_erweima.setVisibility(View.GONE);
            holder.tvBtnRight2.setVisibility(View.GONE);
            holder.tvBtnRight3.setVisibility(View.GONE);
        }

        holder.tv_shop_name.setText(product.getGoodsname());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Long time = Long.valueOf(product.getTime());
        String d = format.format((new Date(time * 1000)));
        holder.tv_time.setText(d);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_erweima;
        TextView tv_shop_name, tv_number, tv_time;
        TextView tvBtn, tvStatus, tvBtnRight2, tvBtnRight3;
        RelativeLayout rl_detiels;
    }

}
