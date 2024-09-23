package com.example.digisign;

import android.app.Application;

public class DigiSignApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.initializeDatabase(this.getApplicationContext());
    }

}