package com.example.justweddingpro.ui.Response

import com.example.justweddingpro.Network.RequestModel.AddEventMenuPlanRequest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MenuItemDetailsResponse {

    @SerializedName("totalItem")
    @Expose
    private var totalItem: Int? = null

    @SerializedName("totalPage")
    @Expose
    private var totalPage: Int? = null

    @SerializedName("eventMenuPlanDetails")
    @Expose
    private var eventMenuPlanDetails: List<AddEventMenuPlanRequest.EventMenuPlanDetail>? = null

    @SerializedName("itemDetails")
    @Expose
    private var itemDetails: List<MenuItemDetail>? = null

    fun getEventMenuPlanDetails(): List<AddEventMenuPlanRequest.EventMenuPlanDetail>? {
        return eventMenuPlanDetails
    }

    fun setEventMenuPlanDetails(eventMenuPlanDetails: List<AddEventMenuPlanRequest.EventMenuPlanDetail>?) {
        this.eventMenuPlanDetails = eventMenuPlanDetails
    }

    fun getTotalItem(): Int? {
        return totalItem
    }

    fun setTotalItem(totalItem: Int) {
        this.totalItem = totalItem
    }

    fun getTotalPage(): Int? {
        return totalPage
    }

    fun setTotalPage(totalPage: Int) {
        this.totalPage = totalPage
    }

    fun getItemDetails(): List<MenuItemDetail>? {
        return itemDetails
    }

    fun setItemDetails(itemDetails: List<MenuItemDetail>?) {
        this.itemDetails = itemDetails
    }

    class MenuItemDetail {

        @SerializedName("id")
        @Expose
        private var id: Int? = null

        @SerializedName("itemname")
        @Expose
        private var itemname: String? = null

        @SerializedName("itemslogan")
        @Expose
        private var itemslogan: String? = null

        @SerializedName("itemnamelanguage")
        @Expose
        private var itemnamelanguage: String? = null

        @SerializedName("itemprice")
        @Expose
        private var itemprice: Double? = null

        @SerializedName("itemimage")
        @Expose
        private var itemimage: String? = null

        @SerializedName("video")
        @Expose
        private var video: String? = null

        @SerializedName("menucategory_id")
        @Expose
        private var menucategoryId: Int? = null

        @SerializedName("submenu_id")
        @Expose
        private var submenuId: Int? = null

        @SerializedName("isSelected")
        @Expose
        private var isSelected: Boolean? = null

        fun getId(): Int? {
            return id
        }

        fun setId(id: Int?) {
            this.id = id
        }

        fun getItemname(): String? {
            return itemname
        }

        fun setItemname(itemname: String?) {
            this.itemname = itemname
        }

        fun getItemslogan(): String? {
            return itemslogan
        }

        fun setItemslogan(itemslogan: String?) {
            this.itemslogan = itemslogan
        }

        fun getItemnamelanguage(): String? {
            return itemnamelanguage
        }

        fun setItemnamelanguage(itemnamelanguage: String?) {
            this.itemnamelanguage = itemnamelanguage
        }

        fun getItemprice(): Double? {
            return itemprice
        }

        fun setItemprice(itemprice: Double?) {
            this.itemprice = itemprice
        }

        fun getItemimage(): String? {
            return itemimage
        }

        fun setItemimage(itemimage: String?) {
            this.itemimage = itemimage
        }

        fun getVideo(): String? {
            return video
        }

        fun setVideo(video: String?) {
            this.video = video
        }

        fun getMenucategoryId(): Int? {
            return menucategoryId
        }

        fun setMenucategoryId(menucategoryId: Int?) {
            this.menucategoryId = menucategoryId
        }

        fun getSubmenuId(): Int? {
            return submenuId
        }

        fun setSubmenuId(submenuId: Int?) {
            this.submenuId = submenuId
        }

        fun getIsSelected(): Boolean? {
            return isSelected
        }

        fun setIsSelected(isSelected: Boolean?) {
            this.isSelected = isSelected
        }
    }
}