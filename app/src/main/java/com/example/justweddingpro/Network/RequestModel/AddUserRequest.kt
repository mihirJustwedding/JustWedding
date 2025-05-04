package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class AddUserRequest {

    @SerializedName("clientUserId")
    @Expose
    private var clientUserId: Int? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("age")
    @Expose
    private var age: Int? = null

    @SerializedName("emailId")
    @Expose
    private var emailId: String? = null

    @SerializedName("companyEmail")
    @Expose
    private var companyEmail: String? = null

    @SerializedName("mobileNo")
    @Expose
    private var mobileNo: String? = null

    @SerializedName("userName")
    @Expose
    private var userName: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    @SerializedName("confirmPassword")
    @Expose
    private var confirmPassword: String? = null

    @SerializedName("clientId")
    @Expose
    private var clientId: Int? = null

    @SerializedName("clientCategory")
    @Expose
    private var clientCategory: String? = null

    @SerializedName("cityName")
    @Expose
    private var cityName: String? = null

    fun getClientUserId(): Int? {
        return clientUserId
    }

    fun setClientUserId(clientUserId: Int?) {
        this.clientUserId = clientUserId
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getAge(): Int? {
        return age
    }

    fun setAge(age: Int?) {
        this.age = age
    }

    fun getEmailId(): String? {
        return emailId
    }

    fun setEmailId(emailId: String?) {
        this.emailId = emailId
    }

    fun getCompanyEmail(): String? {
        return companyEmail
    }

    fun setCompanyEmail(companyEmail: String?) {
        this.companyEmail = companyEmail
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String?) {
        this.mobileNo = mobileNo
    }

    fun getUserName(): String? {
        return userName
    }

    fun setUserName(userName: String?) {
        this.userName = userName
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getConfirmPassword(): String? {
        return confirmPassword
    }

    fun setConfirmPassword(confirmPassword: String?) {
        this.confirmPassword = confirmPassword
    }

    fun getClientId(): Int? {
        return clientId
    }

    fun setClientId(clientId: Int?) {
        this.clientId = clientId
    }

    fun getClientCategory(): String? {
        return clientCategory
    }

    fun setClientCategory(clientCategory: String?) {
        this.clientCategory = clientCategory
    }

    fun getCityName(): String? {
        return cityName
    }

    fun setCityName(cityName: String?) {
        this.cityName = cityName
    }
}