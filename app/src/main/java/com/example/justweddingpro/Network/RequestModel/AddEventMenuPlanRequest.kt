package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AddEventMenuPlanRequest {

    @SerializedName("eventmenuId")
    @Expose
    private var eventmenuId: Int? = null

    @SerializedName("eventId")
    @Expose
    private var eventId: Int? = null

    @SerializedName("functionId")
    @Expose
    private var functionId: Int? = null

    @SerializedName("pax")
    @Expose
    private var pax: Int? = null

    @SerializedName("price")
    @Expose
    private var price: Int? = null

    @SerializedName("sortorder")
    @Expose
    private var sortorder: Int? = null

    @SerializedName("eventMenuPlanDetails")
    @Expose
    private var eventMenuPlanDetails: List<EventMenuPlanDetail>? = null

    fun getEventmenuId(): Int? {
        return eventmenuId
    }

    fun setEventmenuId(eventmenuId: Int?) {
        this.eventmenuId = eventmenuId
    }

    fun getEventId(): Int? {
        return eventId
    }

    fun setEventId(eventId: Int?) {
        this.eventId = eventId
    }

    fun getFunctionId(): Int? {
        return functionId
    }

    fun setFunctionId(functionId: Int?) {
        this.functionId = functionId
    }

    fun getPax(): Int? {
        return pax
    }

    fun setPax(pax: Int?) {
        this.pax = pax
    }

    fun getPrice(): Int? {
        return price
    }

    fun setPrice(price: Int?) {
        this.price = price
    }

    fun getSortorder(): Int? {
        return sortorder
    }

    fun setSortorder(sortorder: Int?) {
        this.sortorder = sortorder
    }

    fun getEventMenuPlanDetails(): List<EventMenuPlanDetail>? {
        return eventMenuPlanDetails
    }

    fun setEventMenuPlanDetails(eventMenuPlanDetails: List<EventMenuPlanDetail>?) {
        this.eventMenuPlanDetails = eventMenuPlanDetails
    }

    class EventMenuPlanDetail {
        @SerializedName("eventmenudetailsId")
        @Expose
        private var eventmenudetailsId: Int? = null

        @SerializedName("starttime")
        @Expose
        private var starttime: String? = null

        @SerializedName("instruction")
        @Expose
        private var instruction: String? = null

        @SerializedName("menu_sortorder")
        @Expose
        private var menuSortorder: Int? = null

        @SerializedName("item_sortorder")
        @Expose
        private var itemSortorder: Int? = null

        @SerializedName("isadditionalcounter")
        @Expose
        private var isadditionalcounter: Int? = null

        @SerializedName("menuCategoryId")
        @Expose
        private var menuCategoryId: Int? = null

        @SerializedName("itemId")
        @Expose
        private var itemId: Int? = null

        @SerializedName("itemName")
        @Expose
        private var itemName: String? = null

        @SerializedName("menuName")
        @Expose
        private var menuName: String? = null

        @SerializedName("itemslogan")
        @Expose
        private var itemslogan: String? = null

        fun getItemslogan(): String? {
            return itemslogan
        }

        fun setItemslogan(itemslogan: String?) {
            this.itemslogan = itemslogan
        }

        fun getEventmenudetailsId(): Int? {
            return eventmenudetailsId
        }

        fun setEventmenudetailsId(eventmenudetailsId: Int?) {
            this.eventmenudetailsId = eventmenudetailsId
        }

        fun getStarttime(): String? {
            return starttime
        }

        fun setStarttime(starttime: String?) {
            this.starttime = starttime
        }

        fun getInstruction(): String? {
            return instruction
        }

        fun setInstruction(instruction: String?) {
            this.instruction = instruction
        }

        fun getMenuSortorder(): Int? {
            return menuSortorder
        }

        fun setMenuSortorder(menuSortorder: Int?) {
            this.menuSortorder = menuSortorder
        }

        fun getItemSortorder(): Int? {
            return itemSortorder
        }

        fun setItemSortorder(itemSortorder: Int?) {
            this.itemSortorder = itemSortorder
        }

        fun getIsadditionalcounter(): Int? {
            return isadditionalcounter
        }

        fun setIsadditionalcounter(isadditionalcounter: Int?) {
            this.isadditionalcounter = isadditionalcounter
        }

        fun getMenuCategoryId(): Int? {
            return menuCategoryId
        }

        fun setMenuCategoryId(menuCategoryId: Int?) {
            this.menuCategoryId = menuCategoryId
        }

        fun getItemId(): Int? {
            return itemId
        }

        fun setItemId(itemId: Int?) {
            this.itemId = itemId
        }

        fun getItemName(): String? {
            return itemName
        }

        fun setItemName(itemName: String?) {
            this.itemName = itemName
        }

        fun getMenuName(): String? {
            return menuName
        }

        fun setMenuName(menuName: String?) {
            this.menuName = menuName
        }
    }
}