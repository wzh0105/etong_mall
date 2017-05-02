package cn.xiaocool.android_etong.UI.Mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.xiaocool.android_etong.R;

/**
 * Created by 潘 on 2016/11/19.
 */

public class SelectBankActivity extends Activity implements View.OnClickListener {

    private Context context;
    private RelativeLayout rl_back;
    private TextView mTabs[],tv_sure;
    private boolean isSelect;
    private int index, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_bank);
        context = this;
        initView();
        initText();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
    }

    private void initText() {
        mTabs = new TextView[7];
        mTabs[0] = (TextView) findViewById(R.id.tv0);
        mTabs[0].setOnClickListener(this);
        mTabs[1] = (TextView) findViewById(R.id.tv1);
        mTabs[1].setOnClickListener(this);
        mTabs[2] = (TextView) findViewById(R.id.tv2);
        mTabs[2].setOnClickListener(this);
        mTabs[3] = (TextView) findViewById(R.id.tv3);
        mTabs[3].setOnClickListener(this);
        mTabs[4] = (TextView) findViewById(R.id.tv4);
        mTabs[4].setOnClickListener(this);
        mTabs[5] = (TextView) findViewById(R.id.tv5);
        mTabs[5].setOnClickListener(this);
        mTabs[6] = (TextView) findViewById(R.id.tv6);
        mTabs[6].setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sure:
                if (isSelect){
                    for (int i = 0;i<mTabs.length;i++){
                        if (mTabs[i].isSelected()){
                            Intent intent = new Intent();
                            intent.putExtra("bankname",mTabs[i].getText().toString());
                            setResult(1000,intent);
                            finish();
                            break;
                        }
                    }
                }else {
                    Toast.makeText(context,"您未选中银行",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv0:
                index = 0;
                isSelect = true;
                break;
            case R.id.tv1:
                index = 1;
                isSelect = true;
                break;
            case R.id.tv2:
                index = 2;
                isSelect = true;
                break;
            case R.id.tv3:
                index = 3;
                isSelect = true;
                break;
            case R.id.tv4:
                index = 4;
                isSelect = true;
                break;
            case R.id.tv5:
                index = 5;
                isSelect = true;
                break;
            case R.id.tv6:
                index = 6;
                isSelect = true;
                break;
        }
        mTabs[currentIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentIndex = index;
    }

}
