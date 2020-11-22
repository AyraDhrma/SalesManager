package id.arya.scanat.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.arya.scanat.R
import id.arya.scanat.model.response.ListProductResponse
import kotlinx.android.synthetic.main.item_list_product.view.*

class ListProductAdapter(
    val activity: Activity,
    val listProduct: ArrayList<ListProductResponse.Data>
) : RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            nama_product.text = listProduct[position].mp_nama
            harga_product.text = listProduct[position].mp_harga
            jenis_product.text = listProduct[position].mp_jenis
            tipe_product.text = listProduct[position].mp_tipe
            kapasitas_product.text = listProduct[position].mp_kapasitas
            jumlah_product.text = listProduct[position].mp_jumlah
            satuan_product.text = listProduct[position].mp_satuan
            berat_product.text = listProduct[position].mp_berat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_product,
                parent, false
            )
        )
    }
}