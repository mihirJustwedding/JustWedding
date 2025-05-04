package com.example.justweddingpro.ClientUi.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ManagerTableListResponse {

    @SerializedName("managerTableAssignDetails")
    @Expose
    private var managerTableAssignDetails: List<ManagerTableAssignDetail?>? = null

    fun getManagerTableAssignDetails(): List<ManagerTableAssignDetail?>? {
        return managerTableAssignDetails
    }

    fun setManagerTableAssignDetails(managerTableAssignDetails: List<ManagerTableAssignDetail?>?) {
        this.managerTableAssignDetails = managerTableAssignDetails
    }

    class ManagerTableAssignDetail {
        @SerializedName("tableAssignId")
        @Expose
        private var tableAssignId: Int? = null

        @SerializedName("clientUserId")
        @Expose
        private var clientUserId: Int? = null

        @SerializedName("tableId")
        @Expose
        private var tableId: Int? = null

        @SerializedName("eventId")
        @Expose
        private var eventId: Int? = null

        @SerializedName("functionId")
        @Expose
        private var functionId: Int? = null

        @SerializedName("functionAssignId")
        @Expose
        private var functionAssignId: Int? = null

        @SerializedName("clientUserName")
        @Expose
        private var clientUserName: String? = null

        @SerializedName("tableName")
        @Expose
        private var tableName: String? = null

        @SerializedName("eventName")
        @Expose
        private var eventName: String? = null

        @SerializedName("functionName")
        @Expose
        private var functionName: String? = null

        fun getTableAssignId(): Int? {
            return tableAssignId
        }

        fun setTableAssignId(tableAssignId: Int?) {
            this.tableAssignId = tableAssignId
        }

        fun getClientUserId(): Int? {
            return clientUserId
        }

        fun setClientUserId(clientUserId: Int?) {
            this.clientUserId = clientUserId
        }

        fun getTableId(): Int? {
            return tableId
        }

        fun setTableId(tableId: Int?) {
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

        fun getFunctionAssignId(): Int? {
            return functionAssignId
        }

        fun setFunctionAssignId(functionAssignId: Int?) {
            this.functionAssignId = functionAssignId
        }

        fun getClientUserName(): String? {
            return clientUserName
        }

        fun setClientUserName(clientUserName: String?) {
            this.clientUserName = clientUserName
        }

        fun getTableName(): String? {
            return tableName
        }

        fun setTableName(tableName: String?) {
            this.tableName = tableName
        }

        fun getEventName(): String? {
            return eventName
        }

        fun setEventName(eventName: String?) {
            this.eventName = eventName
        }

        fun getFunctionName(): String? {
            return functionName
        }

        fun setFunctionName(functionName: String?) {
            this.functionName = functionName
        }
    }
}