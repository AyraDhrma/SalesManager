package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubmitResponse(
    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String
)
