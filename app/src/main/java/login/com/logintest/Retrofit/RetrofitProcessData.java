package login.com.logintest.Retrofit;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;


public interface RetrofitProcessData {


    @GET("http://m2.biz.ua/logintest.php")
    Call<String> SignIn(@Query("signin") String signin, @Query("name") String name, @Query("email") String email, @Query("phone") String phone, @Query("spinner") String spinner);

    @GET("http://m2.biz.ua/logintest.php")
    Call<String> LogIn(@Query("login") String login, @Query("name") String name, @Query("email") String email);

    @GET("http://m2.biz.ua/logintest.php")
    Call<String> RestorePassword(@Query("forget") String forget, @Query("email") String email);

    //@GET("http://m2.biz.ua/droid_json.php")
    //Call<List<Flat>> loadShortTermFlats(@Query("short") String shortTerm, @Query("room") String room, @Query("price") String price, @Query("area") String area, @Query("gcm_token") String GCM_token, @Query("order_by") String orderBy);



}
