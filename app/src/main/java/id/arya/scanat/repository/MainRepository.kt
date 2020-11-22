package id.arya.scanat.repository

import androidx.lifecycle.MutableLiveData
import id.arya.scanat.api.ApiService
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
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
                Timber.d(TAG, t.localizedMessage)
                responseApi.postValue(null)
            }

            override fun onResponse(
                call: Call<SplashScreenResponse>,
                response: Response<SplashScreenResponse>
            ) {
                if (response.isSuccessful) {
                    responseApi.postValue(response.body())
                    Timber.d(TAG, response.body().toString())
                } else {
                    responseApi.postValue(null)
                    Timber.d(TAG, response.errorBody().toString())
                }
            }

        })
        return responseApi
    }

    fun getListProject(
        apiKey: String,
        data: RequestParams
    ): MutableLiveData<ListProjectResponse> {
        val responseApi: MutableLiveData<ListProjectResponse> = MutableLiveData()

        apiService.getListProject(apiKey, data)
            .enqueue(object : Callback<ListProjectResponse> {
                override fun onFailure(call: Call<ListProjectResponse>, t: Throwable) {
                    val data = arrayListOf<ListProjectResponse.Data>()
                    val listProjectResponse = ListProjectResponse(
                        "error", t.localizedMessage,
                        t.localizedMessage, data
                    )
                    responseApi.postValue(listProjectResponse)
                }

                override fun onResponse(
                    call: Call<ListProjectResponse>,
                    response: Response<ListProjectResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val data = arrayListOf<ListProjectResponse.Data>()
                        val listProjectResponse = ListProjectResponse(
                            "error", response.errorBody().toString(),
                            response.errorBody().toString(), data
                        )
                        responseApi.postValue(listProjectResponse)
                        Timber.d(TAG, response.errorBody().toString())
                    }
                }

            })
        return responseApi
    }

    fun getListDocument(
        apiKey: String,
        data: RequestParams
    ): MutableLiveData<ListDocumentResponse> {
        val responseApi: MutableLiveData<ListDocumentResponse> = MutableLiveData()

        apiService.getListDocument(apiKey, data)
            .enqueue(object : Callback<ListDocumentResponse> {
                override fun onFailure(call: Call<ListDocumentResponse>, t: Throwable) {
                    val data = arrayListOf<ListDocumentResponse.Data>()
                    val listProjectResponse = ListDocumentResponse(
                        "error", t.localizedMessage,
                        t.localizedMessage, data
                    )
                    responseApi.postValue(listProjectResponse)
                }

                override fun onResponse(
                    call: Call<ListDocumentResponse>,
                    response: Response<ListDocumentResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val data = arrayListOf<ListDocumentResponse.Data>()
                        val listProjectResponse = ListDocumentResponse(
                            "error", response.errorBody().toString(),
                            response.errorBody().toString(), data
                        )
                        responseApi.postValue(listProjectResponse)
                        Timber.d(TAG, response.errorBody().toString())
                    }
                }

            })
        return responseApi
    }

    fun getListActivity(
        apiKey: String,
        data: RequestParams
    ): MutableLiveData<ListActivityResponse> {
        val responseApi: MutableLiveData<ListActivityResponse> = MutableLiveData()

        apiService.getListActivity(apiKey, data)
            .enqueue(object : Callback<ListActivityResponse> {
                override fun onFailure(call: Call<ListActivityResponse>, t: Throwable) {
                    val data = arrayListOf<ListActivityResponse.Data>()
                    val listProjectResponse = ListActivityResponse(
                        "error", t.localizedMessage,
                        t.localizedMessage, data
                    )
                    responseApi.postValue(listProjectResponse)
                }

                override fun onResponse(
                    call: Call<ListActivityResponse>,
                    response: Response<ListActivityResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val data = arrayListOf<ListActivityResponse.Data>()
                        val listProjectResponse = ListActivityResponse(
                            "error", response.errorBody().toString(),
                            response.errorBody().toString(), data
                        )
                        responseApi.postValue(listProjectResponse)
                        Timber.d(TAG, response.errorBody().toString())
                    }
                }

            })
        return responseApi
    }

    fun getListTipeDocument(
        apiKey: String,
        data: RequestParams
    ): MutableLiveData<ListTipeDocumentResponse> {
        val responseApi: MutableLiveData<ListTipeDocumentResponse> = MutableLiveData()

        apiService.getListTipeDocument(apiKey, data)
            .enqueue(object : Callback<ListTipeDocumentResponse> {
                override fun onFailure(call: Call<ListTipeDocumentResponse>, t: Throwable) {
                    val data = arrayListOf<ListTipeDocumentResponse.Data>()
                    val listProjectResponse = ListTipeDocumentResponse(
                        "error", t.localizedMessage,
                        t.localizedMessage, data
                    )
                    responseApi.postValue(listProjectResponse)
                }

                override fun onResponse(
                    call: Call<ListTipeDocumentResponse>,
                    response: Response<ListTipeDocumentResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val data = arrayListOf<ListTipeDocumentResponse.Data>()
                        val listProjectResponse = ListTipeDocumentResponse(
                            "error", response.errorBody().toString(),
                            response.errorBody().toString(), data
                        )
                        responseApi.postValue(listProjectResponse)
                        Timber.d(TAG, response.errorBody().toString())
                    }
                }

            })
        return responseApi
    }

    fun hitLogin(
        apiKey: String,
        data: RequestParams
    ): MutableLiveData<LoginResponse> {
        val responseApi: MutableLiveData<LoginResponse> = MutableLiveData()

        apiService.hitLogin(apiKey, data)
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Timber.d(TAG, t.localizedMessage)
                    val data = LoginResponse.Data(
                        "", "", "", "",
                        "", "", ""
                    )
                    val loginResponse = LoginResponse(
                        "error", t.localizedMessage,
                        t.localizedMessage, data
                    )
                    responseApi.postValue(loginResponse)
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val data = LoginResponse.Data(
                            "", "", "", "",
                            "", "", ""
                        )
                        val loginResponse = LoginResponse(
                            "error", response.errorBody().toString(),
                            response.errorBody().toString(), data
                        )
                        responseApi.postValue(loginResponse)
                        Timber.d(TAG, response.errorBody().toString())
                    }
                }

            })
        return responseApi
    }

    fun submitDocument(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        val responseApi: MutableLiveData<SubmitResponse> = MutableLiveData()

        apiService.submitDocument(apiKey, params)
            .enqueue(object : Callback<SubmitResponse> {
                override fun onFailure(call: Call<SubmitResponse>, t: Throwable) {
                    Timber.d(TAG, t.localizedMessage)
                    val submitResponse = SubmitResponse("", "", t.localizedMessage)
                    responseApi.postValue(submitResponse)
                }

                override fun onResponse(
                    call: Call<SubmitResponse>,
                    response: Response<SubmitResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val submitResponse = SubmitResponse("", "", response.errorBody().toString())
                        responseApi.postValue(submitResponse)
                    }
                }

            })
        return responseApi
    }

    fun submitActivity(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        val responseApi: MutableLiveData<SubmitResponse> = MutableLiveData()

        apiService.submitActivity(apiKey, params)
            .enqueue(object : Callback<SubmitResponse> {
                override fun onFailure(call: Call<SubmitResponse>, t: Throwable) {
                    Timber.d(TAG, t.localizedMessage)
                    val submitResponse = SubmitResponse("", "", t.localizedMessage)
                    responseApi.postValue(submitResponse)
                }

                override fun onResponse(
                    call: Call<SubmitResponse>,
                    response: Response<SubmitResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val submitResponse = SubmitResponse("", "", response.errorBody().toString())
                        responseApi.postValue(submitResponse)
                    }
                }

            })
        return responseApi
    }

    fun updateDocument(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        val responseApi: MutableLiveData<SubmitResponse> = MutableLiveData()

        apiService.updateDocument(apiKey, params)
            .enqueue(object : Callback<SubmitResponse> {
                override fun onFailure(call: Call<SubmitResponse>, t: Throwable) {
                    Timber.d(TAG, t.localizedMessage)
                    val submitResponse = SubmitResponse("", "", t.localizedMessage)
                    responseApi.postValue(submitResponse)
                }

                override fun onResponse(
                    call: Call<SubmitResponse>,
                    response: Response<SubmitResponse>
                ) {
                    if (response.isSuccessful) {
                        responseApi.postValue(response.body())
                        Timber.d(TAG, response.body().toString())
                    } else {
                        val submitResponse = SubmitResponse("", "", response.errorBody().toString())
                        responseApi.postValue(submitResponse)
                    }
                }

            })
        return responseApi
    }

}

