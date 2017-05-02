package cn.xiaocool.android_etong.dao;

import cn.xiaocool.android_etong.bean.ActivityRegisterBean;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import cn.xiaocool.android_etong.bean.HomePage.NewArrivalBean;
import cn.xiaocool.android_etong.bean.HttpBean;
import cn.xiaocool.android_etong.bean.json.Ranking;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.MIDDLE;

/**
 * Created by hzh on 2016/12/31.
 */

public interface ApiStores {

    //        @GET("adat/sk/{cityId}.html")
//        Call<CityBBSBean> getWeather(@Path("type") String type);
    //获取e专区bbs列表
    @GET(MIDDLE + "getbbspostlist")
    Call<CityBBSBean> getBBSList(@Query("type") String type,@Query("beginid") String beginid);

    @GET(MIDDLE + "getActivityList")
    Call<ActivityRegisterBean> getActivityRegister(@Query("userid") String userId);

    @GET(MIDDLE + "SetLike")
    Call<HttpBean> SetLike(@Query("userid") String userId, @Query("id")String id, @Query("type")String type);
    @GET(MIDDLE + "ResetLike")
    Call<HttpBean> ResetLike(@Query("userid") String userId, @Query("id")String id,  @Query("type")String type);
    @GET(MIDDLE + "SetComment")
    Call<HttpBean> SetComment(@Query("userid") String userId, @Query("id")String id,   @Query("comment")String comment,@Query("type")String type);
    @GET(MIDDLE + "DeleteBbspost")
    Call<HttpBean> DeleteBbspos(@Query("userid") String userId, @Query("id")String id);
    @GET(MIDDLE + "DeleteComment")
    Call<HttpBean> DeleteComment(@Query("userid") String userId, @Query("id")String id, @Query("type")String type);

    @GET(MIDDLE + "GetTimeGoodList")
    Call<NewArrivalBean> GetTimeGoodList(@Query("type") String type,@Query("beginid") String beginid);

    @GET(MIDDLE + "GetLocalShopList")
    Call<Ranking> GetLocalShopList(@Query("city") String city,@Query("type") String stype,@Query("hottype") String hottype,@Query("beginid")String beginid);
}
