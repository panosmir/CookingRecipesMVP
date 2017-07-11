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
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.NewRecipeMVP;

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

import static io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe;

/**
 * Created by Panos on 4/3/2017.
 */

public class NewRecipePresenter implements NewRecipeMVP.Presenter {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    protected CategoryMapper mCategoryMapper;

    @Inject
    protected IngredientMapper mIngredientMapper;

    private NewRecipeMVP.NewRecipeView mView;
    protected CompositeDisposable compositeDisposable;

    @Inject
    public NewRecipePresenter() {
    }


    @Inject
    public void addNewRecipe() {
        if (mView != null) {
            Observable<Recipe> recipeObservable = mRecipesApiService.addRecipe(mView.getRecipeDetails());
            Disposable disposable = recipeObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Recipe>() {
                        @Override
                        public void onNext(Recipe recipe) {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                            mView.onCompletedToast("Your recipe created successfully!!");
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    @Inject
    public void fetchCategories() {
            Observable<Response<Categories>> categoryObservable = mRecipesApiService.getAllCategories();
            Disposable disposable = categoryObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Response<Categories>>() {
                        @Override
                        public void onNext(Response<Categories> category) {
                            List<Category> categories = mCategoryMapper.mapCategories(category.body().getCategories());
                            if (categories != null) {
                                mView.onClearItems();
                                mView.onItemsLoaded(categories);
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
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
    }

    @Inject
    public void fetchIngredients() {
        if (mView!=null && mView.getCategoryId() != 0) {
            Observable<Response<IngredientsResponse>> responseObservable = mRecipesApiService.getIngredientsById(mView.getCategoryId());
            Disposable disposable = responseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<IngredientsResponse>>() {
                        @Override
                        public void onNext(Response<IngredientsResponse> ingredientsResponseResponse) {
                            List<Ingredient> ingredientList = mIngredientMapper.mapIngredients(ingredientsResponseResponse.body().getIngredients());
                            mView.onClearIngredients();
                            mView.onIngredientsLoaded(ingredientList);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
            if(compositeDisposable!=null)
                compositeDisposable.add(disposable);
        } else
            return;
    }

    @Override
    public void attachView(NewRecipeMVP.NewRecipeView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable!=null)
            compositeDisposable.dispose();
        mView = null;
    }
}
