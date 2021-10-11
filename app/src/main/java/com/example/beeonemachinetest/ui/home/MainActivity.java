package com.example.beeonemachinetest.ui.home;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.base.BaseActivity;
import com.example.beeonemachinetest.ui.album.AlbumFragment;
import com.example.beeonemachinetest.ui.homedetail.HomeDetailFragment;
import com.example.beeonemachinetest.ui.settings.SettingsFragment;
import com.example.beeonemachinetest.ui.settings.SettingsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.beeonemachinetest.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    HomeFragment homeFragment;
    AlbumFragment albumFragment;
    SettingsFragment settingsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragment = new HomeFragment();
        albumFragment = new AlbumFragment();
        settingsFragment = new SettingsFragment();
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {

            binding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                return true;

            case R.id.navigation_album:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, albumFragment).commit();
                return true;

            case R.id.navigation_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingsFragment).commit();
                return true;
        }
        return false;
    }


    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}