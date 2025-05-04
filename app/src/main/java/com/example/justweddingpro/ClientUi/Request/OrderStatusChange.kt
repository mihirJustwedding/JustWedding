package com.example.justweddingpro.ClientUi.Request

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class OrderStatusChange {


    @SerializedName("orderTableId")
    @Expose
    private var orderTableId: List<Int>? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    fun getOrderTableId(): List<Int>? {
        return orderTableId
    }

    fun setOrderTableId(orderTableId: List<Int>?) {
        this.orderTableId = orderTableId
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }
}