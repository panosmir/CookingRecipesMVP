package com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient

import java.util.*

data class IngredientsResponse(var ingredients: Array<Ingredient>) : java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IngredientsResponse

        if (!Arrays.equals(ingredients, other.ingredients)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(ingredients)
    }
}
