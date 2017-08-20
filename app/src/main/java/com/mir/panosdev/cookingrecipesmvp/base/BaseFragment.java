package com.mir.panosdev.cookingrecipesmvp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mir.panosdev.cookingrecipesmvp.application.RecipeApplication;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.ApplicationComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        resolveDaggerDependency();
        super.onCreate(savedInstanceState);
    }

    protected void resolveDaggerDependency() {
        //To be implemented
    }

    protected void showDialog(String message){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this.getActivity());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected ApplicationComponent getApplicationComponent(){
        return ((RecipeApplication)getActivity().getApplication()).getApplicationComponent();
    }

}
