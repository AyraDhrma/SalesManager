@file:Suppress("UNCHECKED_CAST")

package id.arya.scanat.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.viewmodel.MainViewModel

class MainFactory(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}