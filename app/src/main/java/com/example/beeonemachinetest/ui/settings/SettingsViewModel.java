package com.example.beeonemachinetest.ui.settings;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

import static android.content.Context.MODE_PRIVATE;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

    private MutableLiveData<Integer> id = new MutableLiveData<>();

    @Inject
    public SettingsViewModel() {

    }

    public MutableLiveData<Integer> getId() {
        return id;
    }

    public void setId(Integer id) {

        this.id.setValue(id);
    }


}