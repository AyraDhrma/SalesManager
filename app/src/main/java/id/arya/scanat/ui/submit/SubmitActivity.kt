package id.arya.scanat.ui.submit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_submit.*
import javax.inject.Inject

@AndroidEntryPoint
class SubmitActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private val loadingDialog = DialogLoading()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dependency()

        checkDataFromDatabase()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun checkDataFromDatabase() {
        showLoadingDialog()
        val requestParams = intent.getStringExtra("result")?.let { RequestParams(it) }
        requestParams?.let {
            mainViewModel.hitCheckData("f99aecef3d12e02dcbb6260bbdd35189c89e6e73", it)
                .observe(this, Observer { response ->
                    dismissLoadingDialog()
                    if (response.rc == "0000") {
                        setResultScan()
                        procdate_value.setText(response.data[0].proc_date)
                        description_value.setText(response.data[0].asset_desc)
                        location_value.setText(response.data[0].location)
                        life_value.setText(response.data[0].life)
                        button_submit.setOnClickListener {
                            intentStatusSubmit()
                        }
                    } else {
                        val snackbar = Snackbar.make(
                            asset_code_value,
                            response.message,
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                        setResultScan()
                        button_submit.setOnClickListener {
                            intentStatusSubmit()
                        }
                    }
                })
        }
    }

    private fun intentStatusSubmit() {
        val intent = Intent(
            this@SubmitActivity,
            StatusSubmitActivity::class.java
        )
        intent.putExtra("status", setDataParams())
        startActivity(intent)
        finish()
    }

    private fun setResultScan() {
        asset_code_value.setText(intent.extras?.getString("result"))
    }

    private fun setDataParams(): String {
        return sharedPrefManager.loadUsername() + "|" + procdate_value.text + "|" +
                asset_code_value.text + "|" + description_value.text + "|" +
                location_value.text + "|" + location_value.text
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