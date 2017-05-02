package cn.xiaocool.android_etong.fragment.Local;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/8/11.
 */
public class RechargeDistenceFragment extends Fragment implements View.OnClickListener {
    private Button[] mTabs;
    private int index, currentIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge_distence, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBtn();
    }

    private void initBtn() {
        mTabs = new Button[6];
        mTabs[0] = (Button) getView().findViewById(R.id.btn_0);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (Button) getView().findViewById(R.id.btn_1);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (Button) getView().findViewById(R.id.btn_2);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (Button) getView().findViewById(R.id.btn_3);
        mTabs[3].setOnClickListener(this);
        mTabs[4] = (Button) getView().findViewById(R.id.btn_4);
        mTabs[4].setOnClickListener(this);
        mTabs[5] = (Button) getView().findViewById(R.id.btn_5);
        mTabs[5].setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_0:
                index = 0;
                break;
            case R.id.btn_1:
                index = 1;
                break;
            case R.id.btn_2:
                index = 2;
                break;
            case R.id.btn_3:
                index = 3;
                break;
            case R.id.btn_4:
                index = 4;
                break;
            case R.id.btn_5:
                index = 5;
                break;
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }

}

