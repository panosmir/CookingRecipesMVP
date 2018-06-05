package com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes

import java.util.*

data class RecipesResponse(var recipes: Array<Recipe>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipesResponse

        if (!Arrays.equals(recipes, other.recipes)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(recipes)
    }
}
