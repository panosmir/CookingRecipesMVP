package com.mir.panosdev.cookingrecipesmvp.modules.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerLoginComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.LoginModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.search.SearchActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.LoginPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Panos on 3/27/2017.
 */

public class LoginActivity extends BaseActivity implements LoginView{

    @BindView(R.id.usernameEditText)
    TextView mUsername;

    @BindView(R.id.passwordEditText)
    TextView mPassword;

    @BindView(R.id.loginButton)
    Button mLoginButton;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @Inject protected LoginPresenter mLoginPresenter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeNavBar();
    }

    private void initializeNavBar() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_recipes:
                        loadRecipes();
                        return true;
                    case R.id.action_search:
                        searchRecipes();
                        return true;
                    case R.id.action_login:
                        return true;
                }
                return true;
            }
        });
    }

    private void searchRecipes() {
        Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void loadRecipes() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build().inject(this);
    }
}
