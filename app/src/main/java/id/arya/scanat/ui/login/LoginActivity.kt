package id.arya.scanat.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.R
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.ui.main.MainActivity
import id.arya.scanat.viewmodel.LoginViewModel
import id.arya.scanat.viewmodelfactory.LoginFactory
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginFactory: LoginFactory

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private val loadingDialog = DialogLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        dependency()

        listenerEvent()
    }

    private fun dependency() {
        val url = "http://202.157.186.151/assetmanagementapi/"
        loginFactory = LoginFactory(MainRepository(url))
        loginViewModel = ViewModelProvider(this, loginFactory)[LoginViewModel::class.java]
    }

    private fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, "LOADING")
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun listenerEvent() {
        login_password_input.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                showLoadingDialog()
                hitApiLogin()
                return@OnKeyListener true
            }
            false
        })

        button_login.setOnClickListener {
            showLoadingDialog()
            hitApiLogin()
        }
    }

    private fun hitApiLogin() {
        val requestParams = RequestParams(
            login_username_input.text.toString() + "|" +
                    login_password_input.text.toString()
        )
        loginViewModel.hitLoginApi("f99aecef3d12e02dcbb6260bbdd35189c89e6e73", requestParams)
            .observe(this, Observer { response ->
                dismissLoadingDialog()
                if (response.rc == "0000") {
                    sharedPrefManager.saveUserData(response)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    val snackbar = Snackbar.make(
                        button_login,
                        response.message,
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                }
            })
    }

}