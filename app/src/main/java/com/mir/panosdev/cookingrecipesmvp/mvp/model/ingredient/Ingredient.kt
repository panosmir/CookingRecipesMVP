package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient

import com.google.gson.annotations.SerializedName
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category

import java.io.Serializable

data class Ingredient(
        var id: Int = 0,
        var ingredient: String? = null,
        @SerializedName(value = "quantity")
        var quantity: String? = null
) : Serializable {
    var category: Category? = null
}
