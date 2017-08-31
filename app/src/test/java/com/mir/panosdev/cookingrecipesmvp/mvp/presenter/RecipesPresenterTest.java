package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainActivityMVP;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class RecipesPresenterTest {

    @InjectMocks
    private RecipesPresenter mRecipesPresenter;
    @Mock
    private RecipesApiService mApiService;
    @Mock
    private RecipeMapper mRecipeMapper;
    @Mock
    private Storage mStorage;
    @Mock
    private MainActivityMVP.MainView mView;
    @Mock
    private Observable<Response<RecipesResponse>> mObservable;

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ ->
                Schedulers.trampoline()
        );
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetRecipes() throws Exception {
        Mockito.when(mApiService.getRecipes()).thenReturn(mObservable);
        Mockito.when(mObservable.subscribeOn(Schedulers.io())).thenReturn(mObservable);
        Mockito.when(mObservable.observeOn(AndroidSchedulers.mainThread())).thenReturn(mObservable);
        mRecipesPresenter.getRecipes();
        Mockito.verify(mApiService).getRecipes();
        Mockito.verify(mView).onShowDialog("Loading recipes....");
    }

}