package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class RegisterRequest {
    @SerializedName("clientUserId")
    @Expose
    private var clientUserId: Int? = null

    @SerializedName("companyName")
    @Expose
    private var companyName: String? = null

    @SerializedName("cityName")
    @Expose
    private var cityName: String? = null

    @SerializedName("companyAddress")
    @Expose
    private var companyAddress: String? = null

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

    @SerializedName("usedreferralCode")
    @Expose
    private var usedreferralCode: String? = null

    @SerializedName("confirmPassword")
    @Expose
    private var confirmPassword: String? = null

    @SerializedName("clientId")
    @Expose
    private var clientId: Int? = null

    fun getClientUserId(): Int? {
        return clientUserId
    }

    fun setClientUserId(clientUserId: Int?) {
        this.clientUserId = clientUserId
    }

    fun getCompanyName(): String? {
        return companyName
    }

    fun setCompanyName(companyName: String?) {
        this.companyName = companyName
    }

    fun getCityName(): String? {
        return cityName
    }

    fun setCityName(cityName: String?) {
        this.cityName = cityName
    }

    fun getCompanyAddress(): String? {
        return companyAddress
    }

    fun setCompanyAddress(companyAddress: String?) {
        this.companyAddress = companyAddress
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

    fun getUsedreferralCode(): String? {
        return usedreferralCode
    }

    fun setUsedreferralCode(usedreferralCode: String?) {
        this.usedreferralCode = usedreferralCode
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
}