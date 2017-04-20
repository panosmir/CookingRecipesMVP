package com.mir.panosdev.cookingrecipesmvp.listeners;

import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

/**
 * Created by Panos on 4/20/2017.
 */
@FunctionalInterface
public interface OnBottomNavigationClickListener extends BottomNavigationView.OnNavigationItemSelectedListener{
    boolean onNavigationItemSelected(MenuItem menu);
}
