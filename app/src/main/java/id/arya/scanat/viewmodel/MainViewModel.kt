package id.arya.scanat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.CheckDataResponse
import id.arya.scanat.model.response.SubmitResponse
import id.arya.scanat.repository.MainRepository

class MainViewModel(private val repository: MainRepository): ViewModel() {

    fun hitCheckData(apiKey: String, params: RequestParams): MutableLiveData<CheckDataResponse> {
        return repository.hitCheckData(apiKey, params)
    }

    fun hitSubmitAsset(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.hitSubmitAsset(apiKey, params)
    }

}