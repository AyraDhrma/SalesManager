package id.arya.scanat.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.R
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.ui.login.LoginActivity
import id.arya.scanat.ui.scan.ScannerActivity
import id.arya.scanat.ui.submit.SubmitActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    //    private var isShowDialog = false
    private val loadingDialog = DialogLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkUserAlreadyLogin()

        button_scan_main.setOnClickListener {
            scanIntent()
        }

        button_manual_submit.setOnClickListener {
            val intent = Intent(this@MainActivity, SubmitActivity::class.java)
            intent.putExtra("result", "")
            startActivity(intent)
        }
    }

    private fun checkUserAlreadyLogin() {
        if (sharedPrefManager.loadUsername() == "") {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        } else {
            username_label_main.text = sharedPrefManager.loadUsername()
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            sharedPrefManager.clearUserData()
            finish()
            return
        }
        doubleBackToExitPressedOnce = true
        val snackbar = Snackbar.make(
            button_scan_main,
            resources.getString(R.string.press_again_to_exit_app),
            Snackbar.LENGTH_SHORT
        )
        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        snackbar.show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun scanIntent() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.captureActivity = ScannerActivity::class.java
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val intentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents == null) {
                val snackbar = Snackbar.make(
                    button_scan_main,
                    resources.getString(R.string.scan_barcode_not_found),
                    Snackbar.LENGTH_LONG
                )
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setAction(resources.getString(R.string.retry)) {
                    scanIntent()
                }.show()
            } else {
//                isShowDialog = true

                val intent = Intent(this@MainActivity, SubmitActivity::class.java)
                intent.putExtra("result", intentResult.contents)
                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 10) {
                if (resultCode == Activity.RESULT_OK) {
                    // Code Here
                }
            }
        }
    }

//    override fun onResumeFragments() {
//        super.onResumeFragments()
//
//        if (isShowDialog) {
//            isShowDialog = false
//
//            showLoadingDialog()
//        }
//    }

    private fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, "LOADING")
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

}