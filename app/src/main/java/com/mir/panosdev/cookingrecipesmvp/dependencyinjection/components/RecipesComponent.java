package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.SearchActivity;

import dagger.Component;

/**
 * Created by Panos on 3/18/2017.
 */
@PerActivity
@Component(modules = RecipesModule.class, dependencies = ApplicationComponent.class)
public interface RecipesComponent {

    void inject(MainActivity activity);
}
