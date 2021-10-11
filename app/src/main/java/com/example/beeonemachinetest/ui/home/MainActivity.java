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
    boolean isFirst = true;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragment = new HomeFragment();
        albumFragment = new AlbumFragment();
        settingsFragment = new SettingsFragment();
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);

//        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
//
//        String mode = sharedPreferences.getString("dark", "");
//        if (mode.isEmpty())

            binding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);


//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                return true;

            case R.id.navigation_album:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, albumFragment).commit();
                isFirst = false;
                return true;

            case R.id.navigation_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingsFragment).commit();
                return true;
        }
        return false;
    }


    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, fragment)
                .setCustomAnimations(0, 0)
                .commit();

//        int id = getSelectedItem(binding.bottomNavigationView);
//        binding.bottomNavigationView.setSelectedItemId(id);


    }


}