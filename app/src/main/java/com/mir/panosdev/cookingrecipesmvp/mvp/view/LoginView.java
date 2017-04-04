package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

import java.util.List;

/**
 * Created by Panos on 3/27/2017.
 */

public interface LoginView extends BaseView {

    User getUserDetails();

    void onHideDialog();

    void onLoginOrRegisterCompleted(String message);

    void onErrorToast(String message);

    void returnUserDetails(User user);
}
