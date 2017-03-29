package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.SearchModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.SearchActivity;

import dagger.Component;

/**
 * Created by Panos on 3/23/2017.
 */
@PerActivity
@Component(modules = SearchModule.class, dependencies = ApplicationComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);
}
