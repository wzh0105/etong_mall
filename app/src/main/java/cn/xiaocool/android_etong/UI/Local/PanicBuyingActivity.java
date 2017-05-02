package cn.xiaocool.android_etong.UI.Local;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.Local.PanicBuying_All_Fragment;
import cn.xiaocool.android_etong.fragment.Local.PanicBuying_Nearly_Fragment;

/**
 * Created by 潘 on 2016/8/8.
 */
public class PanicBuyingActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private PanicBuying_All_Fragment panicBuying_all_fragment;
    private PanicBuying_Nearly_Fragment panicBuying_nearly_fragment;
    private Button[] mTabs;
    private Fragment[] fragments;
    private int index, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_panic_buying);
        initView();
        initFragment();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
    }

    private void initFragment() {
        panicBuying_all_fragment = new PanicBuying_All_Fragment();
        panicBuying_nearly_fragment = new PanicBuying_Nearly_Fragment();
        fragments = new Fragment[]{panicBuying_all_fragment,panicBuying_nearly_fragment};
        getFragmentManager().beginTransaction().add(R.id.fragment_container,panicBuying_all_fragment).show(panicBuying_all_fragment).commit();
        initBtn();
    }

    private void initBtn() {
        mTabs = new Button[2];
        mTabs[0] = (Button) findViewById(R.id.btn_all);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) findViewById(R.id.btn_nearly);
        mTabs[1].setOnClickListener(this);
        mTabs[0].setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_all:
                index = 0;
                break;
            case R.id.btn_nearly:
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
