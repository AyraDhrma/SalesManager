package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListProductResponse(

    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ArrayList<Data>

) {

    data class Data(
        @SerializedName("mp_nama") @Expose val mp_nama: String,
        @SerializedName("mp_jenis") @Expose val mp_jenis: String,
        @SerializedName("mp_tipe") @Expose val mp_tipe: String,
        @SerializedName("mp_kapasitas") @Expose val mp_kapasitas: String,
        @SerializedName("mp_satuan") @Expose val mp_satuan: String,
        @SerializedName("mp_jumlah") @Expose val mp_jumlah: String,
        @SerializedName("mp_berat") @Expose val mp_berat: String,
        @SerializedName("mp_harga") @Expose val mp_harga: String,
        @SerializedName("mp_keterangan") @Expose val mp_keterangan: String
    )

}

