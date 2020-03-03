package com.example.jdemo.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.jdemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private Toolbar mToolbar;
    private ImageView mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);

        initWidget();

        if (hostFragment != null) {
            NavController navController = hostFragment.getNavController();
            NavigationUI.setupWithNavController(mBottomNavigationView, navController);
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                    if (destination.getId() == R.id.meFragment) {
                        mCamera.setVisibility(View.VISIBLE);
                    } else {
                        mCamera.setVisibility(View.GONE);
                    }
                }
            });
        }

    }

    private void initWidget() {
        mBottomNavigationView = findViewById(R.id.bnv_view);
        mToolbar = findViewById(R.id.toolbar);
        mCamera = findViewById(R.id.iv_camera);
    }
}
