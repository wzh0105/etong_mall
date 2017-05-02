package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.ChatAdapter;
import cn.xiaocool.android_etong.bean.Mine.PersonChat;
import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.fragment.PrefectureMyFragment;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

import static cn.xiaocool.android_etong.util.TimeToolUtils.fromateTimeShowByRule;

/**
 * Created by 潘 on 2016/9/13.
 */
public class ChatActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private Context context;
    private String shopid,shop_uid,shop_photo,shopname;
    private ChatAdapter chatAdapter;
    private TextView tv_receive_name;
    private EditText et_chat_message;
    private Button btn_chat_message_send;
    private UserInfo user;
    private int beginid=0;
    private PullToRefreshScrollView scroll;

    /**
     * 声明ListView
     */
    private ListView lv_chat_dialog;
    /**
     * 集合
     */
    private ArrayList<PersonChat> personChats = new ArrayList<PersonChat>();
    private ArrayList<PersonChat> personChats2 = new ArrayList<PersonChat>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 0x12345:
                    chatAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    lv_chat_dialog.setSelection(personChats.size());
                    break;
                case CommunalInterfaces.SendChatData:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            PersonChat personChat = new PersonChat();
                            Log.e("success", "chat");
                            JSONObject object = json.getJSONObject("data");
                            personChat.setChatTime(fromateTimeShowByRule(object.getLong("create_time")));
                            personChat.setPhoto(user.getUserImg());
                            //代表自己发送
                            personChat.setMeSend(true);
                            //得到发送内容
                            personChat.setChatMessage(et_chat_message.getText().toString());
                            //加入集合
                            personChats.add(personChat);
                            //清空输入框
                            et_chat_message.setText("");
                            //刷新ListView
                            chatAdapter.notifyDataSetChanged();
                            handler.sendEmptyMessage(1);
                        } else {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.xcGetChatData:
                    JSONObject json1 = (JSONObject) msg.obj;
                    try {
                        String status = json1.getString("status");
                        String data = json1.getString("data");
                        if (status.equals("success")) {
                            personChats.clear();
                            Log.e("success", "chat");
                            JSONArray object = json1.getJSONArray("data");
                            for (int i = 0 ;i<object.length();i++){
                                JSONObject jsonobj = object.getJSONObject(i);
                                if (jsonobj.getString("send_uid").equals(user.getUserId())){
                                    PersonChat personChat = new PersonChat();
                                    personChat.setId(Integer.parseInt(jsonobj.getString("id")));
                                    personChat.setChatTime(fromateTimeShowByRule(jsonobj.getLong("create_time")));
                                    personChat.setPhoto(user.getUserImg());
                                    //代表自己发送
                                    personChat.setMeSend(true);
                                    //得到发送内容
                                    personChat.setChatMessage(jsonobj.getString("content"));
                                    tv_receive_name.setText(jsonobj.getString("receive_nickname"));
                                    //加入集合
                                    personChats.add(personChat);
                                }else {
                                    PersonChat personChat = new PersonChat();
                                    personChat.setId(Integer.parseInt(jsonobj.getString("id")));
                                    personChat.setChatTime(fromateTimeShowByRule(jsonobj.getLong("create_time")));
                                    personChat.setPhoto(jsonobj.getString("receive_face"));
                                    //代表自己发送
                                    personChat.setMeSend(false);
                                    //得到发送内容
                                    personChat.setChatMessage(jsonobj.getString("content"));
                                    //加入集合
                                    personChats.add(personChat);
                                }
                            }
                            if (personChats.get(0)!=null){
                                beginid = personChats.get(0).getId();
                                Log.e("begid=", String.valueOf(beginid));
                            }

                            /**
                             *setAdapter
                             */
                            setAdapter();

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        context = this;
        user = new UserInfo();
        user.readData(this);
        Intent intent = getIntent();
//        shopid = intent.getStringExtra("shopid");
        shop_uid = intent.getStringExtra("shop_uid");
        Log.e("uid=",shop_uid);
//        shop_photo = intent.getStringExtra("shop_photo");
        shopname = intent.getStringExtra("shopname");
        initView();
        if (NetUtil.isConnnected(context)) {
            new MainRequest(context, handler).xcGetChatData(shop_uid, String.valueOf(beginid));
        } else {
            Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
        initChat();
        setrefrseh();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_receive_name = (TextView) findViewById(R.id.tv_receive_name);
        lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);
        btn_chat_message_send = (Button) findViewById(R.id.btn_chat_message_send);
        et_chat_message = (EditText) findViewById(R.id.et_chat_message);
        scroll = (PullToRefreshScrollView) findViewById(R.id.scroll);
    }
    private void initChat() {

        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Aut-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString().trim())) {
                    Toast.makeText(context, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (NetUtil.isConnnected(context)) {
                    new MainRequest(context, handler).SendChatData(shop_uid, et_chat_message.getText().toString());
                } else {
                    Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setAdapter(){
        for (int i = personChats.size()-1;i>0;i--){
            personChats2.add(0,personChats.get(i));
        }
        if (chatAdapter!=null){
            chatAdapter.notifyDataSetChanged();
        }else {
            chatAdapter = new ChatAdapter(context, personChats2);
            lv_chat_dialog.setAdapter(chatAdapter);
            setListViewHeightBasedOnChildren(lv_chat_dialog);
            lv_chat_dialog.setSelection(personChats.size());
        }
        return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }


    //这是格式化时间的
    public static String getStrTime(long cc_time){
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        //例如 :cc_time = 1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time*1000L));
        return re_StrTime;
    }



    class Reciver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void setrefrseh() {
        //设置可上拉刷新和下拉刷新
        scroll.setMode(PullToRefreshBase.Mode.BOTH);

        //设置刷新时显示的文本
        ILoadingLayout startLayout = scroll.getLoadingLayoutProxy(true, false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");

        ILoadingLayout endLayout = scroll.getLoadingLayoutProxy(false, true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");


        scroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new LoadDataAsyncTask(context, 1).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Log.e("refesh","refesh");
                new LoadDataAsyncTask(context, 2).execute();
            }
        });

    }

    /**
     * 异步下载任务
     */
    private class LoadDataAsyncTask extends AsyncTask<Void, Void, String> {

        private Context mainActivity;
        private int judge;

        public LoadDataAsyncTask(Context mainActivity, int judge) {
            this.mainActivity = mainActivity;
            this.judge = judge;
        }

        @Override
        protected String doInBackground(Void... params){
            try {
                if (judge==1) {
                    if (NetUtil.isConnnected(context)) {
                        new MainRequest(context, handler).xcGetChatData(shop_uid, String.valueOf(beginid));
                    } else {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    handler.sendEmptyMessage(0X12345);
                }
                Log.e("refesh","refesh2");
                Thread.sleep(2000);
                return "seccess";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;

        }

        /**
         * 完成时的方法
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("seccess")) {
                Log.e("refesh","refesh3");
                scroll.onRefreshComplete();//刷新完成
                return;
            }
        }
    }

}
