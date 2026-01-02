package com.example.perdelium.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.perdelium.R;
import com.example.perdelium.model.User;
import com.example.perdelium.utils.SharedPreferencesUtil;

public class ProfileFragment extends Fragment {

    private TextView textProfile;
    private NavController navController;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textProfile = view.findViewById(R.id.profileInfo);

        Button btnEdit = view.findViewById(R.id.editBtn);
        Button btnFav = view.findViewById(R.id.favoriteBtn);
        Button btnMyContents = view.findViewById(R.id.MyContentsBtn);
        Button btnLogout = view.findViewById(R.id.LogoutBtn);

        navController = NavHostFragment.findNavController(this);

        // 🔐 Login kontrolü
        if (!SharedPreferencesUtil.isLoggedIn(requireContext())) {
            navController.navigate(R.id.loginFragment);
            return;
        }

        showProfileInfo();

        btnEdit.setOnClickListener(v ->
                navController.navigate(R.id.editProfileFragment));

        btnFav.setOnClickListener(v ->
                navController.navigate(R.id.favoritesFragment));

        btnMyContents.setOnClickListener(v ->
                navController.navigate(R.id.myContentsFragment));

        btnLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        // 1️⃣ Kullanıcı bilgilerini sil
        SharedPreferencesUtil.clearUserInfo(requireContext());

        // 2️⃣ Backstack temizle + Login
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .build();

        navController.navigate(R.id.loginFragment, null, navOptions);
    }

    private void showProfileInfo() {
        User user = SharedPreferencesUtil.getUserInfo(requireContext());

        if (user == null) {
            navController.navigate(R.id.loginFragment);
            return;
        }

        textProfile.setText(user.getUsername());
    }
}
