package cn.xiaocool.android_etong.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.business.remove;
import cn.xiaocool.android_etong.fragment.business.SellFragment;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/16.
 */
public class RemoveListAdapter extends BaseAdapter {
    private SellFragment.MyListener myListener;
    private ArrayList<remove.DataBean> remove_list;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private Context context;
    private Handler handler  = new Handler() {
    };
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public RemoveListAdapter(Context context,ArrayList<remove.DataBean> remove_list,SellFragment.MyListener myListener, Handler handler){
        this.layoutInflater = LayoutInflater.from(context);
        this.remove_list = remove_list;
        this.context = context;
        this.myListener = myListener;
        this.handler = handler;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return remove_list.size();
    }

    @Override
    public Object getItem(int position) {
        return remove_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final remove.DataBean dataBean = remove_list.get(position);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.business_goods_item_xiajia,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        String pic = dataBean.getPicture();
        String[] arraypic = pic.split("[,]");

        imageLoader.displayImage(WebAddress.GETAVATAR+arraypic[0],holder.img_pic,displayImageOptions);
        holder.tx_biaoti.setText(dataBean.getGoodsname());
        holder.tx_price.setText(dataBean.getPrice() + " ￥");
        holder.tx_oprice.setText("￥"+dataBean.getOprice());
        holder.tx_oprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.btn_shangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_shangjia) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("确认上架");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (NetUtil.isConnnected(context)) {
                                        new MainRequest(context, handler).goodsshangjia(dataBean.getId());
                                        remove_list.remove(position);
                                        notifyDataSetChanged();
                                        myListener.showMessage(2);
                                        Toast.makeText(context, "上架成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
        holder.btn_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_shanchu){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("确认删除");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (NetUtil.isConnnected(context)){
                                        new MainRequest(context,handler).deletegoods(dataBean.getId());
                                        remove_list.remove(position);
                                        notifyDataSetChanged();
                                        myListener.showMessage(2);
                                    }else {
                                        Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView img_pic;
        TextView tx_biaoti,tx_price,tx_oprice;
        Button btn_shangjia,btn_shanchu;
        public ViewHolder(View convertView){
            img_pic = (ImageView)convertView.findViewById(R.id.img_pic);
            tx_biaoti = (TextView)convertView.findViewById(R.id.tx_biaoti);
            tx_price = (TextView)convertView.findViewById(R.id.tx_price);
            tx_oprice = (TextView)convertView.findViewById(R.id.tx_oprice);
            btn_shangjia = (Button)convertView.findViewById(R.id.btn_shangjia);
            btn_shanchu = (Button)convertView.findViewById(R.id.btn_shanchu);
        }
    }
}
