package id.arya.scanat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.ListDocumentResponse
import id.arya.scanat.model.response.ListProjectResponse
import id.arya.scanat.model.response.ListTipeDocumentResponse
import id.arya.scanat.model.response.SubmitResponse
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

    fun submitDocument(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.submitDocument(apiKey, params)
    }

    fun submitActivity(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.submitActivity(apiKey, params)
    }

    fun updateDocument(apiKey: String, params: RequestParams): MutableLiveData<SubmitResponse> {
        return repository.updateDocument(apiKey, params)
    }

}