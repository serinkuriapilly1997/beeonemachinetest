package com.example.beeonemachinetest.utils;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Created by SERIN_SEB on 09-10-2021.
 */
public class ToastUtils {

    private Context context;

    @Inject
    public ToastUtils(@ApplicationContext Context context) {
        this.context = context;
    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}