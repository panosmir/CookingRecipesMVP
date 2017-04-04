package com.mir.panosdev.cookingrecipesmvp.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.mir.panosdev.cookingrecipesmvp.application.RecipeApplication;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.ApplicationComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Panos on 3/17/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        onViewReady(savedInstanceState, getIntent());
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent){
        //To be implemented by child methods
        resolveDaggerDependency();
    }

    protected void resolveDaggerDependency() {
    }

    protected void showDialog(String message){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();

    }

    protected void showBackArrow(){
        ActionBar supActionBar = getSupportActionBar();
        if(supActionBar != null){
            supActionBar.setDisplayHomeAsUpEnabled(true);
            supActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    protected void hideDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected abstract int getContentView();

    protected ApplicationComponent getApplicationComponent(){
        return ((RecipeApplication) getApplication()).getApplicationComponent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
