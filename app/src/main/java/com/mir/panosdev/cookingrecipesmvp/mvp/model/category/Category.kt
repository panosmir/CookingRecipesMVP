package com.mir.panosdev.cookingrecipesmvp.mvp.model.category

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Generated

@Generated("net.hexar.json2pojo")
data class Category (
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("category")
        var category: String? = null
) : Serializable