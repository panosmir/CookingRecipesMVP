package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe;


public class DetailsPresenter implements DetailsActivityMVP.Presenter {

    @Inject
    protected RecipesApiService mRecipesApiService;

    private DetailsActivityMVP.DetailsView mView;
    private Observable<Response<Recipe>> recipeObservable;

    private CompositeDisposable compositeDisposable;

    @Inject
    public DetailsPresenter() {
    }

    @Inject
    public void deleteRecipe() {
        if (mView!=null && mView.getDeleteSignal()) {
            recipeObservable = mRecipesApiService.deleteRecipe(mView.getRecipeDetails());
            Disposable disposable = recipeObservable.observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<Recipe>>() {

                        @Override
                        public void onNext(Response<Recipe> recipeResponse) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Inject
    public void updateRecipe() {
        if (mView!=null && mView.getUpdateSignal()) {
            recipeObservable = mRecipesApiService.updateRecipe(mView.getRecipeDetails());
            Disposable disposable = recipeObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<Recipe>>() {
                        @Override
                        public void onNext(Response<Recipe> recipeResponse) {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }


    @Override
    public void attachView(DetailsActivityMVP.DetailsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
        mView = null;
    }
}
