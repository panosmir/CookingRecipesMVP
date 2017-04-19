package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.users.User;

/**
 * Created by Panos on 4/7/2017.
 */

public interface RegisterView extends BaseView {
    User getUserDetails();

    void onHideDialog();

    void onErrorToast(String message);

    void returnUserDetails(User user);

    void onRegisterSuccess(String s);

    void onShowDialog(String message);
}
