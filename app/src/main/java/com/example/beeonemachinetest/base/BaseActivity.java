package com.example.beeonemachinetest.base;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.beeonemachinetest.utils.InternetUtils;
import com.example.beeonemachinetest.utils.ToastUtils;

import javax.inject.Inject;

/**
 * Created by SERIN_SEB on 09-10-2021.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    ToastUtils toastUtils;



    public <V extends ViewModel> V createViewModel(Class<V> v) {
        return new ViewModelProvider(this).get(v);
    }

    public boolean isNetworkConnected() {
        return InternetUtils.isNetworkAvailable(getApplicationContext());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void showToast(String message) {
        toastUtils.showToast(message);
    }

//
}