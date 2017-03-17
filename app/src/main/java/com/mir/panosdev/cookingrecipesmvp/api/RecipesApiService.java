package com.mir.panosdev.cookingrecipesmvp.api;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.RecipesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Panos on 3/17/2017.
 */
public interface RecipesApiService {

    @GET("/recipes")
    Observable<RecipesResponse> getRecipes();
}
