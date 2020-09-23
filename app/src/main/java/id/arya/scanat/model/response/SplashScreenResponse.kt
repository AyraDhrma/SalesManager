package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SplashScreenResponse(
    @SerializedName("version") @Expose val version: String,
    @SerializedName("info") @Expose val info: String,
    @SerializedName("url") @Expose val url: String,
    @SerializedName("key") @Expose val key: String,
    @SerializedName("username") @Expose val username: String,
    @SerializedName("password") @Expose val password: String,
    @SerializedName("img_banner") @Expose val img_banner: String,
    @SerializedName("greetings") @Expose val greetings: String
)