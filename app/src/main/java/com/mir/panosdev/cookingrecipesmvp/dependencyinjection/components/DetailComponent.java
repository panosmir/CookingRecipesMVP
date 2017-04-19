package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.DetailsModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ApplicationModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;

import dagger.Component;

/**
 * Created by Panos on 4/17/2017.
 */
@PerActivity
@Component(modules = DetailsModule.class, dependencies = ApplicationComponent.class)
public interface DetailComponent {
    void inject (DetailsActivity activity);
}
