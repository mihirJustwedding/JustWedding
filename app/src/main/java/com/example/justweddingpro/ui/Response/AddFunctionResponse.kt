package com.example.justweddingpro.ui.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AddFunctionResponse {

    @SerializedName("moduleName")
    @Expose
    private var moduleName: String? = null

    @SerializedName("moduleId")
    @Expose
    private var moduleId: Int? = null

    @SerializedName("fileType")
    @Expose
    private var fileType: String? = null

    fun getModuleName(): String? {
        return moduleName
    }

    fun setModuleName(moduleName: String?) {
        this.moduleName = moduleName
    }

    fun getModuleId(): Int? {
        return moduleId
    }

    fun setModuleId(moduleId: Int?) {
        this.moduleId = moduleId
    }

    fun getFileType(): String? {
        return fileType
    }

    fun setFileType(fileType: String?) {
        this.fileType = fileType
    }
}