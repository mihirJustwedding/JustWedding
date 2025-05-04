package com.example.justweddingpro.ui.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class EventDetailsResponse {

    @SerializedName("eventMasterDetails")
    @Expose
    private var eventMasterDetails: List<EventMasterDetail?>? = null

    fun getEventMasterDetails(): List<EventMasterDetail?>? {
        return eventMasterDetails
    }

    fun setEventMasterDetails(eventMasterDetails: List<EventMasterDetail?>?) {
        this.eventMasterDetails = eventMasterDetails
    }

    class EventMasterDetail {
        @SerializedName("eventId")
        @Expose
        var eventId: Int? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null

        @SerializedName("eventname")
        @Expose
        var eventname: String? = null

        @SerializedName("eventDate")
        @Expose
        var eventDate: String? = null

        @SerializedName("contactNo")
        @Expose
        var contactNo: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("foodNotes")
        @Expose
        var foodNotes: String? = null

        @SerializedName("eventRemarks")
        @Expose
        var eventRemarks: String? = null

        @SerializedName("brideName")
        @Expose
        var brideName: String? = null

        @SerializedName("groomName")
        @Expose
        var groomName: String? = null

        @SerializedName("venueName")
        @Expose
        var venueName: String? = null

        @SerializedName("clientuserId")
        @Expose
        var clientuserId: Int? = null

        @SerializedName("partyaccId")
        @Expose
        var partyaccId: Int? = null

        @SerializedName("partyName")
        @Expose
        var partyName: String? = null

        @SerializedName("eventFunctionDetails")
        @Expose
        var eventFunctionDetails: List<EventFunctionDetail>? = null
    }


    class EventFunctionDetail {
        @SerializedName("eventfunctionId")
        @Expose
        var eventfunctionId: Int? = null

        @SerializedName("pax")
        @Expose
        var pax: Int? = null

        @SerializedName("starttime")
        @Expose
        var starttime: String? = null

        @SerializedName("endtime")
        @Expose
        var endtime: String? = null

        @SerializedName("venueName")
        @Expose
        var venueName: String? = null

        @SerializedName("functionId")
        @Expose
        var functionId: Int? = null

        @SerializedName("eventId")
        @Expose
        var eventId: Int? = null

        @SerializedName("functionName")
        @Expose
        var functionName: String? = null
    }
}