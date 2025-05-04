package com.example.justweddingpro.ClientUi.Request

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class FeedBackRequest {

    @SerializedName("rating")
    @Expose
    private var rating: Int? = null

    @SerializedName("personName")
    @Expose
    private var personName: String? = null

    @SerializedName("mobileNo")
    @Expose
    private var mobileNo: String? = null

    @SerializedName("cityName")
    @Expose
    private var cityName: String? = null

    @SerializedName("instagramId")
    @Expose
    private var instagramId: String? = null

    @SerializedName("dateOfBirth")
    @Expose
    private var dateOfBirth: String? = null

    @SerializedName("anniversaryDate")
    @Expose
    private var anniversaryDate: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("clientId")
    @Expose
    private var clientId: Int? = null

    @SerializedName("eventId")
    @Expose
    private var eventId: String? = null

    fun getRating(): Int? {
        return rating
    }

    fun setRating(rating: Int?) {
        this.rating = rating
    }

    fun getPersonName(): String? {
        return personName
    }

    fun setPersonName(personName: String?) {
        this.personName = personName
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String?) {
        this.mobileNo = mobileNo
    }

    fun getCityName(): String? {
        return cityName
    }

    fun setCityName(cityName: String?) {
        this.cityName = cityName
    }

    fun getInstagramId(): String? {
        return instagramId
    }

    fun setInstagramId(instagramId: String?) {
        this.instagramId = instagramId
    }

    fun getDateOfBirth(): String? {
        return dateOfBirth
    }

    fun setDateOfBirth(dateOfBirth: String?) {
        this.dateOfBirth = dateOfBirth
    }

    fun getAnniversaryDate(): String? {
        return anniversaryDate
    }

    fun setAnniversaryDate(anniversaryDate: String?) {
        this.anniversaryDate = anniversaryDate
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getClientId(): Int? {
        return clientId
    }

    fun setClientId(clientId: Int?) {
        this.clientId = clientId
    }

    fun getEventId(): String? {
        return eventId
    }

    fun setEventId(eventId: String?) {
        this.eventId = eventId
    }
}