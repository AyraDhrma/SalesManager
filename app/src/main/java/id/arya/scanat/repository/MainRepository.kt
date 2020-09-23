package id.arya.scanat.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import id.arya.scanat.api.ApiService
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.CheckDataResponse
import id.arya.scanat.model.response.SplashScreenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository
@Inject
constructor(url: String) {

    val TAG = MainRepository::class.java.simpleName
    private val apiService = ApiService(url).api()

    fun hitSplashScreen(): MutableLiveData<SplashScreenResponse> {
        val responseApi: MutableLiveData<SplashScreenResponse> = MutableLiveData()

        apiService.getSplashScreen().enqueue(object : Callback<SplashScreenResponse> {
            override fun onFailure(call: Call<SplashScreenResponse>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
                responseApi.postValue(null)
            }

            override fun onResponse(
                call: Call<SplashScreenResponse>,
                response: Response<SplashScreenResponse>
            ) {
                if (response.isSuccessful) {
                    responseApi.postValue(response.body())
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            }

        })
        return responseApi
    }

    fun hitCheckData(
        apiKey: String,
        data: RequestParams
    ): MutableLiveData<CheckDataResponse> {
        val responseApi: MutableLiveData<CheckDataResponse> = MutableLiveData()

        apiService.hitCheckData(apiKey, data)
            .enqueue(object : Callback<CheckDataResponse> {
                override fun onFailure(call: Call<CheckDataResponse>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                    responseApi.postValue(null)
                }

                override fun onResponse(
                    call: Call<CheckDataResponse>,
                    response: Response<CheckDataResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Log.d(TAG, response.body().toString())
                    } else {
                        Log.d(TAG, response.errorBody().toString())
                    }
                }

            })
        return responseApi
    }

//
//    fun hitMenu(apiKey: String, data: RequestParams): MutableLiveData<MenuResponse> {
//        val responseApi: MutableLiveData<MenuResponse> = MutableLiveData()
//
//        apiService.hitApiMenu(apiKey, data).enqueue(object : Callback<MenuResponse> {
//            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
//                Log.d(TAG, t.localizedMessage)
//                responseApi.postValue(null)
//            }
//
//            override fun onResponse(
//                call: Call<MenuResponse>,
//                response: Response<MenuResponse>
//            ) {
//                if (response.isSuccessful) {
//                    responseApi.postValue(response.body())
//                    Log.d(TAG, response.body().toString())
//                } else {
//                    Log.d(TAG, response.errorBody().toString())
//                }
//            }
//
//        })
//        return responseApi
//    }

//    fun hitApiProductList(key: String, data: RequestParams): MutableLiveData<MenuResponse> {
//        val responseApi: MutableLiveData<MenuResponse> = MutableLiveData()
//
//        apiService.hitApiProductList(key, data).enqueue(object : Callback<MenuResponse> {
//            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
//                Log.d(TAG, t.localizedMessage)
//                responseApi.postValue(null)
//            }
//
//            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
//                if (response.isSuccessful) {
//                    responseApi.postValue(response.body())
//                    Log.d(TAG, response.body().toString())
//                } else {
//                    Log.d(TAG, response.errorBody().toString())
//                }
//            }
//        })
//
//        return responseApi
//    }

}

