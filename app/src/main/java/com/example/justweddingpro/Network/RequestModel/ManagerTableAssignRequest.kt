package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ManagerTableAssignRequest {

    @SerializedName("clientUserId")
    @Expose
    private var clientUserId: Int? = null

    @SerializedName("tableId")
    @Expose
    private var tableId: List<Int>? = null

    @SerializedName("eventId")
    @Expose
    private var eventId: Int? = null

    @SerializedName("functionId")
    @Expose
    private var functionId: Int? = null

    @SerializedName("functionManagaerId")
    @Expose
    private var functionManagaerId: Int? = null

    fun getClientUserId(): Int? {
        return clientUserId
    }

    fun setClientUserId(clientUserId: Int?) {
        this.clientUserId = clientUserId
    }

    fun getTableId(): List<Int?>? {
        return tableId
    }

    fun setTableId(tableId: List<Int>?) {
        this.tableId = tableId
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

    fun getFunctionManagaerId(): Int? {
        return functionManagaerId
    }

    fun setFunctionManagaerId(functionManagaerId: Int?) {
        this.functionManagaerId = functionManagaerId
    }
}