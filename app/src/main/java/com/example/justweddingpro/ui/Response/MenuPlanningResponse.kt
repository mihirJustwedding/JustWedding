package com.example.justweddingpro.ui.Response

import android.view.MenuItem

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class MenuPlanningResponse {
    @SerializedName("menuItems")
    @Expose
    private var menuItems: List<MenuItem>? = null

    fun getMenuItems(): List<MenuItem>? {
        return menuItems
    }

    fun setMenuItems(menuItems: List<MenuItem>?) {
        this.menuItems = menuItems
    }


    class MenuItem {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("menuname")
        @Expose
        var menuname: String? = null

        @SerializedName("menunamelanguage")
        @Expose
        var menunamelanguage: String? = null

        @SerializedName("menuslogan")
        @Expose
        var menuslogan: String? = null

        @SerializedName("price")
        @Expose
        var price: Double? = null

        @SerializedName("isActive")
        @Expose
        var isActive: Int? = null

        @SerializedName("menuImage")
        @Expose
        var menuImage: String? = null

        @SerializedName("clientuser_id")
        @Expose
        var clientuserId: Int? = null

        @SerializedName("itemsDetails")
        @Expose
        var itemsDetails: List<MenuItemDetailsResponse>? = null
    }

}