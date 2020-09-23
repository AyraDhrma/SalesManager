package id.arya.scanat.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.viewmodel.SplashViewModel

class SplashScreenFactory(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(repository) as T
    }
}