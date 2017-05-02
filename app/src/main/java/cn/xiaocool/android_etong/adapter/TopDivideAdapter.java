package cn.xiaocool.android_etong.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.bean.HomePage.MenuTypeList;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.ImgLoadUtil;
import cn.xiaocool.android_etong.util.PicassoImageLoader;

/**
 * Created by xiaocool on 17/2/6.
 */

public class TopDivideAdapter extends ArrayAdapter<String>{

    private int mResourceId;
    private Context mContext;
    private List<MenuTypeList.ChildlistBeanX> childlistBeanXes;
    public TopDivideAdapter(Context context, int resource,List<MenuTypeList.ChildlistBeanX> childlistBeanXes) {
        super(context, resource);
        mContext = context;
        this.mResourceId = resource;
        this.childlistBeanXes = childlistBeanXes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResourceId, null);

        TextView textView = (TextView) view.findViewById(R.id.item_text);
        textView.setText(childlistBeanXes.get(position).getName());
        ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
        if (childlistBeanXes.get(position).getPhoto().equals("全部")){
            imageView.setBackgroundResource(R.mipmap.new_list_avatar);
        }else {
            Picasso.with(mContext)
                    .load(WebAddress.GETAVATAR+childlistBeanXes.get(position).getPhoto())
                    .placeholder(R.drawable.hyx_default)
                    .config(Bitmap.Config.RGB_565)
                    .error(R.drawable.hyx_default)
                    .into(imageView);

//            ImgLoadUtil.display(WebAddress.GETAVATAR+childlistBeanXes.get(position).getPhoto(),imageView);
        }


        return view;
    }

}
