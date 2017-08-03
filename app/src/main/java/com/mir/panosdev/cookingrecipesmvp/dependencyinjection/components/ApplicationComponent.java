package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit exposeRetrofit();
    SharedPreferences exposeSharedPreferences();
    Context exposeContext();
}
