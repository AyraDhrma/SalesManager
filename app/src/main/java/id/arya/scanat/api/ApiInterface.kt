package id.arya.scanat.api

import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.CheckDataResponse
import id.arya.scanat.model.response.LoginResponse
import id.arya.scanat.model.response.SplashScreenResponse
import id.arya.scanat.model.response.SubmitResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @GET("android_string.json")
    fun getSplashScreen(): Call<SplashScreenResponse>

    @POST("Assets/checkdata")
    fun hitCheckData(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<CheckDataResponse>

    @POST("Auth/login")
    fun hitLogin(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<LoginResponse>

    @POST("Assets/insert_assets")
    fun hitSubmitAsset(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<SubmitResponse>

//
//    @POST("menu/content_menu")
//    fun hitApiMenu(
//        @Header("x-api-key") apiKey: String,
//        @Body data: RequestParams
//    ): Call<MenuResponse>
//
//    @POST("menu/list_menu")
//    fun hitApiProductList(
//        @Header("x-api-key") apiKey: String,
//        @Body data: RequestParams
//    ): Call<MenuResponse>

}