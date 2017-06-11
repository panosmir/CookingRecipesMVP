package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainActivityMVP;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe;

/**
 * Created by Panos on 3/18/2017.
 */

public class RecipesPresenter implements MainActivityMVP.Presenter {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected RecipeMapper mRecipeMapper;
    @Inject
    protected Storage mStorage;

    private MainActivityMVP.MainView mainView;
    private CompositeDisposable compositeDisposable;

    @Inject
    public RecipesPresenter() {
    }

    @Inject
    public void getRecipes() {
        if (mainView != null)
            mainView.onShowDialog("Loading recipes....");
        Observable<Response<RecipesResponse>> recipesResponseObservable = mRecipesApiService.getRecipes();
        Disposable disposable = recipesResponseObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Response<RecipesResponse>>() {
                    @Override
                    public void onNext(@NonNull Response<RecipesResponse> recipesResponseResponse) {
                        List<Recipe> recipes = mRecipeMapper.mapRecipes(mStorage, recipesResponseResponse.body().getRecipes());
                        mainView.onClearItems();
                        mainView.onRecipeLoaded(recipes);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainView.onHideDialog();
                        mainView.onShowToast("Error loading recipes " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mainView.onHideDialog();
                        mainView.onShowToast("Sync completed!");
                    }
                });
        mStorage.dropDatabase();
        if (compositeDisposable != null)
            compositeDisposable.add(disposable);

    }


    public void getRecipesFromDatabase() {
        List<Recipe> recipes = mStorage.getSavedRecipes();
        mainView.onClearItems();
        mainView.onRecipeLoaded(recipes);
        mainView.onNetworkUnavailableToast("Updating items from database...");
    }

    @Override
    public void attachView(MainActivityMVP.MainView view) {
        mainView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        mainView = null;
    }
}
