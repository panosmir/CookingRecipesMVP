package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.util.Log;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mapper.CategoryMapper;
import com.mir.panosdev.cookingrecipesmvp.mapper.IngredientMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Categories;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.category.Category;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.Ingredient;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.IngredientsResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.ingredient.IngredientsResponseIngredients;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Panos on 4/3/2017.
 */

public class NewRecipePresenter extends BasePresenter<NewRecipeView> implements Observer<Recipe> {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected CategoryMapper mCategoryMapper;

    @Inject
    protected IngredientMapper mIngredientMapper;

    @Inject
    public NewRecipePresenter() {
    }


    @Inject
    public void addNewRecipe() {
        Observable<Recipe> recipeObservable = mRecipesApiService.addRecipe(getView().getRecipeDetails());
        subscribe(recipeObservable, this);
    }

    @Inject
    public void fetchCategories() {
        Observable<Response<Categories>> categoryObservable = mRecipesApiService.getAllCategories();
        categoryObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Categories>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Categories> category) {
                        List<Category> categories = mCategoryMapper.mapCategories(category.body().getCategories());
                        if (categories != null) {
                            getView().onClearItems();
                            getView().onItemsLoaded(categories);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ERROR_LOG", "ERROR------->" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Inject
    public void fetchIngredients() {
        if (getView().getCategoryId() != 0) {
            Observable<Response<IngredientsResponse>> responseObservable = mRecipesApiService.getIngredientsById(getView().getCategoryId());
            subscribe(responseObservable, new Observer<Response<IngredientsResponse>>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(Response<IngredientsResponse> ingredientResponse) {
                    List<Ingredient> ingredientList = mIngredientMapper.mapIngredients(ingredientResponse.body().getIngredients());
                    getView().onClearIngredients();
                    getView().onIngredientsLoaded(ingredientList);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("ERROR_LOG", e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
        else
            return;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Recipe recipe) {
    }

    @Override
    public void onError(Throwable e) {
        Log.d("ERROR_LOG", e.getMessage());
    }

    @Override
    public void onComplete() {
        getView().onCompletedToast("Your recipe created successfully!!");
    }
}
