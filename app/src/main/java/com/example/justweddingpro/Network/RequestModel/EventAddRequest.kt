package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EventAddRequest {
    @SerializedName("eventId")
    @Expose
    private var eventId: Int? = null

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("eventDate")
    @Expose
    private var eventDate: String? = null

    @SerializedName("eventname")
    @Expose
    private var eventname: String? = null

    @SerializedName("foodNotes")
    @Expose
    private var foodNotes: String? = null

    @SerializedName("contactNo")
    @Expose
    private var contactNo: String? = null

    @SerializedName("address")
    @Expose
    private var address: String? = null

    @SerializedName("eventRemarks")
    @Expose
    private var eventRemarks: String? = null

    @SerializedName("venueName")
    @Expose
    private var venueName: String? = null

    @SerializedName("eventFunctionDetails")
    @Expose
    private var eventFunctionDetails: List<EventFunctionDetail?>? = null

    @SerializedName("clientuserId")
    @Expose
    private var clientuserId: Int? = null

    @SerializedName("partyaccId")
    @Expose
    private var partyaccId: Int? = null

    fun getEventId(): Int? {
        return eventId
    }

    fun setEventId(eventId: Int?) {
        this.eventId = eventId
    }

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getEventDate(): String? {
        return eventDate
    }

    fun setEventDate(eventDate: String?) {
        this.eventDate = eventDate
    }

    fun getEventname(): String? {
        return eventname
    }

    fun setEventname(eventname: String?) {
        this.eventname = eventname
    }

    fun getFoodNotes(): String? {
        return foodNotes
    }

    fun setFoodNotes(foodNotes: String?) {
        this.foodNotes = foodNotes
    }

    fun getContactNo(): String? {
        return contactNo
    }

    fun setContactNo(contactNo: String?) {
        this.contactNo = contactNo
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun getEventRemarks(): String? {
        return eventRemarks
    }

    fun setEventRemarks(eventRemarks: String?) {
        this.eventRemarks = eventRemarks
    }

    fun getVenueName(): String? {
        return venueName
    }

    fun setVenueName(venueName: String?) {
        this.venueName = venueName
    }

    fun getEventFunctionDetails(): List<EventFunctionDetail?>? {
        return eventFunctionDetails
    }

    fun setEventFunctionDetails(eventFunctionDetails: List<EventFunctionDetail?>?) {
        this.eventFunctionDetails = eventFunctionDetails
    }

    fun getClientuserId(): Int? {
        return clientuserId
    }

    fun setClientuserId(clientuserId: Int?) {
        this.clientuserId = clientuserId
    }

    fun getPartyaccId(): Int? {
        return partyaccId
    }

    fun setPartyaccId(partyaccId: Int?) {
        this.partyaccId = partyaccId
    }

    class EventFunctionDetail {
        @SerializedName("eventFunctionId")
        @Expose
        private var eventFunctionId: Int? = null

        @SerializedName("pax")
        @Expose
        private var pax: Int? = null

        @SerializedName("starttime")
        @Expose
        private var starttime: String? = null

        @SerializedName("endtime")
        @Expose
        private var endtime: String? = null

        @SerializedName("vanueName")
        @Expose
        private var vanueName: String? = null

        @SerializedName("functionId")
        @Expose
        private var functionId: Int? = null

        @SerializedName("functionName")
        @Expose
        private var functionName: String? = null

        fun getEventFunctionId(): Int? {
            return eventFunctionId
        }

        fun setEventFunctionId(eventFunctionId: Int?) {
            this.eventFunctionId = eventFunctionId
        }

        fun getPax(): Int? {
            return pax
        }

        fun setPax(pax: Int?) {
            this.pax = pax
        }

        fun getStarttime(): String? {
            return starttime
        }

        fun setStarttime(starttime: String?) {
            this.starttime = starttime
        }

        fun getEndtime(): String? {
            return endtime
        }

        fun setEndtime(endtime: String?) {
            this.endtime = endtime
        }

        fun getVanueName(): String? {
            return vanueName
        }

        fun setVanueName(vanueName: String?) {
            this.vanueName = vanueName
        }

        fun getFunctionId(): Int? {
            return functionId
        }

        fun setFunctionId(functionId: Int?) {
            this.functionId = functionId
        }

        fun getFunctionName(): String? {
            return functionName
        }

        fun setFunctionName(functionName: String?) {
            this.functionName = functionName
        }
    }
}
