package id.arya.scanat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.LoginResponse
import id.arya.scanat.repository.MainRepository

class LoginViewModel(private val repository: MainRepository) : ViewModel() {

    fun hitLoginApi(key: String, data: RequestParams): MutableLiveData<LoginResponse> {
        return repository.hitLogin(key, data)
    }

}