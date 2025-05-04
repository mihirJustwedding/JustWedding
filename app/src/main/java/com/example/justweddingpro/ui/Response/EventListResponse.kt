package com.example.justweddingpro.ui.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class EventListResponse {

    @SerializedName("eventDetails")
    @Expose
    private var eventDetails: List<EventDetail?>? = null

    fun getEventDetails(): List<EventDetail?>? {
        return eventDetails
    }

    fun setEventDetails(eventDetails: List<EventDetail?>?) {
        this.eventDetails = eventDetails
    }


    class EventDetail {
        @SerializedName("eventId")
        @Expose
        var eventId: Int? = null

        @SerializedName("eventName")
        @Expose
        var eventName: String? = null

        @SerializedName("eventDate")
        @Expose
        var eventDate: String? = null

        @SerializedName("partyName")
        @Expose
        var partyName: String? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null

        @SerializedName("venueName")
        @Expose
        var venueName: String? = null

        @SerializedName("count")
        @Expose
        var count: Int? = null

        @SerializedName("color")
        @Expose
        var color: String? = null
    }
}