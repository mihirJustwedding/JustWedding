package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class FunctionDetailsResponse {

    @SerializedName("functionMasterDetails")
    @Expose
    val functionMasterDetails: List<FunctionMasterDetail>? = null


    class FunctionMasterDetail {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("functionname")
        @Expose
        var functionname: String? = null

        @SerializedName("fun_start")
        @Expose
        var funStart: String? = null

        @SerializedName("fun_end")
        @Expose
        var funEnd: String? = null

        @SerializedName("price")
        @Expose
        var price: Double? = null

        @SerializedName("function_image")
        @Expose
        var functionImage: String? = null

        @SerializedName("isforwebsite")
        @Expose
        var isforwebsite: Int? = null

        @SerializedName("clientId")
        @Expose
        var clientId: Int? = null
    }
}