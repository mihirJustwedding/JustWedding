package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class PartyDetailsResponse {
    @SerializedName("partyAccountMasterDetails")
    @Expose
    var partyAccountMasterDetails: List<PartyAccountMasterDetail>? = null


    class PartyAccountMasterDetail {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("partyname")
        @Expose
        var partyname: String? = null

        @SerializedName("emailid")
        @Expose
        var emailid: String? = null

        @SerializedName("watsappno")
        @Expose
        var watsappno: String? = null

        @SerializedName("birthdate")
        @Expose
        var birthdate: String? = null

        @SerializedName("anniversarydate")
        @Expose
        var anniversarydate: String? = null

        @SerializedName("category")
        @Expose
        var category: Category? = null
    }


    class Category {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("catname")
        @Expose
        var catname: String? = null

        @SerializedName("cattype")
        @Expose
        var cattype: String? = null
    }

}