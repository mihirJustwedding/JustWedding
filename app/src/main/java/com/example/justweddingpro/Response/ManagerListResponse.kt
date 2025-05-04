package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ManagerListResponse {
    @SerializedName("clientUserDetails")
    @Expose
    private var clientUserDetails: List<ClientUserDetail?>? = null

    fun getClientUserDetails(): List<ClientUserDetail?>? {
        return clientUserDetails
    }

    fun setClientUserDetails(clientUserDetails: List<ClientUserDetail?>?) {
        this.clientUserDetails = clientUserDetails
    }


    class ClientUserDetail {

        @SerializedName("siteUserId")
        @Expose
        var eventfunctionId: Int? = null

        @SerializedName("userName")
        @Expose
        var userName: String? = null

        @SerializedName("mobileNo")
        @Expose
        var mobileNo: String? = null

        @SerializedName("emailId")
        @Expose
        var emailId: String? = null

        @SerializedName("age")
        @Expose
        var age: Int? = null

        @SerializedName("cityName")
        @Expose
        var cityName: String? = null

        @SerializedName("imageUrl")
        @Expose
        var imageUrl: String? = null
    }
}