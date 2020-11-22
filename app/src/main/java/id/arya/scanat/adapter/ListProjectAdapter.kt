package id.arya.scanat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.arya.scanat.R
import id.arya.scanat.model.response.ListProjectResponse
import kotlinx.android.synthetic.main.item_list_project.view.*

class ListProjectAdapter(val listProjectResponse: ListProjectResponse) :
    RecyclerView.Adapter<ListProjectAdapter.ViewHolder>() {
    private lateinit var onSelectedProject: OnSelectedProject

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun onSelectedProject(onSelectedProject: OnSelectedProject) {
        this.onSelectedProject = onSelectedProject
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_project, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listProjectResponse.data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            project_name_list.text = listProjectResponse.data[position].pj_nama
            project_customer_list.text =
                "Costumer : " + listProjectResponse.data[position].pj_cu_kode
            project_date_list.text = listProjectResponse.data[position].pj_tanggal
            if (listProjectResponse.data[position].pj_status != null) {
                project_status_list.text =
                    "Last Activity : " + listProjectResponse.data[position].pj_status
            } else {
                project_status_list.text =
                    "Last Activity : No Activity"
            }

            setOnClickListener {
                onSelectedProject.onselectedproject(position, listProjectResponse.data)
            }
        }
    }

    interface OnSelectedProject {
        fun onselectedproject(
            position: Int,
            listProjectResponse: ArrayList<ListProjectResponse.Data>
        )
    }
}