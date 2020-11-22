package id.arya.scanat.ui.document

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_edit_document.*
import kotlinx.android.synthetic.main.activity_submit.toolbar
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class EditDocument : AppCompatActivity() {
    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory
    private val loadingDialog = DialogLoading()
    var tipeDoc = ""
    var statusDoc = ""
    var idDoc = ""
    val listkode = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_document)
        dependency()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        checkEditOrNew()
        listener()
    }

    private fun checkEditOrNew() {
        if (intent.getStringExtra("activity") == "edit") {
            val namaDoc = intent.getStringExtra("tipe_dokumen")
            getListTipeDocumentUpdate(namaDoc)
            idDoc = intent.getStringExtra("id_dokumen")
            tipe_dokumen_activity.setText(namaDoc)
            nomor_dokumen_activity.setText(intent.getStringExtra("nomor_dokumen"))
            tanggal_dokumen_activity.setText(intent.getStringExtra("tanggal_dokumen"))
            email_dokumen_activity.setText(intent.getStringExtra("email_dokumen"))
            keterangan_dokumen_activity.setText(intent.getStringExtra("keterangan_dokumen"))
            if (intent.getStringExtra("status_dokumen") == "1") {
                statusDoc = "1"
                close.isChecked = true
            } else {
                statusDoc = "0"
                open.isChecked = true
            }
        } else {
            getListTipeDocument()
        }
    }

    private fun showLoadingDialog() {
        loadingDialog.show(supportFragmentManager, "LOADING")
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun getListTipeDocumentUpdate(namaDoc: String?) {
        showLoadingDialog()
        val requestParams = RequestParams("")
        mainViewModel.getListTipeDocument(sharedPrefManager.loadApiKey(), requestParams)
            .observe(this, Observer { res ->
                dismissLoadingDialog()
                if (res.rc == "0000") {
                    listkode.clear()

                    for (res in res.data) {
                        listkode.add(res.tdc_nama)
                        if (namaDoc == res.tdc_nama) {
                            tipeDoc = res.tdc_kode
                        }
                    }

                    tipe_dokumen_activity.setOnClickListener {
                        val spinnerDialog = SpinnerDialog(
                            this@EditDocument,
                            listkode,
                            "Pilih Dokumen",
                            R.style.DialogAnimations_SmileWindow,
                            "Batal"
                        )
                        spinnerDialog.bindOnSpinerListener { s, i ->
                            tipe_dokumen_activity.setText(s)
                            tipeDoc = res.data[i].tdc_kode
                        }
                        spinnerDialog.showSpinerDialog()
                    }

                } else {
                    val snackbar = Snackbar.make(
                        tipe_dokumen_activity,
                        res.message,
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                    snackbar.show()
                }
            })
    }

    private fun getListTipeDocument() {
        showLoadingDialog()
        val requestParams = RequestParams("")
        mainViewModel.getListTipeDocument(sharedPrefManager.loadApiKey(), requestParams)
            .observe(this, Observer { res ->
                dismissLoadingDialog()
                if (res.rc == "0000") {
                    listkode.clear()
                    for (res in res.data) {
                        listkode.add(res.tdc_nama)
                    }

                    tipe_dokumen_activity.setOnClickListener {
                        val spinnerDialog = SpinnerDialog(
                            this@EditDocument,
                            listkode,
                            "Pilih Dokumen",
                            R.style.DialogAnimations_SmileWindow,
                            "Batal"
                        )
                        spinnerDialog.bindOnSpinerListener { s, i ->
                            tipe_dokumen_activity.setText(s)
                            tipeDoc = res.data[i].tdc_kode
                        }
                        spinnerDialog.showSpinerDialog()
                    }

                } else {
                    val snackbar = Snackbar.make(
                        tipe_dokumen_activity,
                        res.message,
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                    snackbar.show()
                }
            })
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
    }

    @SuppressLint("SetTextI18n")
    private fun listener() {
        tanggal_dokumen_activity.setOnClickListener {
            val newCalendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this@EditDocument,
                OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate[year, monthOfYear] = dayOfMonth
                    val dateFormatter =
                        SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    tanggal_dokumen_activity.setText(dateFormatter.format(newDate.time))
                },
                newCalendar[Calendar.YEAR],
                newCalendar[Calendar.MONTH],
                newCalendar[Calendar.DAY_OF_MONTH]
            )

            datePickerDialog.show()

        }

        status_dokumen.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.open -> {
                    statusDoc = "0"
                }
                R.id.close -> {
                    statusDoc = "1"
                }
            }
        }

        add_document.setOnClickListener {
            if (intent.getStringExtra("activity") == "edit") {
                if (validate()) {
                    val projectCode = intent.getStringExtra("project_code")
                    val requestParams = RequestParams(
                        "$tipeDoc|" +
                                "${nomor_dokumen_activity.text.toString()}|$projectCode|${tanggal_dokumen_activity.text.toString()}|" +
                                "${email_dokumen_activity.text.toString()}|$statusDoc|${keterangan_dokumen_activity.text.toString()}|" +
                                idDoc
                    )
                    mainViewModel.updateDocument(sharedPrefManager.loadApiKey(), requestParams)
                        .observe(this, Observer { res ->
                            if (res.rc == "0000") {
                                Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                val snackbar = Snackbar.make(
                                    tipe_dokumen_activity,
                                    res.message,
                                    Snackbar.LENGTH_SHORT
                                )
                                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                                snackbar.show()
                            }
                        })
                }
            } else {
                if (validate()) {
                    val projectCode = intent.getStringExtra("project_code")
                    val requestParams = RequestParams(
                        "$tipeDoc|" +
                                "${nomor_dokumen_activity.text.toString()}|$projectCode|${tanggal_dokumen_activity.text.toString()}|" +
                                "${email_dokumen_activity.text.toString()}|$statusDoc|${keterangan_dokumen_activity.text.toString()}"
                    )
                    mainViewModel.submitDocument(sharedPrefManager.loadApiKey(), requestParams)
                        .observe(this, Observer { res ->
                            if (res.rc == "0000") {
                                Toast.makeText(this, res.message, Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                val snackbar = Snackbar.make(
                                    tipe_dokumen_activity,
                                    res.message,
                                    Snackbar.LENGTH_SHORT
                                )
                                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                                snackbar.show()
                            }
                        })
                }
            }
        }
    }

    fun validate(): Boolean {
        var istrue = true
        when {
            tipe_dokumen_activity.text.toString() == "" -> {
                tipe_dokumen_activity.error = resources.getString(R.string.cannot_null)
                istrue = false
            }
            nomor_dokumen_activity.text.toString() == "" -> {
                nomor_dokumen_activity.error = resources.getString(R.string.cannot_null)
                istrue = false
            }
            tanggal_dokumen_activity.text.toString() == "" -> {
                tanggal_dokumen_activity.error = resources.getString(R.string.cannot_null)
                istrue = false
            }
            email_dokumen_activity.text.toString() == "" -> {
                email_dokumen_activity.error = resources.getString(R.string.cannot_null)
                istrue = false
            }
            keterangan_dokumen_activity.text.toString() == "" -> {
                keterangan_dokumen_activity.error = resources.getString(R.string.cannot_null)
                istrue = false
            }
            statusDoc == "" -> {
                Toast.makeText(this, "Status Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                istrue = false
            }

            else -> {
                istrue = true
            }
        }
        return istrue
    }
}