package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.os.Handler;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginView;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * Created by Panos on 3/27/2017.
 */
public class LoginPresenter extends BasePresenter<LoginView> implements Observer<Response<User>> {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    public LoginPresenter() {
    }


    @Inject
    public void userLogin() {
        if(getView().getUserDetails()!=null) {
            Observable<Response<User>> userObservable = mRecipesApiService.userLogin(getView().getUserDetails());
            subscribe(userObservable, this);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Response<User> userResponse) {
        switch (userResponse.code()) {
            case HttpURLConnection.HTTP_OK:
                getView().returnUserDetails(userResponse.body());
                loginSuccessful();
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                loginFailed();
                break;
        }

    }

    private void loginFailed() {
        getView().onHideDialog();
        getView().onErrorToast("Username or password incorrect...");
    }

    private void loginSuccessful() {
        getView().onShowDialog("Logging you in... Please wait");
        getView().onLoginCompleted("You logged in successfully!!!");
        getView().onHideDialog();
    }

    @Override
    public void onError(Throwable e) {}

    @Override
    public void onComplete() {}
}
