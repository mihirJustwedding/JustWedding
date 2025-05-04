package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class AddPartyRequest {

    @SerializedName("partyaccId")
    @Expose
    private var partyaccId: Int? = null

    @SerializedName("partyname")
    @Expose
    private var partyname: String? = null

    @SerializedName("emailid")
    @Expose
    private var emailid: String? = null

    @SerializedName("watsappno")
    @Expose
    private var watsappno: String? = null

    @SerializedName("birthdate")
    @Expose
    private var birthdate: String? = null

    @SerializedName("anniversarydate")
    @Expose
    private var anniversarydate: String? = null

    @SerializedName("clientUserId")
    @Expose
    private var clientUserId: Int? = null

    @SerializedName("address")
    @Expose
    private var address: String? = null

    fun getPartyaccId(): Int? {
        return partyaccId
    }

    fun setPartyaccId(partyaccId: Int?) {
        this.partyaccId = partyaccId
    }

    fun getPartyname(): String? {
        return partyname
    }

    fun setPartyname(partyname: String?) {
        this.partyname = partyname
    }

    fun getEmailid(): String? {
        return emailid
    }

    fun setEmailid(emailid: String?) {
        this.emailid = emailid
    }

    fun getWatsappno(): String? {
        return watsappno
    }

    fun setWatsappno(watsappno: String?) {
        this.watsappno = watsappno
    }

    fun getBirthdate(): String? {
        return birthdate
    }

    fun setBirthdate(birthdate: String?) {
        this.birthdate = birthdate
    }

    fun getAnniversarydate(): String? {
        return anniversarydate
    }

    fun setAnniversarydate(anniversarydate: String?) {
        this.anniversarydate = anniversarydate
    }

    fun getClientUserId(): Int? {
        return clientUserId
    }

    fun setClientUserId(clientUserId: Int?) {
        this.clientUserId = clientUserId
    }

    fun getaddress(): String? {
        return address
    }

    fun setaddress(address: String?) {
        this.address = address
    }
}