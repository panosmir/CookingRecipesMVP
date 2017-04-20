package com.mir.panosdev.cookingrecipesmvp.listeners;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Panos on 4/20/2017.
 */
@FunctionalInterface
public interface OnSwipeUpListener extends SwipeRefreshLayout.OnRefreshListener{
    void onRefresh();
}
