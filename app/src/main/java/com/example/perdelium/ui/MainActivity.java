package com.example.perdelium.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.perdelium.R;
import com.example.perdelium.ui.fragments.AddContentFragment;
import com.example.perdelium.ui.fragments.ContentsFragment;
import com.example.perdelium.ui.fragments.HomeFragment;
import com.example.perdelium.ui.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    //Tanımlamalar
    BottomNavigationView bottomNavigation;

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

        //onCreate içi tanımlamalar
        bottomNavigation = findViewById(R.id.bottomNavigation);

        //Ana Fragment açıldı
        loadFragment(new HomeFragment());

        //Alt Menü sayfa geçişleri
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_home) {
                loadFragment(new HomeFragment());
            } else if (id==R.id.menu_contents) {
                loadFragment(new ContentsFragment());
            } else if (id==R.id.menu_profile) {
                loadFragment(new ProfileFragment());
            }else if (id==R.id.menu_addContent) {
                loadFragment(new AddContentFragment());
            }

            return true;
        });


    }


    public void loadFragment(Fragment fragment) {
        // Fragment yöneticisini al
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment); // fragmentContainer id'sine yükle
        ft.commit();
    }
}