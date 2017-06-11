package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginActivityMVP;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Panos on 3/27/2017.
 */
@Module
public class LoginModule {

    private LoginActivityMVP mView;
//    private Context mContext;

    public LoginModule(LoginActivityMVP searchView){
        mView = searchView;
//        mContext = context;
    }

    @PerActivity
    @Provides
    RecipesApiService provideApiService(Retrofit retrofit){
        return retrofit.create(RecipesApiService.class);
    }

    @PerActivity
    @Provides
    LoginActivityMVP provideLoginView(){
        return mView;
    }
}
