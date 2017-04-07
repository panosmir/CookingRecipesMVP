package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.RegisterModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.register.RegisterActivity;

import dagger.Component;

/**
 * Created by Panos on 4/7/2017.
 */
@PerActivity
@Component(modules = RegisterModule.class, dependencies = ApplicationComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity activity);
}
