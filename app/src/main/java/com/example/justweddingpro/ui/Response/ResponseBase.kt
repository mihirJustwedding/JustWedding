package com.example.justweddingpro.ui.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseBase<T> {
    @SerializedName("success")
    @Expose
    var mSuccess: Boolean? = null

    @SerializedName("error")
    @Expose
    var mError: String? = null

    @SerializedName("msg")
    @Expose
    var mMessage: String? = null

    @SerializedName("data")
    @Expose
    var mData: T? = null

    @SerializedName("filePath")
    @Expose
    var filePath: String? = null
}