package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginActivityMVP;
import java.net.HttpURLConnection;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginPresenter implements LoginActivityMVP.Presenter {

    private LoginActivityMVP.LoginView mView;
    protected CompositeDisposable compositeDisposable;

    @Inject
    protected RecipesApiService mRecipesApiService;

    @Inject
    public LoginPresenter() {
    }


    @Inject
    public void userLogin() {
        if (mView != null && mView.getUserDetails()!=null) {
            Single<Response<User>> userObservable = mRecipesApiService.userLogin(mView.getUserDetails());
            Disposable disposable = userObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<Response<User>>() {
                        @Override
                        public void onSuccess(Response<User> userResponse) {
                            switch (userResponse.code()) {
                                case HttpURLConnection.HTTP_OK:
                                    mView.returnUserDetails(userResponse.body());
                                    loginSuccessful();
                                    break;
                                case HttpURLConnection.HTTP_NOT_FOUND:
                                    loginFailed();
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.onHideDialog();
                            mView.onErrorToast("Server is down...");
                        }
                    });
            if (compositeDisposable != null)
                compositeDisposable.add(disposable);
        }
    }

    private void loginFailed() {
        mView.onHideDialog();
        mView.onErrorToast("Username or password incorrect...");
    }

    private void loginSuccessful() {
        mView.onHideDialog();
        mView.onLoginCompleted("You logged in successfully!!!");
    }

    @Override
    public void attachView(LoginActivityMVP.LoginView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
        mView = null;
    }
}
