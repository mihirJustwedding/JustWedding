package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class TableListResponse {

    @SerializedName("tableMasterDetails")
    @Expose
    private var tableMasterDetails: List<TableMasterDetail?>? = null

    fun getTableMasterDetails(): List<TableMasterDetail?>? {
        return tableMasterDetails
    }

    fun setTableMasterDetails(tableMasterDetails: List<TableMasterDetail?>?) {
        this.tableMasterDetails = tableMasterDetails
    }

    class TableMasterDetail {
        @SerializedName("tableId")
        @Expose
        var tableId: Int? = null

        @SerializedName("tableName")
        @Expose
        var tableName: String? = null

        @SerializedName("isActive")
        @Expose
        var isActive: Int? = null
    }

}