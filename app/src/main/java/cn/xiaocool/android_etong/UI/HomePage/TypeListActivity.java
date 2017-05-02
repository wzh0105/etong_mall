package cn.xiaocool.android_etong.UI.HomePage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.HomepageListThreeAdapter;
import cn.xiaocool.android_etong.bean.HomePage.HomeList3Bean;
import cn.xiaocool.android_etong.net.constant.request.HomeRequest;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;

/**
 * Created by wzh on 2016/8/15.
 */
public class TypeListActivity extends Activity implements View.OnClickListener {

    private String[] mMenus;
    private String[] mMenus3;
    private Context context;
    private ListView mListView1;
    private GridView mListView2;
    private RelativeLayout rlBack;
    private HomepageListThreeAdapter homepageListThreeAdapter;
    private List<HomeList3Bean.DataBean> dataBeanList;
    private EditText etSearch;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.GET_MENU:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        if (jsonObject.getString("status").equals("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.e("data get", String.valueOf(jsonArray));
                            int length = jsonArray.length();
                            mMenus = new String[length];
                            JSONObject jsonObject1;
                            for (int i = 0; i < length; i++) {
                                jsonObject1 = (JSONObject) jsonArray.get(i);
                                mMenus[i] = jsonObject1.getString("leveltwo_name");
                            }

                            mListView1.setAdapter(new ArrayAdapter<String>(context,
                                    R.layout.homepage_list_two_item, mMenus));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case CommunalInterfaces.GET_MENU3:
                    JSONObject jsonObject1 = (JSONObject) msg.obj;
                    dataBeanList.clear();
                    try {
                        if (jsonObject1.getString("status").equals("success")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            int length = jsonArray.length();
                            mMenus3 = new String[length];
                            JSONObject jsonObject2;
                            for (int i = 0; i < length; i++) {
                                jsonObject2 = (JSONObject) jsonArray.get(i);
                                HomeList3Bean.DataBean dataBean = new HomeList3Bean.DataBean();
                                dataBean.setLevelthree_name(jsonObject2.getString("levelthree_name"));
                                dataBean.setPic(jsonObject2.getString("pic"));
                                dataBeanList.add(dataBean);
//                                mMenus3[i] = jsonObject2.getString("levelthree_name");
                            }
                            homepageListThreeAdapter = new HomepageListThreeAdapter(context, dataBeanList);

                            mListView2.setAdapter(homepageListThreeAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrap_listview);
        context = this;
        String itemName = getIntent().getStringExtra("strItem");
        Log.e("itemname=", itemName);
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        mListView1 = (ListView) findViewById(R.id.homepage_wrap_list0);
        mListView2 = (GridView) findViewById(R.id.homepage_wrap_list1);
        etSearch = (EditText) findViewById(R.id.homepage_search_et);
        etSearch.setText(itemName);
        etSearch.setSelection(itemName.length());//光标置后
        dataBeanList = new ArrayList<>();
        new HomeRequest(context, handler).getMenu("&levelone_name=", itemName);

//        mListView1.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, mMenus));

        mListView1.setOnItemClickListener(new ItemClick());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private class ItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            mListView1.smoothScrollToPositionFromTop(position, 0);
            String itemName2 = mMenus[position];
            new HomeRequest(context, handler).getMenu3(itemName2);


//            String[] items = new String[(position + 1) * 3];
//            for (int i = 0; i < items.length; i++) {
//                items[i] = mMenus[position] + "中的数据：" + i;
//            }
//            mListView2.setAdapter(new ArrayAdapter<String>(TypeListActivity.this, android.R.layout.simple_list_item_1, items));
        }
    }
}
