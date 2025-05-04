package com.example.justweddingpro.Response

import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse.ManagerTableAssignDetail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class AssignTableListResponse {

    @SerializedName("managerTableAssignDetails")
    @Expose
    private var managerTableAssignDetails: List<ManagerTableAssignDetail?>? = null

    fun getManagerTableAssignDetails(): List<ManagerTableAssignDetail?>? {
        return managerTableAssignDetails
    }

    fun setManagerTableAssignDetails(managerTableAssignDetails: List<ManagerTableAssignDetail?>?) {
        this.managerTableAssignDetails = managerTableAssignDetails
    }

}