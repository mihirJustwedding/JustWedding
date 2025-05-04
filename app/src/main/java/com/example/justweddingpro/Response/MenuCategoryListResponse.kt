package com.example.justweddingpro.Response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class MenuCategoryListResponse {
    @SerializedName("menuCategoryDetails")
    @Expose
    private var menuCategoryDetails: List<MenuCategoryDetail?>? = null

    fun getMenuCategoryDetails(): List<MenuCategoryDetail?>? {
        return menuCategoryDetails
    }

    fun setMenuCategoryDetails(menuCategoryDetails: List<MenuCategoryDetail?>?) {
        this.menuCategoryDetails = menuCategoryDetails
    }

    class MenuCategoryDetail {
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
        var isActive: Double? = null

        @SerializedName("menuImage")
        @Expose
        var menuImage: String? = null

        @SerializedName("isMenuHasSepItem")
        @Expose
        var isMenuHasSepItem: Int? = null

        @SerializedName("database_id")
        @Expose
        var databaseId: Int? = null

        @SerializedName("clientuser_id")
        @Expose
        var clientuserId: Int? = null
    }
}