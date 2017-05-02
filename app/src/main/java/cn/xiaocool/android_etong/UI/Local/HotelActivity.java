package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.Local.Hotel_Distence_Fragment;
import cn.xiaocool.android_etong.fragment.Local.Hotel_Nearly_Fragment;

/**
 * Created by 潘 on 2016/8/7.
 */
public class HotelActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private Context context;
    private String city;
    private Button[] mTabs;
    private Hotel_Nearly_Fragment hotel_nearly_fragment;
    private Hotel_Distence_Fragment hotel_distence_fragment;
    private Fragment[] fragments;
    private int index, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hotel_activity);
        context = this;
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        initView();
        initFragment();
    }

    private void initView() {
        rl_back = (RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    private void initFragment() {
        hotel_distence_fragment = new Hotel_Distence_Fragment();
        hotel_nearly_fragment = new Hotel_Nearly_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("city",city);
        hotel_nearly_fragment.setArguments(bundle);
        fragments = new Fragment[]{hotel_nearly_fragment,hotel_distence_fragment};
        getFragmentManager().beginTransaction().add(R.id.fragment_container,hotel_nearly_fragment).show(hotel_nearly_fragment).commit();
        initBtn();
    }

    private void initBtn() {
        mTabs = new Button[2];
        mTabs[0] = (Button) findViewById(R.id.btn_nearly);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_distence);
        mTabs[1].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_nearly:
                index = 0;
                break;
            case R.id.btn_distence:
                index = 1;
                break;
        }
        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, fragments[index]);
            }
            fragmentTransaction.show(fragments[index]);
            fragmentTransaction.commit();
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }
}
