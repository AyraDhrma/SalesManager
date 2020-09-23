package id.arya.scanat.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import id.arya.scanat.R
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.ui.dialog.SubmitDataDialog
import id.arya.scanat.ui.scan.ScannerActivity
import id.arya.scanat.ui.submit.SubmitActivity
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_scan_main.setOnClickListener {
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
                val bundle = Bundle()
                bundle.putString("result", intentResult.contents)
                val intent = Intent(this@MainActivity, SubmitActivity::class.java)
                intent.putExtras(bundle)
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

}