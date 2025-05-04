package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AddItemsRequest {
    @SerializedName("itemId")
    @Expose
    private var itemId: String? = null

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
    private var itemprice: Int? = null

    @SerializedName("menucategoryId")
    @Expose
    private var menucategoryId: Int? = null

    @SerializedName("submenuId")
    @Expose
    private var submenuId: Int? = null

    fun getItemId(): String? {
        return itemId
    }

    fun setItemId(itemId: String?) {
        this.itemId = itemId
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

    fun getItemprice(): Int? {
        return itemprice
    }

    fun setItemprice(itemprice: Int?) {
        this.itemprice = itemprice
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
}