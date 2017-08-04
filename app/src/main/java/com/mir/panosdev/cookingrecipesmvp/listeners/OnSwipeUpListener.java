package com.mir.panosdev.cookingrecipesmvp.listeners;

import android.support.v4.widget.SwipeRefreshLayout;

@FunctionalInterface
public interface OnSwipeUpListener extends SwipeRefreshLayout.OnRefreshListener{
    void onRefresh();
}
