package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class RegisterResponse {
    @SerializedName("clientUserDetails")
    @Expose
    private var clientUserDetails: List<ClientUserDetail?>? = null

    @SerializedName("fileType")
    @Expose
    private var fileType: String? = null

    fun getClientUserDetails(): List<ClientUserDetail?>? {
        return clientUserDetails
    }

    fun setClientUserDetails(clientUserDetails: List<ClientUserDetail?>?) {
        this.clientUserDetails = clientUserDetails
    }

    fun getFileType(): String? {
        return fileType
    }

    fun setFileType(fileType: String?) {
        this.fileType = fileType
    }


    class ClientUserDetail {
        @SerializedName("clientUserId")
        @Expose
        var clientUserId: Int? = null

        @SerializedName("companyName")
        @Expose
        var companyName: String? = null

        @SerializedName("cityName")
        @Expose
        var cityName: String? = null

        @SerializedName("companyAddress")
        @Expose
        var companyAddress: String? = null

        @SerializedName("clientId")
        @Expose
        var clientId: Int? = null

        @SerializedName("companyEmail")
        @Expose
        var companyEmail: String? = null

        @SerializedName("mobileNo")
        @Expose
        var mobileNo: String? = null

        @SerializedName("userName")
        @Expose
        var userName: String? = null

        @SerializedName("category")
        @Expose
        var category: Any? = null

        @SerializedName("emailId")
        @Expose
        var emailId: Any? = null
    }
}