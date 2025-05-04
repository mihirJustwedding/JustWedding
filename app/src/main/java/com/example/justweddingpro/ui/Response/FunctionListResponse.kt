package com.example.justweddingpro.ui.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class FunctionListResponse {

    @SerializedName("functionDetails")
    @Expose
    private var functionDetails: List<FunctionDetail?>? = null

    fun getFunctionDetails(): List<FunctionDetail?>? {
        return functionDetails
    }

    fun setFunctionDetails(functionDetails: List<FunctionDetail?>?) {
        this.functionDetails = functionDetails
    }


    class FunctionDetail {
        @SerializedName("eventfunctionId")
        @Expose
        var eventfunctionId: Int? = null

        @SerializedName("pax")
        @Expose
        var pax: Int? = null

        @SerializedName("starttime")
        @Expose
        var starttime: String? = null

        @SerializedName("endtime")
        @Expose
        var endtime: String? = null

        @SerializedName("venueName")
        @Expose
        var venueName: String? = null

        @SerializedName("functionId")
        @Expose
        var functionId: Int? = null

        @SerializedName("eventId")
        @Expose
        var eventId: Int? = null

        @SerializedName("functionName")
        @Expose
        var functionName: String? = null
    }
}