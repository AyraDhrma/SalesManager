package id.arya.scanat.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RequestParams (
    @SerializedName("data") @Expose val data: String
)