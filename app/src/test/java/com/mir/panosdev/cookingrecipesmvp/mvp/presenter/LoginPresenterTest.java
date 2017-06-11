package com.mir.panosdev.cookingrecipesmvp.mvp.presenter;

import android.os.Looper;

import com.mir.panosdev.cookingrecipesmvp.api.RecipesApiService;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;
import com.mir.panosdev.cookingrecipesmvp.mvp.view.LoginActivityMVP;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.reactivestreams.Subscriber;

import java.net.HttpURLConnection;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Created by Panos on 4/19/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, User.class, Looper.class, Response.class})
public class LoginPresenterTest {
    @InjectMocks
    private LoginPresenter mLoginPresenter;
    @Mock
    private RecipesApiService mApiService;
    @Mock
    private LoginActivityMVP mView;
    @Mock
    private Observable<Response<User>> mObservable;
    @Captor
    private ArgumentCaptor<Subscriber<User>> captor;
    @Mock
    private HttpURLConnection connection;
    @Mock
    private User user;
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Looper.class);
        PowerMockito.mockStatic(Response.class);
    }

    @Test
    public void shouldLoginUser() throws Exception {
        when(mView.getUserDetails()).thenReturn(user);
        when(mApiService.userLogin(user)).thenReturn(mObservable);
        when(mObservable.subscribeOn(Schedulers.newThread())).thenReturn(mObservable);
        when(mObservable.observeOn(AndroidSchedulers.mainThread())).thenReturn(mObservable);
        mLoginPresenter.userLogin();
        verify(mView, atLeastOnce()).getUserDetails();
        verify(mApiService).userLogin(user);
        verify(mObservable).subscribeOn(Schedulers.newThread());
        verify(mObservable).observeOn(AndroidSchedulers.mainThread());
    }

    @Test
    public void onNext() throws Exception {
        when(Response.success(user).code()).thenReturn(HttpURLConnection.HTTP_OK);
        mLoginPresenter.onNext(Response.success(user));
        verify(mView).returnUserDetails(user);
        verify(mView, atLeastOnce()).onShowDialog("Logging you in... Please wait");
        verify(mView, atLeastOnce()).onLoginCompleted("You logged in successfully!!!");
        verify(mView).onHideDialog();
    }

    @Test
    public void shouldLoginFail() throws Exception {
        when(Response.success(user).code()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
        mLoginPresenter.onNext(Response.success(user));
        verify(mView).onHideDialog();
        verify(mView, atLeastOnce()).onErrorToast("Username or password incorrect...");
    }
}