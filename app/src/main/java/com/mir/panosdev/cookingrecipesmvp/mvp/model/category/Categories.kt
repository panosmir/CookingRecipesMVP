package com.mir.panosdev.cookingrecipesmvp.mvp.model.category

import java.util.*


data class Categories(var categories: Array<Category>) : java.io.Serializable  {

    companion object {
        private const val serialVersionUID = 6849705858325444072L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Categories

        if (!Arrays.equals(categories, other.categories)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(categories)
    }
}
