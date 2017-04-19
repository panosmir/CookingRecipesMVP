package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Panos on 4/17/2017.
 */
@Module
public class SharedPrefsModule {

    @Inject
    Context mContext;

    @Singleton
    @Provides
    SharedPreferences preferences(Context context) {
        mContext = context;
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
