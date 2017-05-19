package com.mir.panosdev.cookingrecipesmvp.api;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Categories;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Panos on 3/17/2017.
 */
public interface RecipesApiService {

    @GET("recipes/all")
    Observable<Response<RecipesResponse>> getRecipes();

    @GET("recipes/all/findbyTitle/{title}")
    Observable<Response<RecipesResponse>> getARecipe(@Path("title") String title);

    @GET("recipes/all/userId/{id}")
    Observable<Response<RecipesResponse>> getUserRecipes(@Path("id") int id);

    @POST("recipes/all/userId/create")
    Observable<Recipe> addRecipe(@Body Recipe recipe);

    @POST("users/create")
    Observable<Response<User>> userRegistration(@Body User user);

    @POST("users/all/findUser")
    Observable<Response<User>> userLogin(@Body User user);

    @HTTP(method = "DELETE", path = "/recipes/delete", hasBody = true)
    Observable<Response<Recipe>> deleteRecipe(@Body Recipe recipe);

    @HTTP(method = "PUT", path = "/recipes/update", hasBody = true)
    Observable<Response<Recipe>> updateRecipe(@Body Recipe recipe);

    @GET("/categories/findById/{id}")
    Observable<Category> getCategoryById(@Path("id")int id);

    @GET("/categories/all")
    Observable<Response<Categories>> getAllCategories();

    //// TODO: 18-May-17 Wait for the server to implement it
//    @GET("recipes/userFavorites/{id}")
//    Observable<Response<RecipesResponse>> getUserFavoriteRecipes(@Path("id") int id);

}
