package id.arya.scanat.ui.submit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import id.arya.scanat.R
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_submit.*

class SubmitActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory
    private val loadingDialog = DialogLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        dependency()

        checkDataFromDatabase()
    }

    private fun checkDataFromDatabase() {
        showLoadingDialog()
        val requestParams = intent.extras?.getString("result")?.let { RequestParams(it) }
        requestParams?.let {
            mainViewModel.hitCheckData("f99aecef3d12e02dcbb6260bbdd35189c89e6e73", it)
                .observe(this, Observer { response ->
                    dismissLoadingDialog()
                    if (response.rc == "0000") {
                        result.text = response.data[0].asset_desc
                    } else {
                        val snackbar = Snackbar.make(
                            result,
                            response.message,
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                    }
                })
        }
    }

    private fun dependency() {
        val url = "http://202.157.186.151/assetmanagementapi/"
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
    }

    private fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, "LOADING")
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

}