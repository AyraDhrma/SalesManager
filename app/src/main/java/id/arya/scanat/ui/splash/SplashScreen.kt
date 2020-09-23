package id.arya.scanat.ui.splash

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import id.arya.scanat.R
import id.arya.scanat.model.response.SplashScreenResponse
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.main.MainActivity
import id.arya.scanat.viewmodel.SplashViewModel
import id.arya.scanat.viewmodelfactory.SplashScreenFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.fragment_dialog_loading.*

class SplashScreen : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var splashScreenFactory: SplashScreenFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initDependency()

        splash_loading.setAnimation(R.raw.loading_car_black)
        splash_loading.playAnimation()
        splash_loading.loop(true)

        splashViewModel.hitSplashScreen().observe(this, Observer<SplashScreenResponse> { response ->
            if (response != null) {
                if (response.info == "scanat") {
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
        val url = "http://202.157.186.151/managemenaset/"
        splashScreenFactory = SplashScreenFactory(MainRepository(url))
        splashViewModel = ViewModelProvider(this, splashScreenFactory)[SplashViewModel::class.java]
    }
}