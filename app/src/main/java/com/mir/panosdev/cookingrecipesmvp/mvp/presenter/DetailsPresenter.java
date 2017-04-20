package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsView;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import retrofit2.http.HTTP;

/**
 * Created by Panos on 4/17/2017.
 */

public class DetailsPresenter extends BasePresenter<DetailsView> implements Observer<Response<Recipe>> {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    public DetailsPresenter(){}

    @Inject public void deleteRecipe(){
        if(getView().getDeleteSignal()) {
            Observable<Response<Recipe>> responseObservable = mRecipesApiService.deleteRecipe(getView().getRecipeDetails());
            subscribe(responseObservable, this);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {}

    @Override
    public void onNext(Response<Recipe> recipe) {}

    @Override
    public void onError(Throwable e) {}

    @Override
    public void onComplete() {
        getView().onDeleteShowToast("Delete successfully!!!");
    }
}
