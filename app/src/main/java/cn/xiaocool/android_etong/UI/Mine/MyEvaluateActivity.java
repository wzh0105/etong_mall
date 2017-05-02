package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.UI.Mine.Business.SearchMyOrderActivity;
import cn.xiaocool.android_etong.fragment.MineMyEvaluate.AllOrderFragment;
import cn.xiaocool.android_etong.fragment.MineMyEvaluate.NoCommentFragment;
import cn.xiaocool.android_etong.fragment.MineMyEvaluate.NoConfirmFragment;
import cn.xiaocool.android_etong.fragment.MineMyEvaluate.NoDeliverGoodFragment;
import cn.xiaocool.android_etong.fragment.MineMyEvaluate.NoUseFragment;
import cn.xiaocool.android_etong.fragment.MineMyEvaluate.ObligationFragment;

/**
 * Created by wzh on 2016/8/7.
 */
public class MyEvaluateActivity extends Activity implements View.OnClickListener {
    private Context context;
    private RelativeLayout rl_back;
    private Button[] mTabs;
    private AllOrderFragment allOrderFragment;
    private NoCommentFragment noCommentFragment;
    private NoConfirmFragment noConfirmFragment;
    private NoDeliverGoodFragment noDeliverFragment;
    private NoUseFragment noUseFragment;
    private ObligationFragment obligationFragment;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;
    private int index, currentIndex;
    private int indent;
    private ImageView imgSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_my_order);
        Intent intent = getIntent();
        indent = intent.getIntExtra("indent", 0);
        Log.e("indent is", String.valueOf(indent));
        context = this;
        initData();
        initFragment();
    }

    private void initData() {
        initView();
    }

    private void initView() {
        mTabs = new Button[6];
        mTabs[0] = (Button) findViewById(R.id.mine_order_all);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.mine_order_noPay);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (Button) findViewById(R.id.mine_order_noUse);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (Button) findViewById(R.id.mine_order_noDeliver);
        mTabs[3].setOnClickListener(this);
        mTabs[4] = (Button) findViewById(R.id.mine_order_noReserve);
        mTabs[4].setOnClickListener(this);
        mTabs[5] = (Button) findViewById(R.id.mine_order_noEvaluate);
        mTabs[5].setOnClickListener(this);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.rl_back) {
                    finish();
                }
            }
        });
        imgSearch = (ImageView) findViewById(R.id.myOrder_img_search_order);
        imgSearch.setOnClickListener(this);
    }

    private void initFragment() {
        allOrderFragment = new AllOrderFragment();
        noCommentFragment = new NoCommentFragment();
        noConfirmFragment = new NoConfirmFragment();
        noDeliverFragment = new NoDeliverGoodFragment();
        noUseFragment = new NoUseFragment();
        obligationFragment = new ObligationFragment();
        fragments = new Fragment[]{allOrderFragment, obligationFragment, noUseFragment,
                noDeliverFragment, noConfirmFragment, noCommentFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragments[indent]);
        mTabs[indent].setSelected(true);
        fragmentTransaction.commit();
        currentIndex = indent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_order_all:
                index = 0;
                break;
            case R.id.mine_order_noPay:
                index = 1;
                break;
            case R.id.mine_order_noUse:
                index = 2;
                break;
            case R.id.mine_order_noDeliver:
                index = 3;
                break;
            case R.id.mine_order_noReserve:
                index = 4;
                break;
            case R.id.mine_order_noEvaluate:
                index = 5;
                break;
            case R.id.myOrder_img_search_order:
                Intent intent = new Intent();
                intent.setClass(context,SearchMyOrderActivity.class);
                startActivity(intent);
                break;

        }
        if (currentIndex != index) {
            FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction();
            fragmenttransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                fragmenttransaction.add(R.id.fragment_container, fragments[index]);
            }
            fragmenttransaction.show(fragments[index]);
            fragmenttransaction.commit();
        }

        mTabs[indent].setSelected(false);
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }

}
