package id.arya.scanat.ui.submit

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import id.arya.scanat.R
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.ui.scan.ScannerActivity
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_status_submit.*
import kotlinx.android.synthetic.main.activity_submit.toolbar
import javax.inject.Inject

class StatusSubmitActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private val loadingDialog = DialogLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_submit)
        dependency()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        checkStatusSubmit()
    }

    private fun dependency() {
        val url = "http://202.157.186.151/assetmanagementapi/"
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
    }

    private fun checkStatusSubmit() {
        val status = intent.getStringExtra("status")
        val requestParams = RequestParams(status)
        showLoadingDialog()
        mainViewModel.hitSubmitAsset(
            "f99aecef3d12e02dcbb6260bbdd35189c89e6e73",
            requestParams
        )
            .observe(this, Observer { response ->
                dismissLoadingDialog()
                if (response.rc == "0000") {
                    animation_status.setAnimation(R.raw.success)
                    animation_status.playAnimation()
                    animation_status.speed = 2f
                    label_status.text = "Submit Succesfull"
                } else {
                    animation_status.setAnimation(R.raw.fail)
                    animation_status.playAnimation()
                    animation_status.speed = 2f
                    label_status.text = "Submit Failed"
                }
            })

        button_home.setOnClickListener {
            finish()
        }

        button_scan.setOnClickListener {
            scanIntent()
        }
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
                    button_scan,
                    resources.getString(R.string.scan_barcode_not_found),
                    Snackbar.LENGTH_LONG
                )
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setAction(resources.getString(R.string.retry)) {
                    scanIntent()
                }.show()
            } else {
                val intent = Intent(this@StatusSubmitActivity, SubmitActivity::class.java)
                intent.putExtra("result", intentResult.contents)
                startActivity(intent)
                finish()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, "LOADING")
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

}