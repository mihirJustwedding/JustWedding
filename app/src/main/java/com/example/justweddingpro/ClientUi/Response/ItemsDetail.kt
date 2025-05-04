package com.example.justweddingpro.ClientUi.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ItemsDetail {

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

    @SerializedName("eventMenuId")
    @Expose
    private var eventMenuId: Int? = null

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

    @SerializedName("itemImage")
    @Expose
    private var itemImage: String? = null

    @SerializedName("menuImage")
    @Expose
    private var menuImage: String? = null

    @SerializedName("itemSlogan")
    @Expose
    private var itemSlogan: String? = null

    @SerializedName("menuSlogan")
    @Expose
    private var menuSlogan: String? = null

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

    fun getEventMenuId(): Int? {
        return eventMenuId
    }

    fun setEventMenuId(eventMenuId: Int?) {
        this.eventMenuId = eventMenuId
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

    fun getItemImage(): String? {
        return itemImage
    }

    fun setItemImage(itemImage: String?) {
        this.itemImage = itemImage
    }

    fun getMenuImage(): String? {
        return menuImage
    }

    fun setMenuImage(menuImage: String?) {
        this.menuImage = menuImage
    }

    fun getItemSlogan(): String? {
        return itemSlogan
    }

    fun setItemSlogan(itemSlogan: String?) {
        this.itemSlogan = itemSlogan
    }

    fun getMenuSlogan(): String? {
        return menuSlogan
    }

    fun setMenuSlogan(menuSlogan: String?) {
        this.menuSlogan = menuSlogan
    }
}