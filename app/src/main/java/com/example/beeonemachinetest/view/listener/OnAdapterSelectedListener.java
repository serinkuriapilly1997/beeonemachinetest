package com.example.beeonemachinetest.view.listener;

/**
 * Created by SERIN_SEB on 09-10-2021.
 */
public interface OnAdapterSelectedListener<T> {

    void onAdapterSelected(T pos);

    void onKeyValue(Object value);
}