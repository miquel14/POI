package com.worldline.template;

import com.karumi.dexter.Dexter;
import com.worldline.data.util.RealmVersions;
import com.worldline.data.util.preferences.PreferencesUtil;
import com.worldline.template.internal.di.component.ApplicationComponent;
import com.worldline.template.internal.di.module.ApplicationModule;

import android.app.Application;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AndroidApplication extends Application {

    @Inject
    protected PreferencesUtil preferencesUtil;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.applicationComponent.inject(this);

        //        Fabric.with(this, new CrashLytics());

        Dexter.initialize(this);
        initRealm();


    }

    private void initRealm() {
        //Realm  migration
        RealmConfiguration myConfig = new RealmConfiguration.Builder(this)
                .schemaVersion(RealmVersions.VERSION_0)
//                .migration(new AppRealmMigration(this))
                .build();
        Realm.setDefaultConfiguration(myConfig);
    }

    private void initializeInjector() {
//        this.applicationComponent = DaggerApplicationComponent.builder()
//                .applicationModule(new ApplicationModule(this))
//                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
