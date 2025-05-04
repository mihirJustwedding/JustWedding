package com.example.justweddingpro.ui.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class EvenMenuPlanningDetails {

    @SerializedName("eventMenuPlanDetails")
    @Expose
    private var eventMenuPlanDetails: List<EventMenuPlanDetail?>? = null

    fun getEventMenuPlanDetails(): List<EventMenuPlanDetail?>? {
        return eventMenuPlanDetails
    }

    fun setEventMenuPlanDetails(eventMenuPlanDetails: List<EventMenuPlanDetail?>?) {
        this.eventMenuPlanDetails = eventMenuPlanDetails
    }


    class ItemsDetail {
        @SerializedName("itemId")
        @Expose
        var itemId: Int? = null

        @SerializedName("itemName")
        @Expose
        var itemName: String? = null

        @SerializedName("itemImage")
        @Expose
        var itemImage: String? = null

        @SerializedName("itemSlogan")
        @Expose
        var itemSlogan: String? = null

        @SerializedName("item_sortorder")
        @Expose
        var itemSortorder: Int? = null
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