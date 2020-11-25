package id.arya.scanat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.arya.scanat.R
import id.arya.scanat.model.response.ListDocumentResponse
import kotlinx.android.synthetic.main.item_list_dokumen.view.*

class ListDocumentAdapter(val listDocumentResponse: ListDocumentResponse) :
    RecyclerView.Adapter<ListDocumentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var onSelectedDocument: OnSelectedDocument

    fun onSelectedDocument(onSelectedDocument: OnSelectedDocument) {
        this.onSelectedDocument = onSelectedDocument
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_dokumen, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDocumentResponse.data.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            nomor_dokumen.text = "No : " + listDocumentResponse.data[position].dc_nomor
            tanggal_dokumen.text = listDocumentResponse.data[position].dc_tanggal
            tipe_dokumen.text = "Nama : " + listDocumentResponse.data[position].dc_tdc_kode
            when (listDocumentResponse.data[position].dc_status) {
                "1" -> {
                    status_dokumen.background = context.getDrawable(R.drawable.background_role_red)
                    if (listDocumentResponse.data[position].dc_kondisi == "0") {
                        status_dokumen.text = "Close (Failed)"
                    } else {
                        status_dokumen.text = "Close (Approved)"
                    }
                    status_done.visibility = View.VISIBLE
                }
                "3" -> {
                    status_dokumen.background = context.getDrawable(R.drawable.background_role_blue)
                    status_dokumen.text = "Request"
                }
                else -> {
                    if (listDocumentResponse.data[position].dc_kondisi == "0") {
                        status_dokumen.text = "Open (Failed)"
                    } else {
                        status_dokumen.text = "Open (Approved)"
                    }
                }
            }
            email_dokumen.text = "Email : " + listDocumentResponse.data[position].dc_email
            isi_keterangan_dokumen.text = listDocumentResponse.data[position].dc_keterangan
            setOnClickListener {
                onSelectedDocument.onSelectedDocument(position, listDocumentResponse.data)
            }
        }
    }

    interface OnSelectedDocument {
        fun onSelectedDocument(
            position: Int,
            listDocumentResponse: ArrayList<ListDocumentResponse.Data>
        )
    }
}