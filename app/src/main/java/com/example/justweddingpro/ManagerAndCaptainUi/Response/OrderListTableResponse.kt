package com.example.justweddingpro.ManagerAndCaptainUi.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class OrderListTableResponse {
    @SerializedName("orderTableDetails")
    @Expose
    var orderTableDetails: List<OrderTableDetail?>? = null

    class OrderTableDetail {
        @SerializedName("orderTableId")
        @Expose
        var orderTableId: Int? = null

        @SerializedName("tableId")
        @Expose
        var tableId: Int? = null

        @SerializedName("tableName")
        @Expose
        var tableName: String? = null

        @SerializedName("memberName")
        @Expose
        var memberName: String? = null

        @SerializedName("itemId")
        @Expose
        var itemId: Int? = null

        @SerializedName("itemName")
        @Expose
        var itemName: String? = null

        @SerializedName("itemImage")
        @Expose
        var itemImage: String? = null

        @SerializedName("createdDatetime")
        @Expose
        var createdDatetime: String? = null

        @SerializedName("qty")
        @Expose
        var qty: Int? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }
}