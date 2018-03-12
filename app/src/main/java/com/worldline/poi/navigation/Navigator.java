package com.worldline.template.navigation;

import com.worldline.template.view.activity.DetailActivity;

import android.app.Activity;
import android.content.Context;

import javax.inject.Inject;

/**
 * Class created to handle all the navigation between activities. This class knows how to open
 * every activity in the application and provides to the client code different methods to start
 * activities with the information needed.
 */
public class Navigator {

    private Context context;

    @Inject
    public Navigator(Context context) {
        this.context = context;
    }

    public void openDetailActivity(Activity activity, int id, String title) {
        activity.startActivity(DetailActivity.getDetailCallingIntent(activity, id, title));
    }
}
