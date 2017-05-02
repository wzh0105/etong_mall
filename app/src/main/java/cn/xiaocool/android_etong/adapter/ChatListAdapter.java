package cn.xiaocool.android_etong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.ChatActivity;
import cn.xiaocool.android_etong.bean.Mine.PersonChat;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 潘 on 2016/9/21.
 */
public class ChatListAdapter extends BaseAdapter {
    private Context context;
    private List<PersonChat> lists;
    private LayoutInflater inflater;

    public ChatListAdapter(Context context, List<PersonChat> lists) {
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
        final PersonChat entity = lists.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chat_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(WebAddress.GETAVATAR + entity.getPhoto(), holder.imgHead);
        holder.tvName.setText(entity.getName());
        holder.tvTime.setText(entity.getChatTime());
        holder.tvContent.setText(entity.getChatMessage());
        holder.rltop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ChatActivity.class);
                intent.putExtra("shop_uid",entity.getchatuid());
                intent.putExtra("shop_photo",entity.getPhoto());
                intent.putExtra("shopname",entity.getName());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_chat_time)
        TextView tvTime;
        @BindView(R.id.rl_top)
        RelativeLayout rltop;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
