package id.arya.scanat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.SplashScreenResponse
import id.arya.scanat.model.response.SubmitResponse
import id.arya.scanat.repository.MainRepository

class SplashViewModel(private val repository: MainRepository) : ViewModel() {
    fun hitSplashScreen(): MutableLiveData<SplashScreenResponse> {
        return repository.hitSplashScreen()
    }

    fun submitFFID(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.hitSaveFirebaseId(apiKey, params)
    }

}