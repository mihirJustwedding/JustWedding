package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class AddFunctionRequest {
    @SerializedName("functionId")
    @Expose
    private var functionId: Int? = null

    @SerializedName("functionname")
    @Expose
    private var functionname: String? = null

    @SerializedName("fun_start")
    @Expose
    private var funStart: String? = null

    @SerializedName("fun_end")
    @Expose
    private var funEnd: String? = null

    @SerializedName("price")
    @Expose
    private var price: String? = null

    @SerializedName("isforwebsite")
    @Expose
    private var isforwebsite: String? = null

    @SerializedName("clientuserId")
    @Expose
    private var clientuserId: Int? = null

    fun getFunctionId(): Int? {
        return functionId
    }

    fun setFunctionId(functionId: Int?) {
        this.functionId = functionId
    }

    fun getFunctionname(): String? {
        return functionname
    }

    fun setFunctionname(functionname: String?) {
        this.functionname = functionname
    }

    fun getFunStart(): String? {
        return funStart
    }

    fun setFunStart(funStart: String?) {
        this.funStart = funStart
    }

    fun getFunEnd(): String? {
        return funEnd
    }

    fun setFunEnd(funEnd: String?) {
        this.funEnd = funEnd
    }

    fun getPrice(): String? {
        return price
    }

    fun setPrice(price: String?) {
        this.price = price
    }

    fun getIsforwebsite(): String? {
        return isforwebsite
    }

    fun setIsforwebsite(isforwebsite: String?) {
        this.isforwebsite = isforwebsite
    }

    fun getClientuserId(): Int? {
        return clientuserId
    }

    fun setClientuserId(clientuserId: Int?) {
        this.clientuserId = clientuserId
    }
}