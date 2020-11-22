package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListTipeDocumentResponse(

    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ArrayList<Data>

) {

    data class Data(
        @SerializedName("tdc_id") @Expose val tdc_id: String,
        @SerializedName("tdc_kode") @Expose val tdc_kode: String,
        @SerializedName("tdc_nama") @Expose val tdc_nama: String,
        @SerializedName("tdc_keterangan") @Expose val tdc_keterangan: String,
        @SerializedName("tdc_created") @Expose val tdc_created: String,
        @SerializedName("tdc_modify") @Expose val tdc_modify: String
    )

}

