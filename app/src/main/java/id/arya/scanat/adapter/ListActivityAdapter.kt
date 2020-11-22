package id.arya.scanat.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.zoomy.Zoomy
import com.squareup.picasso.Picasso
import id.arya.scanat.R
import id.arya.scanat.model.response.ListActivityResponse
import kotlinx.android.synthetic.main.item_list_activity.view.*


class ListActivityAdapter(
    val activity: Activity,
    val listActivity: ArrayList<ListActivityResponse.Data>
) : RecyclerView.Adapter<ListActivityAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return listActivity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            Picasso.get()
                .load(listActivity[position].ls_gambar)
                .placeholder(R.drawable.background_role)
                .error(R.drawable.background_role)
                .into(image_activity)
            val builder: Zoomy.Builder = Zoomy.Builder(activity).target(image_activity)
            builder.register()
            caption.text = listActivity[position].ls_keterangan
            date.text = listActivity[position].ls_tanggal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_activity,
                parent, false
            )
        )
    }
}