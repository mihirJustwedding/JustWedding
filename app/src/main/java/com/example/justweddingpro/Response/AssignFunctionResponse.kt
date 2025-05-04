package com.example.justweddingpro.Response

import com.example.justweddingpro.ui.Response.AssignManagerAndCaptainResponse.FunctionManagerAssignDetail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class AssignFunctionResponse {

    @SerializedName("FunctionManagerAssignDetails")
    @Expose
    private var functionManagerAssignDetails: List<FunctionManagerAssignDetail?>? = null

    fun getFunctionManagerAssignDetails(): List<FunctionManagerAssignDetail?>? {
        return functionManagerAssignDetails
    }

    fun setFunctionManagerAssignDetails(functionManagerAssignDetails: List<FunctionManagerAssignDetail?>?) {
        this.functionManagerAssignDetails = functionManagerAssignDetails
    }


    class FunctionManagerAssignDetail {
        @SerializedName("functionAssignId")
        @Expose
        var functionAssignId: Int? = null

        @SerializedName("clientUserId")
        @Expose
        var clientUserId: Int? = null

        @SerializedName("clientUserName")
        @Expose
        var clientUserName: String? = null

        @SerializedName("eventId")
        @Expose
        var eventId: Int? = null

        @SerializedName("eventName")
        @Expose
        var eventName: String? = null

        @SerializedName("functionId")
        @Expose
        var functionId: Int? = null

        @SerializedName("functionName")
        @Expose
        var functionName: String? = null

        @SerializedName("startTime")
        @Expose
        var startTime: String? = null

        @SerializedName("endTime")
        @Expose
        var endTime: String? = null
    }
}