package cn.xiaocool.android_etong.UI.Mine.Business.OrderDetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.adapter.LabelAdapter;
import cn.xiaocool.android_etong.bean.Shop.Property;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/8/20.
 */
public class UploadGoodsPropertyLabelActivity extends Activity implements View.OnClickListener {

    private Context context;
    private RelativeLayout rl_back;
    private TextView tv_name;
    private Button btn_comfirm;
    private Property.DataBean dataBean;
    private GridView grid_label;
    private LabelAdapter labelAdapter;
    private List<Boolean> select;
    private List<String> propertylist;
    private String goodsid , type;
    private String list = null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommunalInterfaces.AddGoodsProperty:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")){
                            Log.e("success","addsuccess");
                            Toast.makeText(context,"已添加，请选择下一属性",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload_goods_property_label);
        context = this;
        dataBean = (Property.DataBean) getIntent().getSerializableExtra("object");
        goodsid = getIntent().getStringExtra("goodsid");
        type = getIntent().getStringExtra("type");
        Log.e("name",dataBean.getName());
        Log.e("goodsid",goodsid);
        Log.e("type",type);
        initView();
    }

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(dataBean.getName());
        btn_comfirm = (Button) findViewById(R.id.btn_comfirm);
        btn_comfirm .setOnClickListener(this);
        grid_label = (GridView) findViewById(R.id.grid_label);
        labelAdapter = new LabelAdapter(UploadGoodsPropertyLabelActivity.this,dataBean.getPlist());
        grid_label.setAdapter(labelAdapter);
        select = new ArrayList<>();
        propertylist = new ArrayList<>();
        for (int i = 0 ; i<dataBean.getPlist().size();i++){
            select.add(false);
        }
    }

    public void setSelect(int position , Boolean judeg){
        select.set(position,judeg);
    }

    public void updata(){
        for (int j = 0 ; j<select.size();j++){
            if (select.get(j)){
                propertylist.add(dataBean.getPlist().get(j).getId());
            }
        }
        Log.e("propertylist", propertylist.toString());
        list = "";
        for (int n = 0 ; n<propertylist.size();n++){
            if (n==0){
                list = propertylist.get(n).toString();
            }else {
                list = list+","+propertylist.get(n).toString();
            }
        }
        Log.e("list",list.toString());
        Log.e("type=",dataBean.getId());
        if (NetUtil.isConnnected(context)){
            new MainRequest(context,handler).AddGoodsProperty(goodsid,dataBean.getId(),list.toString());
        }else {
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
        propertylist.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_comfirm:
                updata();
                break;
        }
    }

}
