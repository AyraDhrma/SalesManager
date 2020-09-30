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
        @SerializedName("mb_id") @Expose val id: String,
        @SerializedName("mb_proc_date") @Expose val proc_date: String,
        @SerializedName("mb_report_prd") @Expose val report_prd: String,
        @SerializedName("mb_reptype") @Expose val reptype: String,
        @SerializedName("mb_comp_code") @Expose val comp_code: String,
        @SerializedName("mb_valtype") @Expose val valtype: String,
        @SerializedName("mb_account") @Expose val account: String,
        @SerializedName("mb_asset") @Expose val asset: String,
        @SerializedName("mb_no_asset") @Expose val no_asset: String,
        @SerializedName("mb_sno") @Expose val sno: String,
        @SerializedName("mb_depky") @Expose val depky: String,
        @SerializedName("mb_plant") @Expose val plant: String,
        @SerializedName("mb_capdate") @Expose val capdate: String,
        @SerializedName("mb_odepstart") @Expose val odepstart: String,
        @SerializedName("mb_costctr") @Expose val costctr: String,
        @SerializedName("mb_asset_desc") @Expose val asset_desc: String,
        @SerializedName("mb_life") @Expose val life: String,
        @SerializedName("mb_lc_kode") @Expose val location: String,
        @SerializedName("mb_created") @Expose val created: String,
        @SerializedName("mb_modified") @Expose val modified: String
    )

}

