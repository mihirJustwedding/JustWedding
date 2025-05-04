package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class FunctionAssignRequest {

    @SerializedName("clientUserId")
    @Expose
    private var clientUserId: List<Int?>? = null

    @SerializedName("eventId")
    @Expose
    private var eventId: Int? = null

    @SerializedName("functionId")
    @Expose
    private var functionId: Int? = null

    fun getClientUserId(): List<Int?>? {
        return clientUserId
    }

    fun setClientUserId(clientUserId: List<Int?>?) {
        this.clientUserId = clientUserId
    }

    fun getEventId(): Int? {
        return eventId
    }

    fun setEventId(eventId: Int?) {
        this.eventId = eventId
    }

    fun getFunctionId(): Int? {
        return functionId
    }

    fun setFunctionId(functionId: Int?) {
        this.functionId = functionId
    }
}