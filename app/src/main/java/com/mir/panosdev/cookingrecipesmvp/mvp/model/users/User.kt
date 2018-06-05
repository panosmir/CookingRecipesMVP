package com.mir.panosdev.cookingrecipesmvp.mvp.model.users

import java.io.Serializable

import io.reactivex.annotations.Nullable

data class User(
        var user_id: Int = 0,
        var username: String? = null,
        @Nullable
        var password: String? = null
) : Serializable
