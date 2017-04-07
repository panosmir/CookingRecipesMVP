package com.mir.panosdev.cookingrecipesmvp.modules.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerLoginComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.LoginModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.register.RegisterActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.LoginPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Panos on 3/27/2017.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.usernameEditText)
    TextView mUsername;

    @BindView(R.id.passwordEditText)
    TextView mPassword;

    @Inject
    protected LoginPresenter mLoginPresenter;

    User user = new User();

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
    }

    @OnClick({R.id.registerButton, R.id.loginButton})
    public void loginButtonClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            case R.id.loginButton:
                mLoginPresenter.userLogin();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this, getApplicationContext()))
                .build().inject(this);
    }

    @Override
    public User getUserDetails() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        if (sharedPreferences.contains("EXISTS")) {
            user.setId(sharedPreferences.getInt("USER_ID", 0));
            user.setUsername(sharedPreferences.getString("USER_USERNAME", null));
            user.setPassword(sharedPreferences.getString("USER_PASSWORD", null));
            return user;
        } else {
            if (!mUsername.getText().toString().isEmpty() || !mPassword.getText().toString().isEmpty()) {
                user.setUsername(mUsername.getText().toString());
                user.setPassword(mPassword.getText().toString());
                return user;
            }
        }
        return null;
    }

    @Override
    public void onHideDialog() {

    }

    @Override
    public void onLoginCompleted(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onErrorToast(String message) {
//            mUsername.setError("Incorrect username or password");
//            mPassword.setError("Incorrect username or password");
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnUserDetails(User user) {
        if(user != null) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("USER_ID", user.getId());
            editor.putString("USER_USERNAME", user.getUsername());
            editor.putString("USER_PASSWORD", user.getPassword());
            editor.putBoolean("EXISTS", true);
            Log.d("USER_DETAILS", "User ---> " + user.getId());
            editor.apply();
        }
    }

}
