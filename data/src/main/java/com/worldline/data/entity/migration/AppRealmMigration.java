package com.worldline.data.entity.migration;

import com.worldline.data.util.RealmVersions;

import android.content.Context;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Realm migration
 */
public class AppRealmMigration implements RealmMigration {

    private Context context;

    public AppRealmMigration(Context context) {
        this.context = context;
    }

    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        // Migrate from version 0 to version 1
        if (oldVersion == RealmVersions.VERSION_0) {
            // Migrate
            oldVersion++;
        }
    }
}