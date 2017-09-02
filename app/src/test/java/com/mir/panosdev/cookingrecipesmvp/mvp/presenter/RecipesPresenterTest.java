package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.observers.DisposableLambdaObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
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
    @Mock
    private Throwable e;
    @Mock
    private RecipesResponse mResponse;
    @Mock
    private List<Recipe> mRecipes = new ArrayList<>();

    @Mock
    private Recipe mRecipe;

    private TestObserver<Response<RecipesResponse>> testObserver;


    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ ->
                Schedulers.trampoline()
        );
        RxJavaPlugins.setIoSchedulerHandler(__ ->
                Schedulers.trampoline()
        );
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldGetRecipes() throws Exception {
        testObserver.onNext(Response.success(mResponse));
        Mockito.when(mApiService.getRecipes()).thenReturn(Observable.just(Response.success(mResponse)));
        Mockito.when(mRecipeMapper.mapRecipesAndStorage(mStorage, Response.success(mResponse).body().getRecipes())).thenReturn(mRecipes);
        mObservable.subscribe(testObserver);
        mRecipesPresenter.getRecipes();
        Mockito.verify(mApiService).getRecipes();
        Mockito.verify(mView).onShowDialog("Loading recipes....");
        Mockito.verify(mRecipeMapper).mapRecipesAndStorage(mStorage, Response.success(mResponse).body().getRecipes());
        Mockito.verify(mView).onClearItems();
        Mockito.verify(mView).onRecipeLoaded(mRecipes);
    }

    @Test
    public void shouldGetError() throws Exception {
        Mockito.when(mApiService.getRecipes()).thenReturn(Observable.error(e));
        mRecipesPresenter.getRecipes();
        Mockito.verify(mView).onShowToast("Error loading recipes " + e.getMessage());
    }

}