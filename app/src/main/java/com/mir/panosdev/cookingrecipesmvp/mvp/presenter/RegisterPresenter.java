package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.base.BasePresenter;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.RegisterActivityMVP;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Panos on 4/7/2017.
 */

public class RegisterPresenter implements RegisterActivityMVP.Presenter {

    private RegisterActivityMVP.RegisterView mView;
    protected CompositeDisposable compositeDisposable;

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    public RegisterPresenter() {
    }

    @Inject
    public void userRegistration() {
        if (mView != null && mView.getUserDetails() != null) {
            Observable<Response<User>> userObservable = mRecipesApiService.userRegistration(mView.getUserDetails());
            Disposable disposable = userObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<User>>() {
                        @Override
                        public void onNext(Response<User> userResponse) {
                            switch (userResponse.code()) {
                                case HttpURLConnection.HTTP_CREATED:
                                    mView.returnUserDetails(userResponse.body());
                                    registerSuccessful();
                                    break;
                                case HttpURLConnection.HTTP_BAD_REQUEST:
                                    registerFailed();
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    private void registerSuccessful() {
        mView.onShowDialog("We're trying to register you, please wait...");
        mView.onRegisterSuccess("Register completed!!");
    }

    private void registerFailed() {
        mView.onHideDialog();
        mView.onErrorToast("Username or password incorrect...");
    }

    @Override
    public void attachView(RegisterActivityMVP.RegisterView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
        mView = null;
    }
}
