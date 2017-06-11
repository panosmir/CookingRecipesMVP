package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.DetailsActivityMVP;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Panos on 4/17/2017.
 */
@Module
public class DetailsModule {
    private DetailsActivityMVP mView;

    public DetailsModule(DetailsActivityMVP mView) {
        this.mView = mView;
    }

    @PerActivity
    @Provides
    RecipesApiService provideApiService(Retrofit retrofit){
        return retrofit.create(RecipesApiService.class);
    }

    @PerActivity
    @Provides
    DetailsActivityMVP provideDetailView(){
        return mView;
    }
}
