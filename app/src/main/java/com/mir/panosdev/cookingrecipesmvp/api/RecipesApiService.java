package com.mir.panosdev.cookingrecipesmvp.api;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.RecipesResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Panos on 3/17/2017.
 */
public interface RecipesApiService {

    @GET("/recipes/all")
    Observable<RecipesResponse> getRecipes();

    @GET("/recipes/all/findbyTitle/{title}")
    Observable<RecipesResponse> getARecipe(@Path("title") String title);

}
