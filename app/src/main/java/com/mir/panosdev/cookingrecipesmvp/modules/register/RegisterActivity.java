package com.mir.panosdev.cookingrecipesmvp.modules.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mir.panosdev.cookingrecipesmvp.R;
import com.mir.panosdev.cookingrecipesmvp.base.BaseActivity;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.DaggerRecipesComponent;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.module.ActivityModules.RecipesModule;
import com.mir.panosdev.cookingrecipesmvp.modules.home.MainActivity;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.presenter.RegisterPresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.RegisterActivityMVP;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends BaseActivity implements RegisterActivityMVP.RegisterView {

    @BindView(R.id.usernameRegisterET)
    EditText mUsername;

    @BindView(R.id.passwordRegisterET)
    EditText mPassword;

    @BindView(R.id.userRegistrationButton)
    Button registerButton;

    @Inject
    protected RegisterPresenter mRegisterPresenter;

    private User user = new User();
    private Observable<Boolean> usernameObservable = null;
    private Observable<Boolean> passwordObservable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterPresenter.attachView(this);
        initUsernameCheck();
        initPasswordCheck();
        Observable.combineLatest(usernameObservable, passwordObservable, (usernameBoolean, passwordBoolean) -> usernameBoolean && passwordBoolean)
                .distinctUntilChanged()
                .subscribe(isValid ->
                        registerButton.setEnabled(isValid)
                );
    }

    private void initPasswordCheck() {
        if (mPassword != null) {
            passwordObservable = RxTextView.textChanges(mPassword)
                    .map(charSequence ->
                            !isEmpty(charSequence.toString()) && charSequence.length() >= 5)
                    .distinctUntilChanged();
        }
    }

    private void initUsernameCheck() {
        if (mUsername.getText() != null) {
            usernameObservable = RxTextView.textChanges(mUsername)
                    .map(charSequence ->
                            !isEmpty(charSequence.toString()) && charSequence.length() >= 5)
                    .distinctUntilChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.detachView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public User getUserDetails() {
        if (!mUsername.getText().toString().isEmpty() || !mPassword.getText().toString().isEmpty()) {
            user.setUsername(mUsername.getText().toString());
            user.setPassword(mPassword.getText().toString());
            return user;
        }
        return null;
    }

    @OnClick(R.id.userRegistrationButton)
    public void userRegistrationButtonClick() {
        mRegisterPresenter.userRegistration();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRecipesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .recipesModule(new RecipesModule())
                .build().inject(this);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        mUsername.setError("Username already in use.");
    }

    @Override
    public void returnUserDetails(User user) {
        if (user != null) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("USER_ID", user.getUser_id());
            editor.putString("USER_USERNAME", user.getUsername());
            editor.putBoolean("EXISTS", true);
            Log.d("USER_DETAILS", "User ---> " + user.getUser_id());
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

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }
}
