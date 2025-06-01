package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LoginResponse {

    @SerializedName("clientUserDetails")
    @Expose
    var clientUserDetails: List<ClientUserDetail>? = null

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

        @SerializedName("category")
        @Expose
        var category: String? = null

        @SerializedName("emailId")
        @Expose
        var emailId: String? = null

        @SerializedName("imgUrl")
        @Expose
        var imageUrl: String? = null
    }
}