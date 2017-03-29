package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Panos on 3/27/2017.
 */
@Module
public class LoginModule {

    private LoginView mView;

    public LoginModule(LoginView searchView){
        mView = searchView;
    }

    @PerActivity
    @Provides
    RecipesApiService provideApiService(Retrofit retrofit){
        return retrofit.create(RecipesApiService.class);
    }

    @PerActivity
    @Provides
    LoginView provideLoginView(){
        return mView;
    }
}
