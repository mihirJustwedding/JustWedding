package com.example.justweddingpro.Network.RequestModel

data class AddEventRequest(
    var eventId: Int,
    var status: Int,
    var eventDate: String,
    var eventname: String,
    var foodNotes: String,
    var contactNo: String,
    var eventRemarks: String,
    var venueName: String,
    var brideName: String,
    var groomName: String,
    var eventFunctionDetails: ArrayList<EventFunctionDetail>,
    var clientuserId: Int,
    var partyaccId: Int,
) {
    constructor() : this(
        0, 0, "", "", "", "",  "", "", "", "", ArrayList(), 0, 0
    )
}

data class EventFunctionDetail(
    var eventFunctionId: Int,
    var pax: Int,
    var starttime: String,
    var endtime: String,
    var vanueName: String,
    var functionId: Int,
    var functionName: String,
)