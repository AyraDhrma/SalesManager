package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListActivityResponse(

    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ArrayList<Data>

) {

    data class Data(
        @SerializedName("ls_id") @Expose val ls_id: String,
        @SerializedName("ls_sl_kode") @Expose val ls_sl_kode: String,
        @SerializedName("ls_cu_kode") @Expose val ls_cu_kode: String,
        @SerializedName("ls_pj_kode") @Expose val ls_pj_kode: String,
        @SerializedName("ls_tdc_kode") @Expose val ls_tdc_kode: String,
        @SerializedName("ls_tanggal") @Expose val ls_tanggal: String,
        @SerializedName("ls_gambar") @Expose val ls_gambar: String,
        @SerializedName("ls_lokasi") @Expose val ls_lokasi: String,
        @SerializedName("ls_keterangan") @Expose val ls_keterangan: String,
        @SerializedName("ls_create") @Expose val ls_create: String,
        @SerializedName("ls_modify") @Expose val ls_modify: String
    )

}

