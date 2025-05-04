package com.example.justweddingpro.Network.RequestModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddCategoryRequest {

    @SerializedName("menucategoryId")
    @Expose
    private var menucategoryId: Int? = null

    @SerializedName("menuname")
    @Expose
    private var menuname: String? = null

    @SerializedName("menunamelanguage")
    @Expose
    private var menunamelanguage: String? = null

    @SerializedName("menuslogan")
    @Expose
    private var menuslogan: String? = null

    @SerializedName("price")
    @Expose
    private var price: Int? = null

    @SerializedName("isActive")
    @Expose
    private var isActive: Int? = null

    @SerializedName("isMenuHasSepItem")
    @Expose
    private var isMenuHasSepItem: Int? = null

    @SerializedName("databaseId")
    @Expose
    private var databaseId: Int? = null

    @SerializedName("clientuserId")
    @Expose
    private var clientuserId: Int? = null

    fun getMenucategoryId(): Int? {
        return menucategoryId
    }

    fun setMenucategoryId(menucategoryId: Int?) {
        this.menucategoryId = menucategoryId
    }

    fun getMenuname(): String? {
        return menuname
    }

    fun setMenuname(menuname: String?) {
        this.menuname = menuname
    }

    fun getMenunamelanguage(): String? {
        return menunamelanguage
    }

    fun setMenunamelanguage(menunamelanguage: String?) {
        this.menunamelanguage = menunamelanguage
    }

    fun getMenuslogan(): String? {
        return menuslogan
    }

    fun setMenuslogan(menuslogan: String?) {
        this.menuslogan = menuslogan
    }

    fun getPrice(): Int? {
        return price
    }

    fun setPrice(price: Int?) {
        this.price = price
    }

    fun getIsActive(): Int? {
        return isActive
    }

    fun setIsActive(isActive: Int?) {
        this.isActive = isActive
    }

    fun getIsMenuHasSepItem(): Int? {
        return isMenuHasSepItem
    }

    fun setIsMenuHasSepItem(isMenuHasSepItem: Int?) {
        this.isMenuHasSepItem = isMenuHasSepItem
    }

    fun getDatabaseId(): Int? {
        return databaseId
    }

    fun setDatabaseId(databaseId: Int?) {
        this.databaseId = databaseId
    }

    fun getClientuserId(): Int? {
        return clientuserId
    }

    fun setClientuserId(clientuserId: Int?) {
        this.clientuserId = clientuserId
    }
}