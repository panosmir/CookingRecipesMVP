package com.mir.panosdev.cookingrecipesmvp.mvp.view;

import com.mir.panosdev.cookingrecipesmvp.base.BaseView;
import com.mir.panosdev.cookingrecipesmvp.mvp.model.recipes.Recipe;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Panos on 3/18/2017.
 */

public interface MainView extends BaseView {

    interface MainView1 extends BaseView{
        void onShowDialog(String message);

        void onHideDialog();

        void onShowToast(String message);

        void onRecipeLoaded(List<Recipe> recipes);

        void onClearItems();

        void onNetworkUnavailableToast(String message);
    }

    interface Presenter {
        <T> void subsribe(Observable<T> observable, Observer<T> observer);
        void unsubscribe();
    }

}
