package com.mir.panosdev.cookingrecipesmvp.application;

import android.app.Application;
import android.content.Context;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.ApplicationComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerApplicationComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ApplicationModule;

/**
 * Created by Panos on 3/18/2017.
 */

public class RecipeApplication extends Application{

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }
    //83.212.119.128 okeanos ip
    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder().applicationModule(
                        new ApplicationModule(this, "http://10.0.2.2:8080")
                ).build();
    }

    public ApplicationComponent getApplicationComponent(){
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
