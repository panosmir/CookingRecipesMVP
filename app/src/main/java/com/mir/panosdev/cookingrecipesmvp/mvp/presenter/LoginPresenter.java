package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginView;

import java.util.ArrayList;
import java.util.List;

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
    public LoginPresenter() {}

    User user = new User();

    @Inject
    public void userRegistration() {
        Observable<Response<User>> userObservable = mRecipesApiService.userRegistration(getView().getUserDetails());
        subscribe(userObservable, this);
    }

    @Inject
    public void userLogin() {
        Observable<Response<User>> userObservable = mRecipesApiService.userLogin((getView().getUserDetails()));
        subscribe(userObservable, this);
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Response<User> userResponse) {
        if(userResponse.isSuccessful()) {
            user.setId(userResponse.body().getId());
            getView().returnUserDetails(userResponse.body());
        }
    }

    @Override
    public void onError(Throwable e) {
        getView().onErrorToast("Username or password incorrect...");
    }

    @Override
    public void onComplete() {
        getView().onHideDialog();
        getView().onLoginOrRegisterCompleted("You logged in successfully!!!");
    }
}
