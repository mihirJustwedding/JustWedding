package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddEventResponse {
    @SerializedName("eventId")
    @Expose
    var eventId: String? = null
}