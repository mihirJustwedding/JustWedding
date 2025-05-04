package com.example.justweddingpro.ui.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddUserResponse {
    @SerializedName("clientUserDetails")
    @Expose
    private var clientUserDetails: List<ClientUserDetail>? = null

    @SerializedName("moduleName")
    @Expose
    private var moduleName: String? = null

    @SerializedName("moduleId")
    @Expose
    private var moduleId: Int? = null

    @SerializedName("fileType")
    @Expose
    private var fileType: String? = null

    fun getClientUserDetails(): List<ClientUserDetail?>? {
        return clientUserDetails
    }

    fun setClientUserDetails(clientUserDetails: List<ClientUserDetail>?) {
        this.clientUserDetails = clientUserDetails
    }

    fun getModuleName(): String? {
        return moduleName
    }

    fun setModuleName(moduleName: String?) {
        this.moduleName = moduleName
    }

    fun getModuleId(): Int? {
        return moduleId
    }

    fun setModuleId(moduleId: Int?) {
        this.moduleId = moduleId
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

        @SerializedName("emailId")
        @Expose
        var emailId: String? = null

        @SerializedName("age")
        @Expose
        var age: Int? = null

        @SerializedName("clientCategory")
        @Expose
        var clientCategory: String? = null
    }
}