package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListProjectResponse(

    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ArrayList<Data>

) {

    data class Data(
        @SerializedName("pj_id") @Expose val pj_id: String,
        @SerializedName("pj_kode") @Expose val pj_kode: String,
        @SerializedName("pj_nama") @Expose val pj_nama: String,
        @SerializedName("pj_sl_kode") @Expose val pj_sl_kode: String,
        @SerializedName("pj_cu_kode") @Expose val pj_cu_kode: String,
        @SerializedName("pj_cu_name") @Expose val pj_cu_name: String,
        @SerializedName("pj_nominal_anggaran") @Expose val pj_nominal_anggaran: String,
        @SerializedName("target_year") @Expose val target_year: String,
        @SerializedName("target_jumlah") @Expose val target_jumlah: String,
        @SerializedName("target_realis") @Expose val target_realis: String,
        @SerializedName("target_ket") @Expose val target_ket: String,
        @SerializedName("pj_sumber_anggaran") @Expose val pj_sumber_anggaran: String,
        @SerializedName("pj_tanggal") @Expose val pj_tanggal: String,
        @SerializedName("pj_status") @Expose val pj_status: String,
        @SerializedName("pj_keterangan") @Expose val pj_keterangan: String,
        @SerializedName("pj_create") @Expose val pj_create: String,
        @SerializedName("pj_modify") @Expose val pj_modify: String
    )

}

