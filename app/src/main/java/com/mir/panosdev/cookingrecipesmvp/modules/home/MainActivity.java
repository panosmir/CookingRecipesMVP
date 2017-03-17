package com.mir.panosdev.cookingrecipesmvp.modules.home;

import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.Recipe;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.RecipesPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView{

    @Inject protected RecipesPresenter mRecipesPresenter;


    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
        .applicationComponent(getApplicationComponent())
        .recipesModule(new RecipesModule(this))
        .build().inject(this);
    }



    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeLoaded(List<Recipe> recipes) {

    }
}
