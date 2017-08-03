package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

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
