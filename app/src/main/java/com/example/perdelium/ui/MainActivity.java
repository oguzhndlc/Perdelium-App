package com.example.perdelium.ui;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.perdelium.R;
import com.example.perdelium.utils.SharedPreferencesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ NavController SADECE BURADAN alınır
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        navController = navHostFragment.getNavController();

        bottomNavigation = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigation, navController);

        // ✅ Login / Register ekranlarında bottom nav gizle

        // ✅ LOGIN KONTROLÜ (NavController hazır olduktan sonra)
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            boolean isLoggedIn = SharedPreferencesUtil.isLoggedIn(this);

            // 🔒 Login gerektiren fragmentlar
            boolean requiresAuth =
                    destination.getId() == R.id.profileFragment ||
                            destination.getId() == R.id.addContentFragment ||
                            destination.getId() == R.id.favoritesFragment;

            if (requiresAuth && !isLoggedIn) {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build();

                controller.navigate(R.id.loginFragment, null, navOptions);
            }
        });

    }
}
