package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListDocumentResponse(

    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ArrayList<Data>

) {

    data class Data(
        @SerializedName("dc_id") @Expose val dc_id: String,
        @SerializedName("dc_tdc_kode") @Expose val dc_tdc_kode: String,
        @SerializedName("dc_nomor") @Expose val dc_nomor: String,
        @SerializedName("dc_pj_kode") @Expose val dc_pj_kode: String,
        @SerializedName("dc_tanggal") @Expose val dc_tanggal: String,
        @SerializedName("dc_email") @Expose val dc_email: String,
        @SerializedName("dc_status") @Expose val dc_status: String,
        @SerializedName("dc_kondisi") @Expose val dc_kondisi: String,
        @SerializedName("dc_keterangan") @Expose val dc_keterangan: String,
        @SerializedName("dc_created") @Expose val dc_created: String,
        @SerializedName("dc_modify") @Expose val dc_modify: String
    )

}

