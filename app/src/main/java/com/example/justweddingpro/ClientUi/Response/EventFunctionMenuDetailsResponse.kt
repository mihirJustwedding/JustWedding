package com.example.justweddingpro.ClientUi.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EventFunctionMenuDetailsResponse {
    @SerializedName("eventMenuPlanDetails")
    @Expose
    private var eventMenuPlanDetails: List<EventMenuPlanDetail?>? = null

    fun getEventMenuPlanDetails(): List<EventMenuPlanDetail?>? {
        return eventMenuPlanDetails
    }

    fun setEventMenuPlanDetails(eventMenuPlanDetails: List<EventMenuPlanDetail?>?) {
        this.eventMenuPlanDetails = eventMenuPlanDetails
    }


    class EventMenuPlanDetail {
        @SerializedName("menuCategoryId")
        @Expose
        var menuCategoryId: Int? = null

        @SerializedName("menuName")
        @Expose
        var menuName: String? = null

        @SerializedName("menuImage")
        @Expose
        var menuImage: String? = null

        @SerializedName("menu_sortorder")
        @Expose
        var menuSortorder: Int? = null

        @SerializedName("itemsDetails")
        @Expose
        var itemsDetails: List<ItemsDetail>? = null
    }

}