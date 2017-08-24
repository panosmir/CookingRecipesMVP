package com.mir.panosdev.cookingrecipesmvp.api;

import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Categories;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.IngredientsResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipesApiService {

    @GET("recipes/all")
    Observable<Response<RecipesResponse>> getRecipes();

    @GET("recipes/all/findbyTitle/{title}")
    Observable<Response<RecipesResponse>> getARecipe(@Path("title") String title);

    @GET("recipes/all/userId/{id}")
    Observable<Response<RecipesResponse>> getUserRecipes(@Path("id") int id);

    @POST("recipes/all/userId/create")
    Completable addRecipe(@Body Recipe recipe);

    @POST("users/create")
    Single<Response<User>> userRegistration(@Body User user);

    @POST("users/all/findUser")
    Single<Response<User>> userLogin(@Body User user);

    @HTTP(method = "DELETE", path = "/recipes/delete", hasBody = true)
    Completable deleteRecipe(@Body Recipe recipe);

    @HTTP(method = "PUT", path = "/recipes/update", hasBody = true)
    Completable updateRecipe(@Body Recipe recipe);

    @GET("/categories/findById/{id}")
    Observable<Category> getCategoryById(@Path("id")int id);

    @GET("/categories/all")
    Single<Response<Categories>> getAllCategories();

    @GET("ingredients/findByCategoryId/{id}")
    Single<Response<IngredientsResponse>> getIngredientsById(@Path("id") int id);

    @GET("recipes/userFavorites/add/{id}/{username}")
    Completable addFavorite(@Path("id") int id, @Path("username") String username);

    @GET("recipes/removeFavorite")
    Completable removeFavorite(@Query("id") int id, @Query("username") String username);

    @GET("/users/userFavorites/{id}")
    Observable<Response<RecipesResponse>> getUserFavorites(@Path("id") int id);
}
