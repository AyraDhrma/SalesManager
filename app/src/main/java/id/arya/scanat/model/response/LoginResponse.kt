package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: Data

) {

    data class Data(
        @SerializedName("username") @Expose val username: String,
        @SerializedName("password") @Expose val password: String,
        @SerializedName("nama") @Expose val nama: String,
        @SerializedName("id_role") @Expose val id_role: String,
        @SerializedName("email") @Expose val email: String,
        @SerializedName("phone") @Expose val phone: String,
        @SerializedName("message") @Expose val message: String
    )

}
