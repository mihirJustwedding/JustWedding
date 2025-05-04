package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class AddTableRequest {

    @SerializedName("tableId")
    @Expose
    private var tableId: Int? = null

    @SerializedName("tableName")
    @Expose
    private var tableName: String? = null

    @SerializedName("isActive")
    @Expose
    private var isActive: Int? = null

    @SerializedName("clientUserId")
    @Expose
    private var clientUserId: String? = null

    fun getClientUserId(): String? {
        return clientUserId
    }

    fun setClientUserId(clientUserId: String?) {
        this.clientUserId = clientUserId
    }

    fun getTableId(): Int? {
        return tableId
    }

    fun setTableId(tableId: Int?) {
        this.tableId = tableId
    }

    fun getTableName(): String? {
        return tableName
    }

    fun setTableName(tableName: String?) {
        this.tableName = tableName
    }

    fun getIsActive(): Int? {
        return isActive
    }

    fun setIsActive(isActive: Int?) {
        this.isActive = isActive
    }
}