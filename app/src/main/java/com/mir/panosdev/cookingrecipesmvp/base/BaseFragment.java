package com.mir.panosdev.cookingrecipesmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.mir.panosdev.cookingrecipesmvp.application.RecipeApplication;
import com.mir.panosdev.cookingrecipesmvp.dependencyinjection.components.ApplicationComponent;


public class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resolveDaggerDependency();
    }

    protected void resolveDaggerDependency() {
        //To be implemented
    }

    protected ApplicationComponent getApplicationComponent(){
        return ((RecipeApplication)getActivity().getApplication()).getApplicationComponent();
    }

}
