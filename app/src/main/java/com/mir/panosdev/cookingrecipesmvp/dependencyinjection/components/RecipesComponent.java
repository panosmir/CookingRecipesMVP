package com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components;

import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.scope.PerActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.DetailsFragment;
import com.mir.panosdev.cookingrecipesmvp.modules.detail.UpdateFragment;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainFragment;
import com.mir.panosdev.cookingrecipesmvp.modules.login.LoginActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.newRecipe.NewRecipeActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.register.RegisterActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.SearchFragment;
import com.mir.panosdev.cookingrecipesmvp.modules.userprofile.UserProfileActivity;

import dagger.Component;

/**
 * Created by Panos on 3/18/2017.
 */
@PerActivity
@Component(modules = RecipesModule.class, dependencies = ApplicationComponent.class)
public interface RecipesComponent {
    void inject(MainActivity activity);
    void inject(MainFragment fragment);
    void inject(DetailsActivity activity);
    void inject(UpdateFragment fragment);
    void inject(DetailsFragment fragment);
    void inject(SearchFragment activity);
    void inject(LoginActivity activity);
    void inject(NewRecipeActivity activity);
    void inject(RegisterActivity activity);
    void inject(UserProfileActivity activity);
}
