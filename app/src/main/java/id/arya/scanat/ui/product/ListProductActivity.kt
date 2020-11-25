package id.arya.scanat.ui.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.R
import id.arya.scanat.adapter.ListProductAdapter
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.model.response.ListProductResponse
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_list_product.*
import kotlinx.android.synthetic.main.activity_list_product.empty
import kotlinx.android.synthetic.main.activity_list_product.progress_bar
import kotlinx.android.synthetic.main.activity_list_product.toolbar
import kotlinx.android.synthetic.main.activity_project_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class ListProductActivity : AppCompatActivity() {

    private lateinit var adapter: ListProductAdapter

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)
        dependency()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        getListProduct()
    }

    private fun searchProduct() {
        search_product.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter.filter(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                adapter.filter.filter(s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

        })
    }

    private fun dependency() {
        val url = sharedPrefManager.loadUrl()
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
    }

    private fun getListProduct() {
        visibleLoading()
        val requestParams = RequestParams("")
        mainViewModel.getListProduct(sharedPrefManager.loadApiKey(), requestParams)
            .observe(this, Observer { response ->
                hideLoading()
                hideEmptyAnim()
                visibleListProject()
                when {
                    response.rc == "0000" -> {
                        var responseList = ArrayList<ListProductResponse.Data>()
                        responseList.clear()
                        responseList = response.data
                        adapter = ListProductAdapter(this, responseList)
                        rv_list_product.hasFixedSize()
                        rv_list_product.layoutManager = LinearLayoutManager(this)
                        rv_list_product.adapter = adapter
                        searchProduct()
                    }
                    response.message == "Empty" -> {
                        visibleEmptyAnim()
                    }
                    else -> {
                        val snackbar = Snackbar.make(
                            document_title_detail,
                            response.message,
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                        snackbar.show()
                    }
                }
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun visibleLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    fun visibleListProject() {
        rv_list_product.visibility = View.VISIBLE
    }

    fun hideListProject() {
        rv_list_product.visibility = View.GONE
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


}