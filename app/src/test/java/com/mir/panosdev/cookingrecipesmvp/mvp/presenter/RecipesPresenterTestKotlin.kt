package com.mir.panosdev.cookingrecipesmvp.mvp.presenter

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService
import com.mir.panosdev.cookingrecipesmvp.mapper.RecipeMapper
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.RecipesResponse
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Storage
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainActivityMVP
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RecipesPresenterTestKotlin {

    @InjectMocks
    private val mRecipesPresenter: RecipesPresenter? = null

    @Mock
    private lateinit var mStorage: Storage
    @Mock
    private lateinit var mApiService: RecipesApiService
    @Mock
    private lateinit var mView: MainActivityMVP.MainView
    @Mock
    private val e: Throwable? = null

    private lateinit var testObserver: TestObserver<Response<RecipesResponse>>

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldGetErrorKotlin() {
        Mockito.`when`(mApiService.recipes).thenReturn(Observable.error(e))
        mRecipesPresenter?.getRecipes()
        Mockito.verify(mView).onHideDialog()
        Mockito.verify(mView).onShowToast("Error loading recipes " + e?.message)
    }
}