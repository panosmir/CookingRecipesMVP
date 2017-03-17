package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import android.content.Context;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Panos on 3/17/2017.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit exposeRetrofit();
    Context exposeContext();
}
