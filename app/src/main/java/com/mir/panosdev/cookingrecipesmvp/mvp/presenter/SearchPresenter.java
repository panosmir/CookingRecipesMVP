package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.SearchActivityMVP;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Panos on 3/21/2017.
 */

public class SearchPresenter implements SearchActivityMVP.Presenter {

    private SearchActivityMVP.SearchView mView;
    private CompositeDisposable compositeDisposable;

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected RecipeMapper mRecipeMapper;

    @Inject
    public SearchPresenter() {
    }

    @Inject
    public void getARecipe() {
        if (mView != null) {
            Observable<Response<RecipesResponse>> recipesResponseObservable = mRecipesApiService.getARecipe(mView.searchTitle());
            Disposable disposable = recipesResponseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<RecipesResponse>>() {
                        @Override
                        public void onNext(Response<RecipesResponse> recipesResponseResponse) {
                            if(recipesResponseResponse.isSuccessful()) {
                                List<Recipe> recipes = mRecipeMapper.mapResults(recipesResponseResponse.body().getRecipes());
                                mView.onClearItems();
                                mView.onRecipeLoaded(recipes);
                            }
                            else if(recipesResponseResponse.code() == HttpURLConnection.HTTP_NOT_FOUND){
                                mView.onHideDialog();
                                mView.onShowToast("Recipe not found.");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.onHideDialog();
                            mView.onShowToast("Error loading recipes " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            mView.onHideDialog();
                            mView.onShowToast("Recipe found!!!");
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Override
    public void attachView(SearchActivityMVP.SearchView view) {
        mView = view;
    }

    @Override
    public void detatchView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
        mView = null;
    }
}
