package com.example.beeonemachinetest.base;

import android.support.annotation.LayoutRes;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by SERIN_SEB on 09-10-2021.
 */
public abstract class BaseFragment extends Fragment {


    public <V extends ViewModel> V createViewModel(Class<V> v) {
        return new ViewModelProvider(this, getDefaultViewModelProviderFactory()).get(v);
    }

    public void hideKeyboard() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideKeyboard();
        }
    }

    public boolean isNetworkConnected() {
        boolean isNetwork = true;
        if (getActivity() instanceof BaseActivity) {
            isNetwork = ((BaseActivity) getActivity()).isNetworkConnected();
        }
        return isNetwork;
    }

    public void showToast(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showToast(message);
        }
    }
}