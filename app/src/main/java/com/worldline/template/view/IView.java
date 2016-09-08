package com.worldline.template.view;

import android.content.Context;

/**
 * Root class for View classes, a View M<b>V</b>P.
 */
public interface IView {
    boolean isReady();

    boolean isAlreadyLoaded();

    void showEmptyCase();

    Context getContext();
}
