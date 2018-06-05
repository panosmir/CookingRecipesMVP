package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes

import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User

import java.io.Serializable
import java.util.ArrayList


data class Recipe(
        var id: Int = 0,
        var title: String? = null,
        var description: String? = null
) : Serializable {

    var user: User? = null
    var favorites: List<User> = ArrayList()
    var ingredients: List<Ingredient> = ArrayList()

}
