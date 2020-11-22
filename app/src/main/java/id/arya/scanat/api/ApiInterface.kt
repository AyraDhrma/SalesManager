package id.arya.scanat.api

import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @GET("android_string.json")
    fun getSplashScreen(): Call<SplashScreenResponse>

    @POST("Project/list_project")
    fun getListProject(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<ListProjectResponse>

    @POST("Project/list_document")
    fun getListDocument(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<ListDocumentResponse>

    @POST("Project/list_activity")
    fun getListActivity(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<ListActivityResponse>

    @POST("Content/list_tipe_document")
    fun getListTipeDocument(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<ListTipeDocumentResponse>

    @POST("Auth/login")
    fun hitLogin(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<LoginResponse>

    @POST("Project/add_document")
    fun submitDocument(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<SubmitResponse>

    @POST("Project/add_new_activity")
    fun submitActivity(
        @Header("x-api-key") apiKey: String,
        @Body data: RequestParams
    ): Call<SubmitResponse>

    @POST("Project/update_document")
    fun updateDocument(
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