package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.business.RemoveFragment;
import cn.xiaocool.android_etong.fragment.business.SellFragment;
import cn.xiaocool.android_etong.util.IntentUtils;

/**
 * Created by 潘 on 2016/7/15.
 */
public class GoodsManageActivity extends Activity implements View.OnClickListener , SellFragment.MyListener{
    private RelativeLayout rl_back;
    private String shopid;
    private Button[] mTabs;
    private SellFragment sellFragment;
    private RemoveFragment removeFragment;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;
    private int index, currentIndex;
    private Context context;
    private ProgressDialog progressDialog;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goodsmanage);
        context = this;
        Intent intent =getIntent();
        shopid = intent.getStringExtra("shopid");
        progressDialog = new ProgressDialog(context,AlertDialog.THEME_HOLO_LIGHT);
        initfragment();
        initview();

    }

    private void initview() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        etSearch = (EditText) findViewById(R.id.goods_manage_search);
        etSearch.setOnClickListener(this);
    }

    private void initfragment() {
        //活动向碎片交互
        sellFragment = new SellFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("shopid", shopid);
        sellFragment.setArguments(bundle1);

        removeFragment = new RemoveFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("shopid", shopid);
        removeFragment.setArguments(bundle2);

        fragments = new Fragment[]{sellFragment,removeFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,removeFragment);
        fragmentTransaction.add(R.id.fragment_container, sellFragment);
        fragmentTransaction.hide(removeFragment);
        fragmentTransaction.commit();
        initbtn();
    }

    private void initbtn() {
        mTabs = new Button[2];
        mTabs[0] = (Button)findViewById(R.id.btn_sell);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button)findViewById(R.id.btn_remove);
        mTabs[1].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sell:
                index = 0;
                break;
            case R.id.btn_remove:
                index = 1;
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.goods_manage_search:
                IntentUtils.getIntent(this,GoodsManageSearchActivity.class);
                break;
        }
        if (currentIndex!=index){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragments[currentIndex]);
            if(!fragments[index].isAdded()){
                fragmentTransaction.add(R.id.fragment_container,fragments[index]);
            }
            fragmentTransaction.show(fragments[index]);
            fragmentTransaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }


    @Override
    public void showMessage(int index) {
        if (index==1){
          removeFragment.updataUI();
        }
        if (index==2){
          sellFragment.updataUI();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMessage(1);
        showMessage(2);
    }

}
