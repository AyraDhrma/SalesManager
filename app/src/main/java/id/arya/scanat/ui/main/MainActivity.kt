package id.arya.scanat.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import id.arya.scanat.R
import id.arya.scanat.adapter.ListProjectAdapter
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.ListProjectResponse
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.ui.login.LoginActivity
import id.arya.scanat.ui.product.ListProductActivity
import id.arya.scanat.ui.profile.ProfileActivity
import id.arya.scanat.ui.project.ProjectDetail
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var targetVal = 0
    private var realizeVal = 0

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory
    private lateinit var listProjectAdapter: ListProjectAdapter

    //    private var isShowDialog = false
    private val loadingDialog = DialogLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dependency()
        checkCameraPermission()
        checkUserAlreadyLogin()
        listener()
    }

    private fun checkCameraPermission() {
        Dexter.withContext(this@MainActivity)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            Toasty.success(
                                this@MainActivity, "All Permission Granted",
                                Toast.LENGTH_SHORT, true
                            ).show();
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Toasty.error(this@MainActivity, it.name, Toast.LENGTH_SHORT, true).show()
            }
            .check()
    }

    private fun listener() {
        profile_img_main.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }
        product_img_main.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListProductActivity::class.java))
        }
        swipe_refresh.setOnRefreshListener {
            Handler().postDelayed({
                swipe_refresh.isRefreshing = false
                getListProject()
            }, 2000)
        }
    }

    private fun dependency() {
        val url = sharedPrefManager.loadUrl()
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
    }

    private fun checkUserAlreadyLogin() {
        if (sharedPrefManager.loadUsername() == "") {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        } else {
            username_label_main.text = "Nama : " + sharedPrefManager.loadUsername()
            role_label_main.text = "Sales"
            getListProject()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getListProject() {
        visibleLoading()
        val salesCode = sharedPrefManager.loadSalesCode()
        val requestParams = RequestParams("$salesCode|")
        mainViewModel.getListProject(sharedPrefManager.loadApiKey(), requestParams)
            .observe(this, Observer { response ->
                hideLoading()
                visibleListProject()
                saveFirebaseId()
                if (response.rc == "0000") {
                    if (response.data[0].target_jumlah != null) {
                        targetVal = response.data[0].target_jumlah!!
                        target.setmValueText(
                            "Rp " + NumberFormat.getNumberInstance(Locale("in", "ID"))
                                .format(response.data[0].target_jumlah)
                        )
                        target.setmDefText("Target ${response.data[0].target_year}")
                        target.setmPercentage(100)
                    } else {
                        target.setmValueText("")
                        target.setmDefText("No Target")
                        target.setmPercentage(0)
                    }
                    if (response.data[0].target_realis != null) {
                        realizeVal = response.data[0].target_realis!!
                        realise.setmValueText(
                            "Rp " + NumberFormat.getNumberInstance(Locale("in", "ID"))
                                .format(response.data[0].target_realis)
                        )
                        realise.setmDefText("Realize ${response.data[0].target_year}")
                        val percentage = (realizeVal.toDouble() / targetVal) * 100
                        realise.setmPercentage(percentage.toInt())
                    } else {
                        realise.setmValueText("")
                        realise.setmDefText("No Target")
                        realise.setmPercentage(0)
                    }

                    listProjectAdapter = ListProjectAdapter(response)
                    rv_list_project_main.setHasFixedSize(true)
                    rv_list_project_main.layoutManager = LinearLayoutManager(this)
                    rv_list_project_main.adapter = listProjectAdapter
                    listProjectAdapter.onSelectedProject(object :
                        ListProjectAdapter.OnSelectedProject {
                        override fun onselectedproject(
                            position: Int,
                            listProjectResponse: ArrayList<ListProjectResponse.Data>
                        ) {
                            val intent = Intent(this@MainActivity, ProjectDetail::class.java)
                            intent.putExtra("project_name", listProjectResponse[position].pj_nama)
                            intent.putExtra("customer", listProjectResponse[position].pj_cu_name)
                            intent.putExtra("project_code", listProjectResponse[position].pj_kode)
                            intent.putExtra(
                                "project_status",
                                listProjectResponse[position].pj_status
                            )
                            intent.putExtra(
                                "project_date",
                                listProjectResponse[position].pj_tanggal
                            )
                            startActivity(intent)
                        }
                    })
                } else {
                    saveFirebaseId()
                    val snackbar = Snackbar.make(
                        project_title_main,
                        response.message,
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                    snackbar.show()
                }
            })
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            sharedPrefManager.clearUserData()
            finish()
            return
        }
        doubleBackToExitPressedOnce = true
        val snackbar = Snackbar.make(
            username_label_main,
            resources.getString(R.string.press_again_to_exit_app),
            Snackbar.LENGTH_SHORT
        )
        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        snackbar.show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onResume() {
        super.onResume()
        getListProject()
        saveFirebaseId()
    }

    fun visibleLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    fun visibleListProject() {
        rv_list_project_main.visibility = View.VISIBLE
    }

    fun hideListProject() {
        rv_list_project_main.visibility = View.GONE
    }

    fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    fun saveFirebaseId() {
        val requestParams = RequestParams(
            sharedPrefManager.loadSalesCode() + "|" +
                    sharedPrefManager.loadFirebaseId()
        )
        mainViewModel.submitFFID(sharedPrefManager.loadApiKey(), requestParams)
            .observe(this, Observer { response ->

            })
    }

}