package com.worldline.template.clean.presenter;

import com.worldline.data.util.preferences.PreferencesUtil;
import com.worldline.template.clean.navigation.Navigator;
import com.worldline.template.clean.view.IView;

import javax.inject.Inject;

/**
 * Abstract presenter to work as base for every presenter, representing a Presenter
 * in a model view presenter (MV<b>P</b>) pattern.
 * <p>
 * This presenter declares some methods to attach the fragment/activity lifecycle.
 */
public abstract class Presenter <V extends IView> {

    @Inject
    Navigator navigator;

    @Inject
    PreferencesUtil preferencesUtil;

    protected V view;

    private void checkViewAlreadySet() {
        if (view == null) {
            throw new IllegalArgumentException("Remember to set a view for this presenter");
        }
    }

    public V getView() {
        return view;
    }

    public void setView(V view) {
        if (view == null) {
            throw new IllegalArgumentException("Presenter must have a view");
        }
        this.view = view;
    }

    public void start() {
        checkViewAlreadySet();
        initialize();
    }

    /**
     * Method to initialize the presenter
     */
    protected abstract void initialize();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's (Activity or Fragment) onResume() method.
     */
    public abstract void resume();


    /**
     * Method that control the lifecycle of the view. It should be called in the view's (Activity or Fragment) onPause() method.
     */
    public abstract void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's (Activity or Fragment) onDestroy()
     * method.
     */

    public abstract void destroy();
}