package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.util.Log;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsView;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


public class DetailsPresenter extends BasePresenter<DetailsView> implements Observer<Response<Recipe>> {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    public DetailsPresenter() {
    }

    @Inject
    public void deleteRecipe() {
        if (getView().getDeleteSignal()) {
            Observable<Response<Recipe>> responseObservable = mRecipesApiService.deleteRecipe(getView().getRecipeDetails());
            subscribe(responseObservable, this);
        }
    }

    @Inject
    public void updateRecipe() {
        if (getView().getUpdateSignal()) {
            Observable<Response<Recipe>> responseObservable = mRecipesApiService.updateRecipe(getView().getRecipeDetails());
            subscribe(responseObservable, this);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Response<Recipe> recipe) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }
}
