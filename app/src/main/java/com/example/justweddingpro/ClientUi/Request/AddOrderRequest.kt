package com.example.justweddingpro.ClientUi.Request

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class AddOrderRequest {

    @SerializedName("clientUserId")
    @Expose
    private var clientUserId: Int? = null

    @SerializedName("eventId")
    @Expose
    private var eventId: Int? = null

    @SerializedName("functionId")
    @Expose
    private var functionId: Int? = null

    @SerializedName("tableId")
    @Expose
    private var tableId: Int? = null

    @SerializedName("itemDetails")
    @Expose
    private var itemDetails: List<ItemDetail>? = null

    fun getClientUserId(): Int? {
        return clientUserId
    }

    fun setClientUserId(clientUserId: Int?) {
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

    fun getTableId(): Int? {
        return tableId
    }

    fun setTableId(tableId: Int?) {
        this.tableId = tableId
    }

    fun getItemDetails(): List<ItemDetail?>? {
        return itemDetails
    }

    fun setItemDetails(itemDetails: List<ItemDetail?>?) {
        this.itemDetails = itemDetails as List<ItemDetail>?
    }

    class ItemDetail {
        @SerializedName("itemId")
        @Expose
        private var itemId: Int? = null

        @SerializedName("itemName")
        @Expose
        private var itemName: String? = null

        @SerializedName("qty")
        @Expose
        private var qty: Int? = null

        fun getItemId(): Int? {
            return itemId
        }

        fun setItemId(itemId: Int?) {
            this.itemId = itemId
        }

        fun getItemName(): String? {
            return itemName
        }

        fun setItemName(itemName: String?) {
            this.itemName = itemName
        }

        fun getQty(): Int? {
            return qty
        }

        fun setQty(qty: Int?) {
            this.qty = qty
        }
    }
}