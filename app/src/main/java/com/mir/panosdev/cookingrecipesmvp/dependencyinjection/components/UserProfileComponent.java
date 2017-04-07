package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.UserProfileModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.UserProfileActivity;

import dagger.Component;

/**
 * Created by Panos on 4/5/2017.
 */
@PerActivity
@Component(modules = UserProfileModule.class, dependencies = ApplicationComponent.class)
public interface UserProfileComponent {
    void inject(UserProfileActivity activity);
}
