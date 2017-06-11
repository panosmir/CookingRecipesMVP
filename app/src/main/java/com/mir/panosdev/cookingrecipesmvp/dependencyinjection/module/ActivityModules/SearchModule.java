package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.SearchActivityMVP;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Panos on 3/23/2017.
 */
@Module
public class SearchModule {

    private SearchActivityMVP mView;

    public SearchModule(SearchActivityMVP searchView){
       mView = searchView;
    }

    @PerActivity
    @Provides
    RecipesApiService provideApiService(Retrofit retrofit){
        return retrofit.create(RecipesApiService.class);
    }

    @PerActivity
    @Provides
    SearchActivityMVP provideSearchView(){
        return mView;
    }
}
