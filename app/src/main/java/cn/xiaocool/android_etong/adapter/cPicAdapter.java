package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.net.constant.WebAddress;

/**
 * Created by 潘 on 2016/11/2.
 */

public class cPicAdapter extends BaseAdapter {
    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;

    public cPicAdapter(Context context, List<String> lists) {
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
        String pic = lists.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cpic_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        Log.e("pic=",pic);
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + pic, holder.imgPic);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_pic)
        ImageView imgPic;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public static int dp2px(Context context, int dp)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
