package id.arya.scanat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.*
import id.arya.scanat.repository.MainRepository

class MainViewModel(private val repository: MainRepository): ViewModel() {

    fun getListProject(
        apiKey: String,
        params: RequestParams
    ): MutableLiveData<ListProjectResponse> {
        return repository.getListProject(apiKey, params)
    }

    fun getListDocument(
        apiKey: String,
        params: RequestParams
    ): MutableLiveData<ListDocumentResponse> {
        return repository.getListDocument(apiKey, params)
    }

    fun getListTipeDocument(
        apiKey: String,
        params: RequestParams
    ): MutableLiveData<ListTipeDocumentResponse> {
        return repository.getListTipeDocument(apiKey, params)
    }

    fun getListActivity(
        apiKey: String,
        params: RequestParams
    ): MutableLiveData<ListActivityResponse> {
        return repository.getListActivity(apiKey, params)
    }

    fun getListProduct(
        apiKey: String,
        params: RequestParams
    ): MutableLiveData<ListProductResponse> {
        return repository.getListProduct(apiKey, params)
    }

    fun submitDocument(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.submitDocument(apiKey, params)
    }

    fun submitActivity(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.submitActivity(apiKey, params)
    }

    fun updateDocument(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.updateDocument(apiKey, params)
    }

    fun submitFFID(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.hitSaveFirebaseId(apiKey, params)
    }

}