package com.example.e_lock;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolder {
    @GET("welcome")
    Call<responseWelcome>getwelcome();
    @POST("user/login")
    Call<postlogin>postlogin(@Body postlogin postlogin);
    @POST("user/input")
    Call<postdaftar>postDaftar(@Body postdaftar postdaftar);
    @POST("user/lokasi/input")
    Call<postLokasi>postlokasi(@Body postLokasi postLokasi);

}
