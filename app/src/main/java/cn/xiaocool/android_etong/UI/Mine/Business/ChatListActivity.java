package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.ChatListAdapter;
import cn.xiaocool.android_etong.bean.Mine.PersonChat;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/9/21.
 */
public class ChatListActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private ChatListAdapter chatListAdapter;
    private UserInfo user;
    /**
     * 声明ListView
     */
    private ListView lv_chat_dialog;
    /**
     * 集合
     */
    private List<PersonChat> personChats = new ArrayList<PersonChat>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case CommunalInterfaces.xcGetChatListData:
                    personChats.clear();
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            Log.e("success", "chat");
                            JSONArray jsonarry = json.getJSONArray("data");
                            for (int i = 0;i<jsonarry.length();i++){
                                JSONObject jsonobj = jsonarry.getJSONObject(i);
                                PersonChat personChat = new PersonChat();
                                personChat.setChatMessage(jsonobj.getString("last_content"));
                                personChat.setChatTime(jsonobj.getString("create_time"));
                                personChat.setchatuid(jsonobj.getString("chat_uid"));
                                personChat.setPhoto(jsonobj.getString("other_face"));
                                personChat.setName(jsonobj.getString("other_nickname"));
                                personChats.add(personChat);
                            }
                            /**
                             *setAdapter
                             */
                            chatListAdapter = new ChatListAdapter(context, personChats);
                            lv_chat_dialog.setAdapter(chatListAdapter);
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        context = this;
        user = new UserInfo();
        user.readData(context);
        initView();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).xcGetChatListData();
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);
        personChats = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public static String getStrTime(String cc_time){
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        //例如 :cc_time = 1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time*1000L));
        return re_StrTime;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).xcGetChatListData();
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }
}
