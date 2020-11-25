package id.arya.scanat.utils

import android.annotation.SuppressLint
import android.widget.Filter
import id.arya.scanat.adapter.ListProductAdapter
import id.arya.scanat.model.response.ListProductResponse

class AllProductSearchFilter(
    private var listProductAdapter: ListProductAdapter,
    private var productList: ArrayList<ListProductResponse.Data>
) : Filter() {

    @SuppressLint("DefaultLocale")
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = FilterResults()
        if (constraint != null && constraint.isNotEmpty()) {
            val query = constraint.toString().toUpperCase()
            val filteredProducts: ArrayList<ListProductResponse.Data> = ArrayList()
            for (i in 0 until productList.size) {
                if (productList[i].mp_nama.toUpperCase().contains(query)) {
                    filteredProducts.add(productList[i])
                }
            }
            results.count = filteredProducts.size
            results.values = filteredProducts
        } else {
            results.count = productList.size
            results.values = productList
        }

        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        listProductAdapter.listProduct = results.values as ArrayList<ListProductResponse.Data>
        listProductAdapter.notifyDataSetChanged()
    }

}