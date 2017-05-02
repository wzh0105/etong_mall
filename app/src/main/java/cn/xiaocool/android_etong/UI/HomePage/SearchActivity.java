package cn.xiaocool.android_etong.UI.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/8/13.
 */
public class SearchActivity extends Activity implements View.OnClickListener {
    private Spinner spinner_search;
    private Context context;
    private TextView tv_back;
    private EditText et_search;
    private String city;
    ArrayAdapter<String> adapter01;
    private String show = "宝贝";
    private String[] type = new String[]{"宝贝","店铺"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        context = this;
        initView();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        spinner_search = (Spinner) findViewById(R.id.spinner_search);
        adapter01 = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,type);
        spinner_search.setAdapter(adapter01);
        spinner_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show = spinner_search.getSelectedItem().toString();
                Log.e("show=", show);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(et_search.getText().toString())){
                        if (show.equals("宝贝")){
                            Intent intent1 = new Intent();
                            intent1.putExtra("search_content",et_search.getText().toString());
                            intent1.putExtra("city",city);
                            intent1.setClass(context, SearchResultGoodsActivity.class);
                            startActivity(intent1);
                            finish();
                        }else {
                            Intent intent = new Intent();
                            intent.putExtra("search_content",et_search.getText().toString());
                            intent.putExtra("city",city);
                            intent.setClass(context, SearchResultShopActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Log.e("无内容","无内容");
                    }
                    //TODO
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                if (!TextUtils.isEmpty(et_search.getText().toString())){
                    if (show.equals("宝贝")){
                        Intent intent1 = new Intent();
                        intent1.putExtra("search_content",et_search.getText().toString());
                        intent1.putExtra("city",city);
                        intent1.setClass(context, SearchResultGoodsActivity.class);
                        startActivity(intent1);
                        finish();
                    }else {
                        Intent intent = new Intent();
                        intent.putExtra("search_content",et_search.getText().toString());
                        intent.putExtra("city",city);
                        intent.setClass(context, SearchResultShopActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Log.e("无内容","无内容");
                }
                break;
        }
    }

}
