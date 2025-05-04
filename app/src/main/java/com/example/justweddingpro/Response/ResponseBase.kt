package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseBase<T> {
    @SerializedName("success")
    @Expose
    var mSuccess: String? = null

    @SerializedName("error")
    @Expose
    var mError: String? = null

    @SerializedName("msg")
    @Expose
    var mMessage: String? = null

    @SerializedName("data")
    @Expose
    var mData: T? = null
}