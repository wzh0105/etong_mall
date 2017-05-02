package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.MyLikeFragment.MyLikeGoodFragment;
import cn.xiaocool.android_etong.fragment.MyLikeFragment.MyLikeShopFragment;

/**
 * Created by wzh on 2016/7/21.
 */
public class MyLikeActivity extends Activity implements View.OnClickListener {
    private MyLikeShopFragment myLikeShopFragment;
    private MyLikeGoodFragment myLikeGoodFragment;
    private LinearLayout llContainer;
    private Fragment[] fragment;
    private FragmentManager fragmentManager;
    private Button[] button;
    private int currentIndex, index;
    private TextView tvTitle;
    private RelativeLayout btnback;
    private Button lineButton0, lineButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_mylike_layout);
        initView();
    }

    private void initView() {
        llContainer = (LinearLayout) findViewById(R.id.mine_order_fragment_container);
        myLikeShopFragment = new MyLikeShopFragment();
        myLikeGoodFragment = new MyLikeGoodFragment();
        fragment = new Fragment[]{myLikeGoodFragment, myLikeShopFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mine_order_fragment_container, myLikeGoodFragment);
        fragmentTransaction.commit();
        button = new Button[2];
        button[0] = (Button) findViewById(R.id.mine_btn_myGood);
        button[1] = (Button) findViewById(R.id.mine_btn_myShop);
        button[0].setOnClickListener(this);
        button[1].setOnClickListener(this);
        lineButton0 = (Button) findViewById(R.id.mine_line_button0);
        lineButton0.setSelected(true);
        lineButton1 = (Button) findViewById(R.id.mine_line_button1);
        btnback = (RelativeLayout) findViewById(R.id.btn_back);
        btnback.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("收藏");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                finish();
                break;
            case R.id.mine_btn_myGood:
                index = 0;
                Log.e("加载第一个", "fragment");
                lineButton0.setSelected(true);
                lineButton1.setSelected(false);
                break;
            case R.id.mine_btn_myShop:
                index = 1;
                Log.e("加载第2个", "fragment");
                lineButton0.setSelected(false);
                lineButton1.setSelected(true);
                break;
        }

        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragment[currentIndex]);
            if (!fragment[index].isAdded()) {
                fragmentTransaction.add(R.id.mine_order_fragment_container, fragment[index]);
            }
            fragmentTransaction.show(fragment[index]);
            fragmentTransaction.commit();
        }
        currentIndex = index;
    }
}
