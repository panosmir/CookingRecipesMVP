package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.os.Looper;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Panos on 4/19/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, Looper.class, RecipesResponse.class})
public class DetailsPresenterTest {

    @Mock
    private RecipesApiService mApiService;

    @Mock
    private DetailsActivityMVP mView;

    @Mock
    private Observable<Response<Recipe>> mObservable;

    @InjectMocks
    private DetailsPresenter mDetailsPresenter;

    @Mock
    private Recipe recipe;

    @Captor
    private ArgumentCaptor<Subscriber<Recipe>> captor;

    @Before
    public void setUp() throws Exception {
        ArrayList<Recipe> recipesList = new ArrayList<>();
        recipesList.add(new Recipe());
    }

    @Test
    public void shouldDeleteRecipes() throws Exception {
        PowerMockito.mockStatic(Looper.class);
        when(mView.getRecipeDetails()).thenReturn(recipe);
        when(mApiService.deleteRecipe(recipe)).thenReturn(mObservable);
        when(mObservable.subscribeOn(Schedulers.newThread())).thenReturn(mObservable);
        when(mObservable.observeOn(AndroidSchedulers.mainThread())).thenReturn(mObservable);
        mDetailsPresenter.deleteRecipe();
        verify(mApiService).deleteRecipe(recipe);
        verify(mObservable).subscribeOn(Schedulers.newThread());
        verify(mObservable).observeOn(AndroidSchedulers.mainThread());
    }
}