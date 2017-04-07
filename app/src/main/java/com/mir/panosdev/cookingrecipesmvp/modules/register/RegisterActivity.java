package com.mir.panosdev.cookingrecipesmvp.modules.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRegisterComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.RegisterModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.login.LoginActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.RegisterPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.RegisterView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Panos on 4/7/2017.
 */

public class RegisterActivity extends BaseActivity implements RegisterView {

    @BindView(R.id.usernameRegisterET)
    EditText mUsername;

    @BindView(R.id.passwordRegisterET)
    EditText mPassword;

    @Inject protected RegisterPresenter mRegisterPresenter;

    User user = new User();

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
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

    @OnClick(R.id.userRegistrationButton)
    public void userRegistrationButtonClick(){
        mRegisterPresenter.userRegistration();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRegisterComponent.builder()
            .applicationComponent(getApplicationComponent())
            .registerModule(new RegisterModule(this))
            .build().inject(this);
    }

    @Override
    public void onHideDialog() {

    }

    @Override
    public void onErrorToast(String message) {
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

    @Override
    public void onRegisterSuccess(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
