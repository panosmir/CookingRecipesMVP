package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.NewRecipeModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.NewRecipeActivity;

import dagger.Component;

/**
 * Created by Panos on 4/3/2017.
 */
@PerActivity
@Component(modules = NewRecipeModule.class, dependencies = ApplicationComponent.class)
public interface NewRecipeComponent {
    void inject(NewRecipeActivity activity);
}
