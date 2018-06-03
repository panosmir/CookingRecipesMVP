package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

public interface LoginActivityMVP {

    interface LoginView extends BaseView{
        User getUserDetails();
        void onHideDialog();
        void onLoginCompleted(String message);
        void onErrorToast(String message);
        void returnUserDetails(User user);
        void onShowDialog(String s);
    }

    interface Presenter{
        void attachView(LoginActivityMVP.LoginView view);
        void detachView();
    }
}
