package id.arya.scanat.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckDataResponse(

    @SerializedName("rc") @Expose val rc: String,
    @SerializedName("response") @Expose val response: String,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ArrayList<Data>

) {

    data class Data(
        @SerializedName("id") @Expose val id: String,
        @SerializedName("proc_date") @Expose val proc_date: String,
        @SerializedName("report_prd") @Expose val report_prd: String,
        @SerializedName("reptype") @Expose val reptype: String,
        @SerializedName("comp_code") @Expose val comp_code: String,
        @SerializedName("valtype") @Expose val valtype: String,
        @SerializedName("account") @Expose val account: String,
        @SerializedName("asset") @Expose val asset: String,
        @SerializedName("no_asset") @Expose val no_asset: String,
        @SerializedName("sno") @Expose val sno: String,
        @SerializedName("depky") @Expose val depky: String,
        @SerializedName("plant") @Expose val plant: String,
        @SerializedName("capdate") @Expose val capdate: String,
        @SerializedName("odepstart") @Expose val odepstart: String,
        @SerializedName("costctr") @Expose val costctr: String,
        @SerializedName("asset_desc") @Expose val asset_desc: String,
        @SerializedName("life") @Expose val life: String,
        @SerializedName("location") @Expose val location: String,
        @SerializedName("created") @Expose val created: String,
        @SerializedName("modified") @Expose val modified: String
    )

}

