package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.os.Looper;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainActivityMVP;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, Looper.class, RecipesResponse.class})
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
    private MainActivityMVP mView;
    @Mock
    private Observable<Response<RecipesResponse>> mObservable;

    @Captor
    private ArgumentCaptor<Subscriber<RecipesResponse>> captor;

//    @Before
//    public void setUp() throws Exception {
//        ArrayList<Recipe> recipes = new ArrayList<>();
//        recipes.add(new Recipe());
//    }
//
//    @Test
//    public void shouldGetRecipesFromNet() throws Exception {
//        PowerMockito.mockStatic(Looper.class);
//        when(mApiService.getRecipes()).thenReturn(mObservable);
//        when(mObservable.subscribeOn(Schedulers.newThread())).thenReturn(mObservable);
//        when(mObservable.observeOn(AndroidSchedulers.mainThread())).thenReturn(mObservable);
//        mRecipesPresenter.getRecipes();
//        mStorage.dropDatabase();
//        verify(mView, atLeastOnce()).onShowDialog("Loading recipes....");
//        verify(mApiService).getRecipes();
//        verify(mObservable).subscribeOn(Schedulers.newThread());
//        verify(mObservable).observeOn(AndroidSchedulers.mainThread());
//        verify(mStorage, atLeastOnce()).dropDatabase();
//    }
//
//    @Test
//    public void shouldGetRecipesFromDatabase() throws Exception {
//        PowerMockito.mockStatic(Looper.class);
//        mRecipesPresenter.getRecipesFromDatabase();
//        mStorage.getSavedRecipes();
//        verify(mStorage, atLeastOnce()).getSavedRecipes();
//        verify(mView).onClearItems();
//        verify(mView).onRecipeLoaded(anyListOf(Recipe.class));
//        verify(mView, atLeastOnce()).onNetworkUnavailableToast("Updating items from database...");
//    }
//
//    @Test
//    public void onNext() throws Exception {
//        RecipesResponse response = mock(RecipesResponse.class);
//        RecipesResponseRecipes[] responseRecipes = new RecipesResponseRecipes[1];
//        when(response.getRecipes()).thenReturn(responseRecipes);
//        mRecipesPresenter.onNext(Response.success(response));
//
//        verify(mRecipeMapper).mapRecipesAndStorage(mStorage, responseRecipes);
//        verify(mView).onClearItems();
//        verify(mView).onRecipeLoaded(anyListOf(Recipe.class));
//    }
//
//
//    @Test
//    public void onError() throws Exception {
//        Throwable e = mock(Throwable.class);
//        mRecipesPresenter.onError(e);
//        verify(mView).onHideDialog();
//        verify(mView).onShowToast("Error loading recipes " + e.getMessage());
//    }
//
//    @Test
//    public void onCompleted() throws Exception {
//        mRecipesPresenter.onComplete();
//        verify(mView, times(1)).onHideDialog();
//        verify(mView, times(1)).onShowToast("Sync completed!");
//
//    }
}