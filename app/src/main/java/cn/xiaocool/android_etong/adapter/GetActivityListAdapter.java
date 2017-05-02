package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.ActivityRegisterNowActivity;
import cn.xiaocool.android_etong.bean.ActivityRegisterBean;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.NetBaseConstant;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.ToastUtils;


/**
 * Created by wzh on 2016/12/31.
 */
public class GetActivityListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private List<ActivityRegisterBean.DataBean> dataBeenList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == CommunalInterfaces.CHECK_ACTIVITY_REGISTER){
                JSONObject jsonObject = (JSONObject) msg.obj;
                try {
                    String status = jsonObject.getString("status");
                    if (status.equals("success")){

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public GetActivityListAdapter(Context context, List<ActivityRegisterBean.DataBean> dataBeenList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataBeenList = dataBeenList;
        this.context = context;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return dataBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final ActivityRegisterBean.DataBean dataBean = dataBeenList.get(position);
//        String picName = dataBean.getPic();
//        String[] arrayPic = picName.split("[,]");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_register_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + dataBean.getThumb(),
                viewHolder.ivPic, displayImageOptions);
        viewHolder.tvTitle.setText(dataBean.getPost_title());
        viewHolder.tvContent.setText(dataBean.getPost_excerpt());
        viewHolder.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.activity_register_btn) {
                    Intent intent = new Intent(context, ActivityRegisterNowActivity.class);
                    intent.putExtra("id",dataBean.getId());
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    private void checkRegisterActivity(String activityId){
        new MainRequest(context,handler).checkRegisterActivity(activityId);
    }

    private class ViewHolder {
        TextView tvTitle, tvContent;
        ImageView ivPic;
        Button btnRegister;

        public ViewHolder(View view) {
            ivPic = (ImageView) view.findViewById(R.id.iv_activity_pic);
            tvTitle = (TextView) view.findViewById(R.id.tv_activity_name);
            tvContent = (TextView) view.findViewById(R.id.tv_activity_content);
            btnRegister = (Button) view.findViewById(R.id.activity_register_btn);
        }

    }
}
