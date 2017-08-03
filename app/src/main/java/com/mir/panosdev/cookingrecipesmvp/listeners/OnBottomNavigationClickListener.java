package com.mir.panosdev.cookingrecipesmvp.listeners;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

@FunctionalInterface
public interface OnBottomNavigationClickListener extends BottomNavigationView.OnNavigationItemSelectedListener{
    boolean onNavigationItemSelected(@NonNull MenuItem menu);
}
