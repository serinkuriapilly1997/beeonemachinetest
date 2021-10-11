package com.example.beeonemachinetest.application;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Created by SERIN_SEB on 09-10-2021.
 */
@HiltAndroidApp
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}