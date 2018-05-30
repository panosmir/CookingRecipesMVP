package com.mir.panosdev.cookingrecipesmvp.modules.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.modules.register.RegisterActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.LoginPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginActivityMVP;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends BaseActivity implements LoginActivityMVP.LoginView {

    @BindView(R.id.usernameEditText)
    TextView mUsername;

    @BindView(R.id.passwordEditText)
    TextView mPassword;

    @BindView(R.id.loginButton)
    Button loginButton;

    @Inject
    protected LoginPresenter mLoginPresenter;

    private User user = new User();
    private Observable<Boolean> usernameObservable = null;
    private Observable<Boolean> passwordObservable = null;

    @Inject
    protected SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter.attachView(this);
        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        if (sharedPreferences.contains("EXISTS")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        initUsernameCheck();
        initPasswordCheck();
        Observable.combineLatest(usernameObservable, passwordObservable, (usernameBoolean, passwordBoolean) -> usernameBoolean && passwordBoolean)
                .distinctUntilChanged()
                .subscribe(isValid -> {
                    if(isValid)
                        enableButton();
                    else
                        disableButton();
                });
    }

    private void disableButton() {
        loginButton.setBackground(getResources().getDrawable(R.color.grey));
        loginButton.setEnabled(false);
    }

    private void enableButton() {
        loginButton.setBackground(getResources().getDrawable(R.color.colorPrimary));
        loginButton.setEnabled(true);
    }

    private void initPasswordCheck() {
        passwordObservable = RxTextView.textChanges(mPassword)
                .map(charSequence ->
                        !isEmpty(charSequence.toString()) && charSequence.length() >= 5)
                .distinctUntilChanged();
    }

    private void initUsernameCheck() {
        usernameObservable = RxTextView.textChanges(mUsername)
                .map(charSequence ->
                    !isEmpty(charSequence.toString()) && charSequence.length() >= 5
                )
                .distinctUntilChanged();
    }

    @OnClick({R.id.registerButton, R.id.loginButton})
    public void loginButtonClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.loginButton:
                onShowDialog("Logging you in... Please wait!");
                mLoginPresenter.userLogin();
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    @Override
    public User getUserDetails() {
        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        if (sharedPreferences.contains("EXISTS")) {
            user.setId(sharedPreferences.getInt("USER_ID", 0));
            user.setUsername(sharedPreferences.getString("USER_USERNAME", null));
            Log.d("LOGIN_LOG", "Username -> " + user.getUsername());
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
        hideDialog();
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
        mUsername.setError("Incorrect username or password");
        mPassword.setError("Incorrect username or password");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnUserDetails(User user) {
        if (user != null) {
            sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("USER_ID", user.getId());
            editor.putString("USER_USERNAME", user.getUsername());
            editor.putBoolean("EXISTS", true);
            Log.d("USER_DETAILS", "User ---> " + user.getId());
            editor.apply();
        }
    }

    @Override
    public void onShowDialog(String s) {
        showDialog(s);
    }

}
