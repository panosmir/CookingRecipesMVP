package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.RegisterView;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * Created by Panos on 4/7/2017.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> implements Observer<Response<User>> {

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    public RegisterPresenter() {
    }

    @Inject
    public void userRegistration() {
        if (getView().getUserDetails() != null) {
            Observable<Response<User>> userObservable = mRecipesApiService.userRegistration(getView().getUserDetails());
            subscribe(userObservable, this);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Response<User> userResponse) {
        switch (userResponse.code()) {
            case HttpURLConnection.HTTP_CREATED:
                getView().returnUserDetails(userResponse.body());
                registerSuccessful();
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                registerFailed();
                break;
        }
    }

    private void registerSuccessful() {
        getView().onShowDialog("We're trying to register you, please wait...");
        getView().onRegisterSuccess("Register completed!!");
    }

    private void registerFailed() {
        getView().onHideDialog();
        getView().onErrorToast("Username or password incorrect...");
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }
}
