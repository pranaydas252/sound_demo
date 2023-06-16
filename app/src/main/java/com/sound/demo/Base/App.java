package com.sound.demo.Base;

import android.app.Application;
import android.content.Context;

import io.realm.BuildConfig;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import android.content.Intent;
import com.sound.demo.LocationService;


public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Realm.init(this);

        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .allowWritesOnUiThread(true)
                        .deleteRealmIfMigrationNeeded()
                        .build();
        Realm.setDefaultConfiguration(config);
    }

    public static Context getContext() {
        return context;
    }
}

