package id.arya.scanat.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.viewmodel.LoginViewModel

@Suppress("UNCHECKED_CAST")
class LoginFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}