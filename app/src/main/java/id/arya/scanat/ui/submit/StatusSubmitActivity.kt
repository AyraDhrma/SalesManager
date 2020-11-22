package id.arya.scanat.ui.submit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.arya.scanat.R
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_submit.*
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
    }

    private fun dependency() {
        val url = "http://202.157.186.151/assetmanagementapi/"
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
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