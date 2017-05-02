package cn.xiaocool.android_etong.tool;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.android_etong.bean.json.Business;
import cn.xiaocool.android_etong.bean.json.BusinessService;
import cn.xiaocool.android_etong.bean.json.Car;
import cn.xiaocool.android_etong.bean.json.Carowner;
import cn.xiaocool.android_etong.bean.json.DingDan;

/**
 * Created by JustYu on 2015/6/15.
 */
/*[
        {
        "uid": "1",
        "avatar": "",
        "nickname": "yunzhu",
        "sex": "0",
        "birthday": "0000-00-00",
        "phone": "18363866803",
        "score": "10",
        "login": "5",
        "reg_ip": "0",
        "reg_time": "1433750515",
        "last_login_ip": "1020535572",
        "last_login_time": "1433812626",
        "status": "1"
        }
        ]*/
//http://114.215.151.98/Uploads/
public class JsonTool {
    private Context context;
   public JsonTool (Context context){
        this.context = context;
    }
    public static ArrayList<Carowner> getUserInfo(String jsonStr) {
       ArrayList<Carowner> carownerlist=new ArrayList<Carowner>();
        try {
             JSONArray carownerJsonObjectArray= new JSONArray(jsonStr);
            if (null != carownerJsonObjectArray && carownerJsonObjectArray.length()>0) {
                for (int i = 0; i <carownerJsonObjectArray.length(); i++) {
                    Carowner carowner = new Carowner();
                    JSONObject userObject=carownerJsonObjectArray.getJSONObject(i);
                    carowner.setId(userObject.getInt("uid"));
                    carowner.setAvatar(userObject.getString("avatar"));
                    carowner.setNickname(userObject.getString("nickname"));
                    if(userObject.getInt("sex")==0){
                        carowner.setSex("男");
                    }else {
                        carowner.setSex("女");
                    }
                    carowner.setBirthday(userObject.getString("birthday"));
                    carowner.setPhone(userObject.getString("phone"));
                    carowner.setScore(userObject.getInt("score"));
                    carowner.setLogin(userObject.getInt("login"));
                    carowner.setReg_ip(userObject.getInt("reg_ip"));
                    carowner.setReg_time(userObject.getInt("reg_time"));
                    carowner.setLast_login_ip(userObject.getInt("last_login_ip"));
                    carowner.setLast_login_time(userObject.getInt("last_login_time"));
                    carownerlist.add(carowner);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carownerlist;
    }
    //解析 车辆信息的json格式
    public static ArrayList<Car> getCarInfo(String jsonStr){
       ArrayList<Car> carArrayList=new ArrayList<Car>();
        try{
            JSONArray carJsonObjectArray= new JSONArray(jsonStr);
            if (null != carJsonObjectArray && carJsonObjectArray.length()>0) {
                for (int i = 0; i <carJsonObjectArray.length(); i++) {//循环 每个数组
                    Car car = new Car();
                    JSONObject carObject =carJsonObjectArray.getJSONObject(i);
                    car.setUid(carObject.getInt("uid"));
                    car.setBrandid(carObject.getInt("brandid"));
                    car.setLicencenumber(carObject.getString("licencenumber"));
                    carArrayList.add(car);
                      }
                   }
                }catch (Exception e){
            e.printStackTrace();
        }
        return carArrayList;
    }
    //解析 返回的商户json 数据
    public static ArrayList<Business> getBusinessInfo(String jsonStr){
        ArrayList<Business> businessArrayList=new ArrayList<Business>();
        //
        try{
            JSONArray JsonObjectArray=new JSONArray(jsonStr);
            if(null!=JsonObjectArray&&JsonObjectArray.length()>=0){
                for(int i=0;i<JsonObjectArray.length();i++){
                    Business business=new Business();
                    JSONObject businessObject=JsonObjectArray.getJSONObject(i);
                    business.setUid(businessObject.getInt("uid"));
                    business.setPoster(businessObject.getString("poster"));
                    business.setName(businessObject.getString("name"));
                    business.setPhone(businessObject.getInt("phone"));
                    business.setLongitude(businessObject.getInt("longitude"));
                    business.setLatitude(businessObject.getInt("latitude"));
                    business.setAddress(businessObject.getString("address"));
                    business.setDesc(businessObject.getString("abstract"));
                    business.setMbusinesstime(businessObject.getInt("mbusinesstime"));
                    business.setEbusinesstime(businessObject.getInt("ebusinesstime"));
                    JSONArray serviceArray =  businessObject.getJSONArray("servicelist");
                   /*   [{"name":"内部清理"},{"name":"空调清理"},{"name":"换轮胎"}]*/
                    if(null!=serviceArray&&serviceArray.length()>=0){
                    ArrayList<BusinessService> businessServicesList=new ArrayList<BusinessService>();
                        for(int j=0;j<serviceArray.length();j++){
                            BusinessService service=new BusinessService();
                            try {
                                JSONObject serviceObject=serviceArray.getJSONObject(j);
                                service.setShopname(businessObject.getString("name"));
                                service.setName(serviceObject.getString("name"));
                                service.setDescription(serviceObject.getString("description"));
                                service.setMprice(String.valueOf(serviceObject.getInt("mprice")));
                                service.setPrice(String.valueOf(serviceObject.getInt("price")));
                                businessServicesList.add(service);
                                }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        business.setBService(businessServicesList);
                    }
                   businessArrayList.add(business);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return businessArrayList;
    }


    //解析 订单的json格式
    public static ArrayList<DingDan> getDingDanInfo(String jsonStr){
        ArrayList<DingDan> dingDanArrayList=new ArrayList<DingDan>();
        try{
            JSONArray dingDanJsonObjectArray= new JSONArray(jsonStr);
            if (null != dingDanJsonObjectArray && dingDanJsonObjectArray.length()>0) {
                for (int i = 0; i <dingDanJsonObjectArray.length(); i++) {//循环 每个数组
                    DingDan dingDan = new DingDan();


                    JSONObject dingDanObject =dingDanJsonObjectArray.getJSONObject(i);
                    dingDan.setUserId(dingDanObject.getString("uid")+"");
                    dingDan.setServiceid(dingDanObject.getString("serviceid"));
                    dingDan.setCarOwnerid(dingDanObject.getString("carownerid"));
                    dingDan.setShopId(dingDanObject.getString("shopid"));
                    dingDan.setPrice(dingDanObject.getString("price"));
                    dingDan.setOrderstatus(dingDanObject.getString("orderstatus"));
                    dingDan.setTime(dingDanObject.getString("time"));
                    dingDan.setStatus(dingDanObject.getString("status"));
                    dingDanArrayList.add(dingDan);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dingDanArrayList;
    }


}
