package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.fragment.business.MyComment.MyCommentFragment;
import cn.xiaocool.android_etong.fragment.business.MyComment.OtherCommentFragment;

/**
 * Created by wzh on 2016/8/13.
 */
public class MyCommentActivity extends Activity implements View.OnClickListener {
    private MyCommentFragment myCommentFragment;
    private OtherCommentFragment otherCommentFragment;
    private Context context;
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
        setContentView(R.layout.my_comment);
        context = this;
        initView();
    }

    private void initView() {
        llContainer = (LinearLayout) findViewById(R.id.my_comment_fragment_content);
        myCommentFragment = new MyCommentFragment();
        otherCommentFragment = new OtherCommentFragment();
        fragment = new Fragment[]{myCommentFragment, otherCommentFragment};
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.my_comment_fragment_content, myCommentFragment);
        fragmentTransaction.commit();
        button = new Button[2];
        button[0] = (Button) findViewById(R.id.my_comment_my);
        button[1] = (Button) findViewById(R.id.my_comment_others);
        button[0].setOnClickListener(this);
        button[1].setOnClickListener(this);
        lineButton0 = (Button) findViewById(R.id.my_comment_line0);
        lineButton0.setSelected(true);
        lineButton1 = (Button) findViewById(R.id.my_comment_line1);
        btnback = (RelativeLayout) findViewById(R.id.btn_back);
        btnback.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("评价");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                finish();
                break;
            case R.id.my_comment_my:
                index = 0;
                lineButton0.setSelected(true);
                lineButton1.setSelected(false);
                break;
            case R.id.my_comment_others:
                index = 1;
                lineButton0.setSelected(false);
                lineButton1.setSelected(true);
                break;
        }

        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragment[currentIndex]);
            if (!fragment[index].isAdded()) {
                fragmentTransaction.add(R.id.my_comment_fragment_content, fragment[index]);
            }
            fragmentTransaction.show(fragment[index]);
            fragmentTransaction.commit();
        }
        currentIndex = index;
    }
}
