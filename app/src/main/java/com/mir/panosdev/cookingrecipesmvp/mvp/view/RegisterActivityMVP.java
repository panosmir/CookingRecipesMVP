package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

public interface RegisterActivityMVP {

    interface RegisterView extends BaseView{
        User getUserDetails();
        void onHideDialog();
        void onErrorToast(String message);
        void returnUserDetails(User user);
        void onRegisterSuccess(String s);
        void onShowDialog(String message);
    }

    interface Presenter{
        void attachView(RegisterActivityMVP.RegisterView view);
        void detachView();
    }
}
