package id.arya.scanat.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.R
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.response.SplashScreenResponse
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.main.MainActivity
import id.arya.scanat.viewmodel.SplashViewModel
import id.arya.scanat.viewmodelfactory.SplashScreenFactory
import kotlinx.android.synthetic.main.activity_splash_screen.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var splashScreenFactory: SplashScreenFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initDependency()

        splash_loading.setAnimation(R.raw.loading_animation)
        splash_loading.playAnimation()
        splash_loading.loop(true)

        splashViewModel.hitSplashScreen().observe(this, Observer<SplashScreenResponse> { response ->
            if (response != null) {
                if (response.info == "success") {
                    sharedPrefManager.saveSplashData(response)
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }
            } else {
                val snackbar = Snackbar.make(
                    splash_logo,
                    resources.getString(R.string.no_internet),
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setAction(resources.getString(R.string.retry)) {
                    finish()
                    startActivity(intent)
                }.show()
            }
        })
    }

    private fun initDependency() {
        val url = "http://103.82.242.80/crmapi/"
        splashScreenFactory = SplashScreenFactory(MainRepository(url))
        splashViewModel = ViewModelProvider(this, splashScreenFactory)[SplashViewModel::class.java]
    }
}