package com.example.beeonemachinetest.ui.settings;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.base.BaseFragment;
import com.example.beeonemachinetest.databinding.FragmentSettingsBinding;
import com.example.beeonemachinetest.databinding.LayoutContactAlertBinding;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

import static android.content.Context.MODE_PRIVATE;

@AndroidEntryPoint
public class SettingsFragment extends BaseFragment implements View.OnClickListener {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;
    SharedPreferences sharedPreferences;
    AlertDialog alertDialogBuilder;
    LayoutContactAlertBinding contactAlertBinding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel = createViewModel(SettingsViewModel.class);
        binding.setListener(this::onClick);
        initUi();
    }

    private void initUi() {


        if (getActivity() != null) {

            sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
            final boolean isDarkModeOn = sharedPreferences.getBoolean(
                    "isDarkModeOn", false);

            if (isDarkModeOn) {
                binding.textviewTheme.setText("Switch to Light Mode");
            } else {
                binding.textviewTheme.setText("Switch to Dark Mode");
            }


            try {
                PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                String version = pInfo.versionName;
                binding.appVersion.setText("version" + version);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {

        if (view == binding.cardViewTheme) {

            if (getActivity() != null) {

                final SharedPreferences.Editor editor = sharedPreferences.edit();
                final boolean isDarkModeOn = sharedPreferences.getBoolean(
                        "isDarkModeOn", false);

                if (isDarkModeOn) {

                    binding.textviewTheme.setText("Switch to Dark Mode");
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


                } else {


                    binding.textviewTheme.setText("Switch to Light Mode");
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

            }

        } else if (view == binding.cardViewContact) {
            contactAlert();
        }

    }

    private void contactAlert() {

        alertDialogBuilder = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_contact_alert, null);
        alertDialogBuilder.setView(dialogView);

        if (alertDialogBuilder.getWindow() != null) {
            alertDialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialogBuilder.show();
    }


}