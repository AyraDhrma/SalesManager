package id.arya.scanat.ui.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.R
import id.arya.scanat.adapter.ListActivityAdapter
import id.arya.scanat.costumeui.DialogFragmentGps
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.ListActivityResponse
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.activity.SalesActivity
import id.arya.scanat.ui.document.ListDocumentActivity
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.activity_submit.toolbar
import javax.inject.Inject

@AndroidEntryPoint
class ProjectDetail : AppCompatActivity() {

    private var customer: String? = null
    private var projectCode: String? = null

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory
    private lateinit var listActivityAdapter: ListActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)
        dependency()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        getListDocument()
    }

    private fun checkGpsOn(): Boolean {
        var isGpsOn = false
        val manager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        isGpsOn = if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val dialogFragmentGps = DialogFragmentGps()
            dialogFragmentGps.show(supportFragmentManager, "GPS")
            false
        } else {
            true
        }
        return isGpsOn
    }

    private fun listener() {
        add_document.setOnClickListener {
            val intent = Intent(this, ListDocumentActivity::class.java)
            intent.putExtra("project_code", projectCode)
            intent.putExtra("customer", customer)
            startActivity(intent)
        }

        add_activity.setOnClickListener {
            val intent = Intent(this, SalesActivity::class.java)
            intent.putExtra("project_code", projectCode)
            intent.putExtra("customer", customer)
            startActivity(intent)
        }

        swipe_refresh.setOnRefreshListener {
            Handler().postDelayed({ // Berhenti berputar/refreshing
                swipe_refresh.isRefreshing = false
                getListDocument()
            }, 2000)
        }
    }

    fun visibleLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    fun visibleListProject() {
        rv_list_document_detail.visibility = View.VISIBLE
    }

    fun hideListProject() {
        rv_list_document_detail.visibility = View.GONE
    }

    fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    fun visibleEmptyAnim() {
        empty.visibility = View.VISIBLE
        empty.setAnimation(R.raw.empty)
        empty.playAnimation()
    }

    fun hideEmptyAnim() {
        empty.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun dependency() {
        val url = sharedPrefManager.loadUrl()
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
        customer = intent.getStringExtra("customer")
        val projectName = intent.getStringExtra("project_name")
        val projectStatus = intent.getStringExtra("project_status")
        val projectDate = intent.getStringExtra("project_date")

        project_name_detail.text = projectName
        project_customername_detail.text = customer
        project_date_detail.text = "Tanggal $projectDate"
        if (projectStatus == "1") {
            project_status_detail.background = this.getDrawable(R.drawable.background_role_red)
            project_status_detail.text = "close"
        } else {
            project_status_detail.text = "open"
        }
        projectCode = intent.getStringExtra("project_code")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getListDocument() {
        val projectCode = intent.getStringExtra("project_code")
        visibleLoading()
        val requestParams = RequestParams("|$projectCode")
        mainViewModel.getListActivity(sharedPrefManager.loadApiKey(), requestParams)
            .observe(this, Observer { response ->
                hideLoading()
                hideEmptyAnim()
                visibleListProject()
                when {
                    response.rc == "0000" -> {
                        var responseList = ArrayList<ListActivityResponse.Data>()
                        responseList.clear()
                        responseList = response.data
                        val adapter = ListActivityAdapter(this, responseList)
                        rv_list_document_detail.hasFixedSize()
                        rv_list_document_detail.layoutManager = LinearLayoutManager(this)
                        rv_list_document_detail.adapter = adapter

                        listener()

                    }
                    response.message == "Empty Activity" -> {
                        visibleEmptyAnim()

                        listener()

                    }
                    else -> {
                        val snackbar = Snackbar.make(
                            document_title_detail,
                            response.message,
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                        snackbar.show()

                        listener()

                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        checkGpsOn()
        getListDocument()
    }

    override fun onDestroy() {
        super.onDestroy()
        empty.clearAnimation()
    }

}