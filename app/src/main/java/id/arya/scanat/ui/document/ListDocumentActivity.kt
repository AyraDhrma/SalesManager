package id.arya.scanat.ui.document

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.R
import id.arya.scanat.adapter.ListDocumentAdapter
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.ListDocumentResponse
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_list_document.*
import javax.inject.Inject

@AndroidEntryPoint
class ListDocumentActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory
    private var projectCode: String? = null
    private var customer: String? = null
    private lateinit var listDocumentAdapter: ListDocumentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_document)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dependency()
        getListDocument()
        listener()
    }

    private fun listener() {
        add_document.setOnClickListener {
            val intent = Intent(this, EditDocument::class.java)
            intent.putExtra("project_code", projectCode)
            intent.putExtra("customer", customer)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("SetTextI18n")
    private fun dependency() {
        val url = sharedPrefManager.loadUrl()
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
        projectCode = intent.getStringExtra("project_code")
        customer = intent.getStringExtra("customer")
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

    private fun getListDocument() {
        visibleLoading()
        val requestParams = RequestParams("|$projectCode")
        mainViewModel.getListDocument(sharedPrefManager.loadApiKey(), requestParams)
            .observe(this, Observer { response ->
                hideLoading()
                hideEmptyAnim()
                visibleListProject()
                when {
                    response.rc == "0000" -> {
                        var responseList = ArrayList<ListDocumentResponse.Data>()
                        responseList.clear()
                        responseList = response.data
                        listDocumentAdapter = ListDocumentAdapter(response)
                        rv_list_document_detail.setHasFixedSize(true)
                        rv_list_document_detail.layoutManager = LinearLayoutManager(this)
                        rv_list_document_detail.adapter = listDocumentAdapter
                        listDocumentAdapter.onSelectedDocument(object :
                            ListDocumentAdapter.OnSelectedDocument {
                            override fun onSelectedDocument(
                                position: Int,
                                listDocumentResponse: ArrayList<ListDocumentResponse.Data>
                            ) {
                                val intent =
                                    Intent(this@ListDocumentActivity, EditDocument::class.java)
                                intent.putExtra("project_code", projectCode)
                                intent.putExtra("activity", "edit")
                                intent.putExtra("id_dokumen", listDocumentResponse[position].dc_id)
                                intent.putExtra(
                                    "tipe_dokumen",
                                    listDocumentResponse[position].dc_tdc_kode
                                )
                                intent.putExtra(
                                    "nomor_dokumen",
                                    listDocumentResponse[position].dc_nomor
                                )
                                intent.putExtra(
                                    "tanggal_dokumen",
                                    listDocumentResponse[position].dc_tanggal
                                )
                                intent.putExtra(
                                    "email_dokumen",
                                    listDocumentResponse[position].dc_email
                                )
                                intent.putExtra(
                                    "status_dokumen",
                                    listDocumentResponse[position].dc_status
                                )
                                intent.putExtra(
                                    "keterangan_dokumen",
                                    listDocumentResponse[position].dc_keterangan
                                )
                                startActivity(intent)
                            }
                        })
                    }
                    response.message == "Empty document" -> {
                        visibleEmptyAnim()
                    }
                    else -> {
                        val snackbar = Snackbar.make(
                            rv_list_document_detail,
                            response.message,
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                        snackbar.show()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        hideListProject()
        getListDocument()
    }

    override fun onDestroy() {
        super.onDestroy()
        empty.clearAnimation()
    }


}