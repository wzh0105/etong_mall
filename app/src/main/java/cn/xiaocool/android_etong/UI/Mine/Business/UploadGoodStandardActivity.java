package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;

/**
 * Created by wzh on 2016/7/28.
 */
public class UploadGoodStandardActivity extends Activity implements View.OnClickListener {

    private TextView tvTitle;
    private RelativeLayout rlBack;
    private Button[] colorButtons, sizeButtons;
    private Button btnUpload;
    private String goodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.upload_good_standard);
        initView();
        Intent intent = getIntent();
        goodId = intent.getStringExtra("goodId");
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title_text);
        tvTitle.setText("商品规格");
        rlBack = (RelativeLayout) findViewById(R.id.btn_back);
        rlBack.setOnClickListener(this);
        initButtons();
        btnUpload = (Button) findViewById(R.id.upload_standard_btn);
        btnUpload.setOnClickListener(this);
    }

    private void initButtons() {
        colorButtons = new Button[9];
        colorButtons[0] = (Button) findViewById(R.id.upload_standard_color0);
        colorButtons[1] = (Button) findViewById(R.id.upload_standard_color1);
        colorButtons[2] = (Button) findViewById(R.id.upload_standard_color2);
        colorButtons[3] = (Button) findViewById(R.id.upload_standard_color3);
        colorButtons[4] = (Button) findViewById(R.id.upload_standard_color4);
        colorButtons[5] = (Button) findViewById(R.id.upload_standard_color5);
        colorButtons[6] = (Button) findViewById(R.id.upload_standard_color6);
        colorButtons[7] = (Button) findViewById(R.id.upload_standard_color7);
        colorButtons[8] = (Button) findViewById(R.id.upload_standard_color8);
        for (int i = 0; i < 9; i++) {
            colorButtons[i].setOnClickListener(this);
            colorButtons[i].setSelected(false);
        }
        sizeButtons = new Button[9];
        sizeButtons[0] = (Button) findViewById(R.id.upload_standard_size0);
        sizeButtons[1] = (Button) findViewById(R.id.upload_standard_size1);
        sizeButtons[2] = (Button) findViewById(R.id.upload_standard_size2);
        sizeButtons[3] = (Button) findViewById(R.id.upload_standard_size3);
        sizeButtons[4] = (Button) findViewById(R.id.upload_standard_size4);
        sizeButtons[5] = (Button) findViewById(R.id.upload_standard_size5);
        sizeButtons[6] = (Button) findViewById(R.id.upload_standard_size6);
        sizeButtons[7] = (Button) findViewById(R.id.upload_standard_size7);
        sizeButtons[8] = (Button) findViewById(R.id.upload_standard_size8);
        for (int i = 0; i < 9; i++) {
            sizeButtons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.upload_standard_btn:
                int i = 9;
                String[] colorText = new String[i];
                String[] sizeText = new String[i];
                for (i = 0; i < 9; i++) {
                    if (colorButtons[i].isSelected()) {
                        colorText[i] = colorButtons[i].getText().toString();
                    } else {
                        colorText[i] = "";
                    }
                    if (sizeButtons[i].isSelected()) {
                        sizeText[i] = sizeButtons[i].getText().toString();
                    } else {
                        colorText[i] = "";
                    }
                    Log.e("数值1", i + colorText[i]);
                    Log.e("数值2", i + sizeText[i]);
                    Intent intent = new Intent();
                    intent.setClass(this,AddGoodStandardActivity.class);
                    intent.putExtra("goodId",goodId);
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("colorArr",colorText);
                    bundle.putStringArray("sizeArr",sizeText);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.upload_standard_color0:
                if (!colorButtons[0].isSelected()) {
                    colorButtons[0].setSelected(true);
                    colorButtons[0].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[0].setSelected(false);
                    colorButtons[0].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color1:
                if (!colorButtons[1].isSelected()) {
                    colorButtons[1].setSelected(true);
                    colorButtons[1].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[1].setSelected(false);
                    colorButtons[1].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color2:
                if (!colorButtons[2].isSelected()) {
                    colorButtons[2].setSelected(true);
                    colorButtons[2].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[2].setSelected(false);
                    colorButtons[2].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color3:
                if (!colorButtons[3].isSelected()) {
                    colorButtons[3].setSelected(true);
                    colorButtons[3].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[3].setSelected(false);
                    colorButtons[3].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color4:
                if (!colorButtons[4].isSelected()) {
                    colorButtons[4].setSelected(true);
                    colorButtons[4].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[4].setSelected(false);
                    colorButtons[4].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color5:
                if (!colorButtons[5].isSelected()) {
                    colorButtons[5].setSelected(true);
                    colorButtons[5].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[5].setSelected(false);
                    colorButtons[5].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color6:
                if (!colorButtons[6].isSelected()) {
                    colorButtons[6].setSelected(true);
                    colorButtons[6].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[6].setSelected(false);
                    colorButtons[6].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color7:
                if (!colorButtons[7].isSelected()) {
                    colorButtons[7].setSelected(true);
                    colorButtons[7].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[7].setSelected(false);
                    colorButtons[7].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_color8:
                if (!colorButtons[8].isSelected()) {
                    colorButtons[8].setSelected(true);
                    colorButtons[8].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    colorButtons[8].setSelected(false);
                    colorButtons[8].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size0:
                if (!sizeButtons[0].isSelected()) {
                    sizeButtons[0].setSelected(true);
                    sizeButtons[0].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[0].setSelected(false);
                    sizeButtons[0].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size1:
                if (!sizeButtons[1].isSelected()) {
                    sizeButtons[1].setSelected(true);
                    sizeButtons[1].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[1].setSelected(false);
                    sizeButtons[1].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size2:
                if (!sizeButtons[2].isSelected()) {
                    sizeButtons[2].setSelected(true);
                    sizeButtons[2].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[2].setSelected(false);
                    sizeButtons[2].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size3:
                if (!sizeButtons[3].isSelected()) {
                    sizeButtons[3].setSelected(true);
                    sizeButtons[3].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[3].setSelected(false);
                    sizeButtons[3].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size4:
                if (!sizeButtons[4].isSelected()) {
                    sizeButtons[4].setSelected(true);
                    sizeButtons[4].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[4].setSelected(false);
                    sizeButtons[4].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size5:
                if (!sizeButtons[5].isSelected()) {
                    sizeButtons[5].setSelected(true);
                    sizeButtons[5].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[5].setSelected(false);
                    sizeButtons[5].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size6:
                if (!sizeButtons[6].isSelected()) {
                    sizeButtons[6].setSelected(true);
                    sizeButtons[6].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[6].setSelected(false);
                    sizeButtons[6].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size7:
                if (!sizeButtons[7].isSelected()) {
                    sizeButtons[7].setSelected(true);
                    sizeButtons[7].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[7].setSelected(false);
                    sizeButtons[7].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;
            case R.id.upload_standard_size8:
                if (!sizeButtons[8].isSelected()) {
                    sizeButtons[8].setSelected(true);
                    sizeButtons[8].setTextColor(this.getResources().getColor(R.color.whilte));
                } else {
                    sizeButtons[8].setSelected(false);
                    sizeButtons[8].setTextColor(this.getResources().getColor(R.color.black2));
                }
                break;

        }

    }
}
